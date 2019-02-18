/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.android.ui.lifecycle

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import org.swiften.redux.core.EmptyJob
import org.swiften.redux.core.IActionDispatcher
import org.swiften.redux.core.IDeinitializer
import org.swiften.redux.core.IReduxSubscription
import org.swiften.redux.core.IStateGetter
import org.swiften.redux.core.ReduxSubscription
import org.swiften.redux.ui.IPropContainer
import org.swiften.redux.ui.IPropInjector
import org.swiften.redux.ui.IPropLifecycleOwner
import org.swiften.redux.ui.IPropMapper
import org.swiften.redux.ui.ObservableReduxProp
import org.swiften.redux.ui.ReduxProp
import org.swiften.redux.ui.StaticProp
import java.util.Collections.synchronizedList
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
    IPropContainer<Int, Unit>,
    IPropLifecycleOwner<Int, Unit>,
    IPropMapper<Int, Unit, Int, Unit> by TestLifecycleOwner {
    companion object : IPropMapper<Int, Unit, Int, Unit> {
      override fun mapState(state: Int, outProp: Unit) = state
      override fun mapAction(dispatch: IActionDispatcher, outProp: Unit) = Unit
    }

    val registry = TestLifecycleRegistry(this)
    override var reduxProp by ObservableReduxProp<Int, Unit> { _, _ -> }
    override fun getLifecycle(): Lifecycle = this.registry
  }

  class TestInjector(override val lastState: IStateGetter<Int> = { 0 }) : IPropInjector<Int> {
    val subscriptions: MutableList<IReduxSubscription> = synchronizedList(mutableListOf())
    val injectionCount get() = this.subscriptions.size

    @Suppress("UNCHECKED_CAST")
    override fun <LState, OutProp, View, State, Action> inject(
      outProp: OutProp,
      view: View,
      mapper: IPropMapper<LState, OutProp, State, Action>
    ): IReduxSubscription where
      LState : Any,
      View : IPropContainer<State, Action>,
      View : IPropLifecycleOwner<LState, OutProp>,
      State : Any,
      Action : Any {
      val lastState = this.lastState()
      val subscription = ReduxSubscription("$view") { view.afterPropInjectionEnds() }
      val state = mapper.mapState(lastState as LState, outProp)
      val action = mapper.mapAction(this.dispatch, outProp)
      view.beforePropInjectionStarts(StaticProp(this as IPropInjector<LState>, outProp))
      view.reduxProp = ReduxProp(subscription, state, action)
      this.subscriptions.add(subscription)
      return subscription
    }

    override val dispatch: IActionDispatcher = { EmptyJob }
    override val deinitialize: IDeinitializer = {}
  }
}
