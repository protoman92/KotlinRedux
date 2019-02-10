/*
 * Copyright (c) haipham 2018. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.ui

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.swiften.redux.core.IActionDispatcher
import org.swiften.redux.core.IReduxAction
import org.swiften.redux.core.IReduxStore
import org.swiften.redux.core.IReduxSubscriber
import org.swiften.redux.core.ReduxSubscription
import org.swiften.redux.core.ThreadSafeStore
import java.util.concurrent.atomic.AtomicInteger

/** Created by haipham on 2018/12/20 */
open class PropInjectorTest {
  data class S(val query: String = "")
  class A

  sealed class Action : IReduxAction {
    data class SetQuery(val query: String) : Action()
  }

  class TestInjector<GState>(
    store: IReduxStore<GState>
  ) : PropInjector<GState>(store) where GState : Any

  class StoreWrapper(private val store: IReduxStore<S>) : IReduxStore<S> by store {
    val unsubscribeCount = AtomicInteger()

    override val subscribe: IReduxSubscriber<S> = { id, callback ->
      val sub = this@StoreWrapper.store.subscribe(id, callback)

      ReduxSubscription(id) {
        this@StoreWrapper.unsubscribeCount.incrementAndGet()
        sub.unsubscribe()
      }
    }
  }

  internal class View : IPropContainer<S, Unit, S, A> {
    override var reduxProps by ObservableReduxProps<S, A> { prev, next ->
      this.propCallback?.invoke(prev, next)
      this.propsInjectionCount.incrementAndGet()
    }

    var propCallback: ((IVariableProps<S, A>?, IVariableProps<S, A>) -> Unit)? = null
    val propsInjectionCount = AtomicInteger()
    val beforeInjectionCount = AtomicInteger()
    val afterInjectionCount = AtomicInteger()

    override fun beforePropInjectionStarts(sp: StaticProps<S, Unit>) {
      assertNotNull(this.reduxProps)
      this.beforeInjectionCount.incrementAndGet()
    }

    override fun afterPropInjectionEnds() { this.afterInjectionCount.incrementAndGet() }
  }

  protected lateinit var store: StoreWrapper
  protected lateinit var mapper: IPropMapper<S, Unit, S, A>

  @Before
  open fun beforeMethod() {
    val store = ThreadSafeStore(S()) { s, a ->
      when (a) {
        is Action -> when (a) {
          is Action.SetQuery -> s.copy(query = a.query)
        }
        else -> s
      }
    }

    this.store = StoreWrapper(store)

    this.mapper = object : IPropMapper<S, Unit, S, A> {
      override fun mapState(state: S, outProps: Unit) = state
      override fun mapAction(dispatch: IActionDispatcher, outProps: Unit) = A()
    }
  }

  open fun createInjector(store: IReduxStore<S>): IPropInjector<S> {
    return TestInjector(store)
  }

  @Test
  fun `Injecting same state props - should not trigger set event`() {
    // Setup
    val injector = this.createInjector(this.store)
    val view = View()
    val allProps = arrayListOf<Pair<S?, S?>>()
    view.propCallback = { prev, next -> allProps.add(prev?.state to next.state) }

    // When
    injector.inject(view, Unit, this.mapper)
    this.store.dispatch(Action.SetQuery("1"))
    this.store.dispatch(Action.SetQuery("1"))
    this.store.dispatch(Action.SetQuery("2"))
    this.store.dispatch(Action.SetQuery("2"))
    this.store.dispatch(Action.SetQuery("3"))
    this.store.dispatch(Action.SetQuery("3"))
    injector.inject(view, Unit, this.mapper)

    // Then
    assertEquals(this.store.unsubscribeCount.get(), 1)
    assertEquals(view.propsInjectionCount.get(), 6)
    assertEquals(view.beforeInjectionCount.get(), 2)
    assertEquals(view.afterInjectionCount.get(), 1)

    assertEquals(allProps, arrayListOf(
      null to S(""),
      S("") to S("1"),
      S("1") to S("2"),
      S("2") to S("3"),
      S("3") to null,
      null to S("3"))
    )
  }
}
