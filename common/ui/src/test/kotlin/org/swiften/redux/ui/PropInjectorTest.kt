/*
 * Copyright (c) haipham 2018. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.ui

import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.swiften.redux.core.IReduxAction
import org.swiften.redux.core.IActionDispatcher
import org.swiften.redux.core.IReduxStore
import org.swiften.redux.core.IReduxSubscriber
import org.swiften.redux.core.ReduxSubscription
import org.swiften.redux.store.ThreadSafeReduxStore

/** Created by haipham on 2018/12/20 */
class PropInjectorTest {
  data class S(val query: String = "")
  class A

  sealed class Action : IReduxAction {
    data class SetQuery(val query: String) : Action()
  }

  class StoreWrapper(private val store: IReduxStore<S>) : IReduxStore<S> by store {
    var unsubscribeCount: Int = 0

    override val subscribe: IReduxSubscriber<S> = { id, callback ->
      val sub = this@StoreWrapper.store.subscribe(id, callback)

      ReduxSubscription(id) {
        this@StoreWrapper.unsubscribeCount += 1
        sub.unsubscribe()
      }
    }
  }

  class View : IPropContainer<PropInjectorTest.S, PropInjectorTest.S, PropInjectorTest.A> {
    override var reduxProps by ObservableReduxProps<PropInjectorTest.S, S, A> { prev, next ->
      this.propCallback(prev, next)
      this.reduxPropsInjectionCount += 1
    }

    lateinit var propCallback: (VariableProps<S, A>?, VariableProps<S, A>?) -> Unit
    var reduxPropsInjectionCount = 0
    var beforeInjectionCount = 0
    var afterInjectionCount = 0

    override fun beforePropInjectionStarts(sp: StaticProps<S>) {
      Assert.assertNotNull(this.reduxProps)
      this.beforeInjectionCount += 1
    }

    override fun afterPropInjectionEnds() { this.afterInjectionCount += 1 }
  }

  private lateinit var store: StoreWrapper
  private lateinit var injector: IPropInjector<S>
  private lateinit var mapper: IPropMapper<S, Unit, S, A>

  @Before
  fun beforeMethod() {
    val store = ThreadSafeReduxStore(S()) { s, a ->
      when (a) {
        is Action -> when (a) {
          is Action.SetQuery -> s.copy(query = a.query)
        }
        else -> s
      }
    }

    this.store = StoreWrapper(store)
    this.injector = PropInjector(this.store)

    this.mapper = object : IPropMapper<S, Unit, S, A> {
      override fun mapState(state: S, outProps: Unit) = state
      override fun mapAction(dispatch: IActionDispatcher, state: S, outProps: Unit) = A()
    }
  }

  @Test
  fun `Injecting same state props - should not trigger set event`() {
    // Setup
    val view = View()
    val allProps = arrayListOf<Pair<S?, S?>>()
    view.propCallback = { prev, next -> allProps.add(prev?.state to next?.state) }

    // When
    this.injector.injectProps(view, Unit, this.mapper)
    this.store.dispatch(Action.SetQuery("1"))
    this.store.dispatch(Action.SetQuery("1"))
    this.store.dispatch(Action.SetQuery("2"))
    this.store.dispatch(Action.SetQuery("2"))
    this.store.dispatch(Action.SetQuery("3"))
    this.store.dispatch(Action.SetQuery("3"))
    this.injector.injectProps(view, Unit, this.mapper)

    // Then
    Assert.assertEquals(this.store.unsubscribeCount, 1)
    Assert.assertEquals(view.reduxPropsInjectionCount, 6)
    Assert.assertEquals(view.beforeInjectionCount, 2)
    Assert.assertEquals(view.afterInjectionCount, 1)

    Assert.assertEquals(allProps, arrayListOf(
      null to S(""),
      S("") to S("1"),
      S("1") to S("2"),
      S("2") to S("3"),
      S("3") to null,
      null to S("3"))
    )
  }
}
