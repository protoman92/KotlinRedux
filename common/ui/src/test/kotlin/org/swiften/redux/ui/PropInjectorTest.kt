/*
 * Copyright (c) haipham 2018. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.ui

import org.swiften.redux.core.Redux
import org.swiften.redux.core.ReduxDispatcher
import org.swiften.redux.core.ReduxSubscriber
import org.swiften.redux.core.SimpleReduxStore
import org.testng.Assert
import org.testng.annotations.BeforeMethod
import org.testng.annotations.Test
import kotlin.properties.Delegates

/**
 * Created by haipham on 2018/12/20.
 */
class PropInjectorTest {
  data class S(val query: String = "")
  class A

  sealed class Action: Redux.IAction {
    data class SetQuery(val query: String): Action()
  }

  class StoreWrapper(val store: Redux.IStore<S>): Redux.IStore<S> by store {
    var unsubCount: Int = 0

    override val subscribe = object : ReduxSubscriber<S> {
      override operator fun invoke(
        subscriberId: String,
        callback: Function1<S, Unit>
      ): Redux.Subscription {
        val sub = this@StoreWrapper.store.subscribe(subscriberId, callback)

        return Redux.Subscription {
          this@StoreWrapper.unsubCount += 1
          sub.unsubscribe()
        }
      }
    }
  }

  class View: ReduxUI.IPropContainerView<S, S, A> {
    override var staticProps
      by Delegates.observable<ReduxUI.StaticProps<S>?>(null) { _, _, p ->
        this.didSetStaticProps(p)
      }

    override var variableProps
      by Delegates.observable<ReduxUI.VariableProps<S, A>?>(null) { _, _, p ->
        this.didSetVariableProps(p)
      }

    var staticInjectionCount = 0
    var variableInjectionCount = 0

    private fun didSetStaticProps(props: ReduxUI.StaticProps<S>?) {
      this.staticInjectionCount += 1
    }

    private fun didSetVariableProps(props: ReduxUI.VariableProps<S, A>?) {
      this.variableInjectionCount += 1
    }
  }

  private lateinit var store: StoreWrapper
  private lateinit var injector: ReduxUI.IPropInjector<S>
  private lateinit var mapper: ReduxUI.IPropMapper<S, Unit, S, A>

  @BeforeMethod
  fun beforeMethod() {
    val store = SimpleReduxStore(S()) { s, a ->
      when (a) {
        is Action -> when (a) {is Action.SetQuery -> s.copy(query = a.query) }
        else -> s
      }
    }

    this.store = StoreWrapper(store)
    this.injector = ReduxUI.PropInjector(this.store)

    this.mapper = object : ReduxUI.IPropMapper<S, Unit, S, A> {
      override fun mapState(state: S, outProps: Unit) = state

      override fun mapAction(
        dispatch: ReduxDispatcher,
        state: S,
        outProps: Unit
      ) = A()
    }
  }

  @Test
  fun `Injecting same state props - should not trigger set event`() {
    /// Setup
    val view = View()

    /// When
    this.injector.injectProps(view, Unit, this.mapper)
    this.store.dispatch(Action.SetQuery("1"))
    this.store.dispatch(Action.SetQuery("1"))
    this.store.dispatch(Action.SetQuery("2"))
    this.store.dispatch(Action.SetQuery("2"))
    this.store.dispatch(Action.SetQuery("3"))
    this.store.dispatch(Action.SetQuery("3"))
    this.injector.injectProps(view, Unit, this.mapper)

    /// Then
    Assert.assertEquals(this.store.unsubCount, 1)
    Assert.assertEquals(view.staticInjectionCount, 2)
    Assert.assertEquals(view.variableInjectionCount, 4)
  }
}
