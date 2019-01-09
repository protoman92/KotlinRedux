/*
 * Copyright (c) haipham 2018. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.ui

import org.swiften.redux.core.IReduxAction
import org.swiften.redux.core.IReduxDispatcher
import org.swiften.redux.core.IReduxStore
import org.swiften.redux.core.IReduxSubscriber
import org.swiften.redux.core.ReduxSubscription
import org.swiften.redux.core.SimpleReduxStore
import org.testng.Assert
import org.testng.annotations.BeforeMethod
import org.testng.annotations.Test
import kotlin.properties.Delegates

/** Created by haipham on 2018/12/20 */
class PropInjectorTest {
  data class S(val query: String = "")
  class A

  sealed class Action : IReduxAction {
    data class SetQuery(val query: String) : Action()
  }

  class StoreWrapper(val store: IReduxStore<S>) : IReduxStore<S> by store {
    var unsubCount: Int = 0

    override val subscribe = object : IReduxSubscriber<S> {
      override operator fun invoke(
        subscriberId: String,
        callback: Function1<S, Unit>
      ): ReduxSubscription {
        val sub = this@StoreWrapper.store.subscribe(subscriberId, callback)

        return ReduxSubscription {
          this@StoreWrapper.unsubCount += 1
          sub.unsubscribe()
        }
      }
    }
  }

  class View : IReduxPropContainer<S, S, A> {
    override lateinit var staticProps: StaticProps<S>

    override var variableProps
      by Delegates.observable<VariableProps<S, A>?>(null) { _, _, p ->
        this.didSetVariableProps(p)
      }

    var variableInjectionCount = 0

    private fun didSetVariableProps(props: VariableProps<S, A>?) {
      this.variableInjectionCount += 1
    }
  }

  private lateinit var store: StoreWrapper
  private lateinit var injector: IReduxPropInjector<S>
  private lateinit var mapper: IReduxPropMapper<S, Unit, S, A>

  @BeforeMethod
  fun beforeMethod() {
    val store = SimpleReduxStore(S()) { s, a ->
      when (a) {
        is Action -> when (a) { is Action.SetQuery -> s.copy(query = a.query) }
        else -> s
      }
    }

    this.store = StoreWrapper(store)
    this.injector = ReduxPropInjector(this.store)

    this.mapper = object : IReduxPropMapper<S, Unit, S, A> {
      override fun mapState(state: S, outProps: Unit) = state

      override fun mapAction(
        dispatch: IReduxDispatcher,
        state: S,
        outProps: Unit
      ) = A()
    }
  }

  @Test
  fun `Injecting same state props - should not trigger set event`() {
    // Setup
    val view = View()

    // When
    this.injector.injectRecyclerViewProps(view, Unit, this.mapper)
    this.store.dispatch(Action.SetQuery("1"))
    this.store.dispatch(Action.SetQuery("1"))
    this.store.dispatch(Action.SetQuery("2"))
    this.store.dispatch(Action.SetQuery("2"))
    this.store.dispatch(Action.SetQuery("3"))
    this.store.dispatch(Action.SetQuery("3"))
    this.injector.injectRecyclerViewProps(view, Unit, this.mapper)

    // Then
    Assert.assertEquals(this.store.unsubCount, 1)
    Assert.assertEquals(view.variableInjectionCount, 4)
  }
}
