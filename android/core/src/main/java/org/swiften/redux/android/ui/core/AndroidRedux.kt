/*
 * Copyright (c) haipham 2018. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.android.ui.core

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import org.swiften.redux.android.ui.core.AndroidRedux.ReduxLifecycleObserver
import org.swiften.redux.core.Redux
import org.swiften.redux.core.ReduxDispatcher
import org.swiften.redux.ui.ReduxUI

/** Created by haipham on 2018/12/17 */
/** Top-level namespace for Android Redux UI functionalities */
object AndroidRedux {
  /** Use this [LifecycleObserver] to unsubscribe from a [Redux.Subscription] */
  internal class ReduxLifecycleObserver private constructor(
    private val lifecycle: Lifecycle,
    private val subscription: Redux.Subscription
  ) : LifecycleObserver {
    companion object {
      /** Create a [ReduxLifecycleObserver] with [lifecycle] and [subscription] */
      fun register(lifecycle: Lifecycle, subscription: Redux.Subscription) {
        ReduxLifecycleObserver(lifecycle, subscription)
      }
    }

    init { lifecycle.addObserver(this) }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onStop() {
      this.subscription.unsubscribe()
      this.lifecycle.removeObserver(this)
    }
  }

  /**
   * Default implementation for [ReduxUI.IPropInjector]. [ReduxUI.PropInjector.injectProps]
   * will perform the injector on the main thread, so we do not need to handle threads anywhere
   * else.
   */
  class PropInjector<State>(
    private val injector: ReduxUI.IPropInjector<State>
  ) : ReduxUI.IPropInjector<State> {
    /** Use the default [ReduxUI.PropInjector] implementation */
    constructor(store: Redux.IStore<State>) : this(ReduxUI.PropInjector(store))

    private fun runOnUIThread(runnable: () -> Unit) {
      if (Looper.myLooper() == Looper.getMainLooper()) { runnable() } else {
        Handler(Looper.getMainLooper()).post { runnable() }
      }
    }

    override fun <OP, SP, AP> injectProps(
      view: ReduxUI.IPropContainer<State, SP, AP>,
      outProps: OP,
      mapper: ReduxUI.IPropMapper<State, OP, SP, AP>
    ) = this.injector.injectProps(
      object : ReduxUI.IPropContainer<State, SP, AP> {
        override var staticProps: ReduxUI.StaticProps<State>
          get() = view.staticProps
          set(value) { view.staticProps = value }

        override var variableProps: ReduxUI.VariableProps<SP, AP>?
          get() = view.variableProps
          set(value) { this@PropInjector.runOnUIThread { view.variableProps = value } }
      },
      outProps, mapper)
  }
}

/** When [ReduxLifecycleObserver.onStop] is called, unsubscribe from [State] updates */
fun <State, LC, OP, SP, AP> ReduxUI.IPropInjector<State>.injectLifecycleProps(
  lifecycleOwner: LC,
  outProps: OP,
  mapper: ReduxUI.IPropMapper<State, OP, SP, AP>
) where
  LC : LifecycleOwner,
  LC : ReduxUI.IPropContainer<State, SP, AP>
{
  val view: ReduxUI.IPropContainer<State, SP, AP> = lifecycleOwner
  val subscription = this.injectProps(view, outProps, mapper)
  ReduxLifecycleObserver.register(lifecycleOwner.lifecycle, subscription)
}

/**
 * Inject props into [lifecycleOwner], which is a view that only has a mutable [SP] but handles
 * no actions.
 */
fun <State, LC, OP, SP> ReduxUI.IPropInjector<State>.injectLifecycleProps(
  lifecycleOwner: LC,
  outProps: OP,
  mapper: ReduxUI.IStatePropMapper<State, OP, SP>
) where
  LC : LifecycleOwner,
  LC : ReduxUI.IPropContainer<State, SP, Unit> =
  this.injectProps(lifecycleOwner, outProps,
    object : ReduxUI.IPropMapper<State, OP, SP, Unit> {
      override fun mapAction(dispatch: ReduxDispatcher, state: State, outProps: OP) = Unit
      override fun mapState(state: State, outProps: OP) = mapper.mapState(state, outProps)
    })
