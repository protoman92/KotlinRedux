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
import org.swiften.redux.ui.IActionDependency
import org.swiften.redux.ui.IPropContainer
import org.swiften.redux.ui.IPropInjector
import org.swiften.redux.ui.IPropLifecycleOwner
import org.swiften.redux.ui.IPropMapper
import org.swiften.redux.ui.ObservableReduxProps
import org.swiften.redux.ui.ReduxProps
import org.swiften.redux.ui.StaticProps
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
      override fun mapAction(static: IActionDependency<Unit>, outProps: Unit) = Unit
    }

    val registry = TestLifecycleRegistry(this)
    override var reduxProps by ObservableReduxProps<Int, Unit> { _, _ -> }
    override fun getLifecycle(): Lifecycle = this.registry
  }

  class TestInjector(override val lastState: IStateGetter<Int> = { 0 }) : IPropInjector<Int, Unit> {
    override val external = Unit
    val dispatched = Collections.synchronizedList(mutableListOf<IReduxAction>())
    val subscriptions = Collections.synchronizedList(arrayListOf<IReduxSubscription>())
    val injectionCount get() = this.subscriptions.size

    @Suppress("UNCHECKED_CAST")
    override fun <LExt, OutProps, State, Action> inject(
      view: IPropContainer<Int, LExt, State, Action>,
      outProps: OutProps,
      mapper: IPropMapper<Int, LExt, OutProps, State, Action>
    ): IReduxSubscription where LExt : Any, State : Any, Action : Any {
      val lastState = this.lastState()
      val subscription = ReduxSubscription("$view") { view.afterPropInjectionEnds() }
      val state = mapper.mapState(lastState, outProps)
      val action = mapper.mapAction(this as IActionDependency<LExt>, outProps)
      view.beforePropInjectionStarts(StaticProps(this as IPropInjector<Int, LExt>))
      view.reduxProps = ReduxProps(subscription, state, action)
      this.subscriptions.add(subscription)
      return subscription
    }

    override val dispatch: IActionDispatcher = { this.dispatched.add(it) }
    override val deinitialize: IDeinitializer = {}
  }
}
