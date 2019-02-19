/*
 * Copyright (c) haipham 2018. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.ui

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Before
import org.junit.Test
import org.swiften.redux.core.IActionDispatcher
import org.swiften.redux.core.IReduxAction
import org.swiften.redux.core.IReduxStore
import org.swiften.redux.core.IReduxSubscriber
import org.swiften.redux.core.ReduxSubscription
import org.swiften.redux.core.ThreadSafeStore
import java.util.concurrent.atomic.AtomicBoolean
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

  internal class View : IPropContainer<S, A>, IPropLifecycleOwner<S, Unit> {
    private val propInitialized = AtomicBoolean(false)

    override var reduxProp by ObservableReduxProp<S, A> { prev, next ->
      this.propCallback?.invoke(prev, next)
      this.propInjectionCount.incrementAndGet()
      this.propInitialized.set(true)
    }

    var propCallback: ((IVariableProp<S, A>?, IVariableProp<S, A>) -> Unit)? = null
    val propInjectionCount = AtomicInteger()
    val beforeInjectionCount = AtomicInteger()
    val afterInjectionCount = AtomicInteger()

    override fun beforePropInjectionStarts(sp: StaticProp<S, Unit>) {
      assertFalse(this.propInitialized.get())
      this.beforeInjectionCount.incrementAndGet()
    }

    override fun afterPropInjectionEnds() {
      this.afterInjectionCount.incrementAndGet()
      this.propInitialized.set(false)
    }
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
      override fun mapState(state: S, outProp: Unit) = state
      override fun mapAction(dispatch: IActionDispatcher, outProp: Unit) = A()
    }
  }

  open fun createInjector(store: IReduxStore<S>): IFullPropInjector<S> {
    return TestInjector(store)
  }

  @Test
  fun `Injecting same state prop - should not trigger set event`() {
    // Setup
    val injector = this.createInjector(this.store)
    val view = View()
    val allProps = arrayListOf<Pair<S?, S?>>()
    val reinjectCount = 1000
    view.propCallback = { prev, next -> allProps.add(prev?.state to next.state) }

    // When
    injector.inject(Unit, view, this.mapper)
    this.store.dispatch(Action.SetQuery("1"))
    this.store.dispatch(Action.SetQuery("1"))
    this.store.dispatch(Action.SetQuery("2"))
    this.store.dispatch(Action.SetQuery("2"))
    this.store.dispatch(Action.SetQuery("3"))
    this.store.dispatch(Action.SetQuery("3"))
    repeat((reinjectCount)) { injector.inject(Unit, view, this.mapper) }

    // Then
    assertEquals(this.store.unsubscribeCount.get(), reinjectCount)
    assertEquals(view.propInjectionCount.get(), 4)
    assertEquals(view.beforeInjectionCount.get(), reinjectCount + 1)
    assertEquals(view.afterInjectionCount.get(), reinjectCount)

    assertEquals(allProps, arrayListOf(
      null to S(""),
      S("") to S("1"),
      S("1") to S("2"),
      S("2") to S("3")
    ))
  }
}
