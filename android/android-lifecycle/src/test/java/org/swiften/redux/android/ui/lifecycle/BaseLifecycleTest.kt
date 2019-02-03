/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.android.ui.lifecycle

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import org.swiften.redux.core.IActionDispatcher
import org.swiften.redux.core.IDeinitializer
import org.swiften.redux.core.IReduxAction
import org.swiften.redux.core.IReduxSubscription
import org.swiften.redux.core.IStateGetter
import org.swiften.redux.core.ReduxSubscription
import org.swiften.redux.ui.EmptyPropLifecycleOwner
import org.swiften.redux.ui.IPropContainer
import org.swiften.redux.ui.IPropInjector
import org.swiften.redux.ui.IPropLifecycleOwner
import org.swiften.redux.ui.IPropMapper
import org.swiften.redux.ui.ObservableReduxProps
import org.swiften.redux.ui.ReduxProps
import org.swiften.redux.ui.StaticProps
import org.swiften.redux.ui.VariableProps
import java.util.Collections
import java.util.concurrent.atomic.AtomicInteger

/** Created by viethai.pham on 2019/01/28 */
open class BaseLifecycleTest {
  class TestLifecycleRegistry(provider: LifecycleOwner) : LifecycleRegistry(provider) {
    val registerCount = AtomicInteger()
    val unregisterCount = AtomicInteger()

    override fun addObserver(observer: androidx.lifecycle.LifecycleObserver) {
      super.addObserver(observer)
      this.registerCount.incrementAndGet()
    }

    override fun removeObserver(observer: androidx.lifecycle.LifecycleObserver) {
      super.removeObserver(observer)
      this.unregisterCount.incrementAndGet()
    }
  }

  class TestLifecycleOwner : LifecycleOwner,
    IPropContainer<Int, Unit, Int, Unit>,
    IPropLifecycleOwner<Int, Unit> by EmptyPropLifecycleOwner(),
    IPropMapper<Int, Unit, Unit, Int, Unit> by TestLifecycleOwner {
    companion object : IPropMapper<Int, Unit, Unit, Int, Unit> {
      override fun mapState(state: Int, outProps: Unit) = state
      override fun mapAction(dispatch: IActionDispatcher, state: Int, ext: Unit, outProps: Unit) = Unit
    }

    val registry = TestLifecycleRegistry(this)
    override var reduxProps by ObservableReduxProps<Int, Unit, Int, Unit> { _, _ -> }
    override fun getLifecycle(): Lifecycle = this.registry
  }

  class TestInjector(override val lastState: IStateGetter<Int> = { 0 }) : IPropInjector<Int, Unit> {
    val dispatched = Collections.synchronizedList(mutableListOf<IReduxAction>())
    val subscriptions = Collections.synchronizedList(arrayListOf<IReduxSubscription>())
    val injectionCount get() = this.subscriptions.size

    override fun <OutProps, View, State, Action> inject(
      view: View,
      outProps: OutProps,
      mapper: IPropMapper<Int, Unit, OutProps, State, Action>
    ): IReduxSubscription where
      View : IPropContainer<Int, Unit, State, Action>,
      View : IPropLifecycleOwner<Int, Unit> {
      val lastState = this.lastState()
      val subscription = ReduxSubscription("$view") {}
      val static = StaticProps(this, subscription)
      val state = mapper.mapState(lastState, outProps)
      val action = mapper.mapAction(this.dispatch, lastState, Unit, outProps)
      val variable = VariableProps(state, action)
      view.reduxProps = ReduxProps(static, variable)
      this.subscriptions.add(subscription)
      return subscription
    }

    override val dispatch: IActionDispatcher = { this.dispatched.add(it) }
    override val deinitialize: IDeinitializer = {}
  }
}
