/*
 * Copyright (c) haipham 2018. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.android.ui

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import org.swiften.redux.core.Redux
import org.swiften.redux.core.ReduxDispatcher
import org.swiften.redux.ui.ReduxUI

/**
 * Created by haipham on 2018/12/17.
 */
/** Top-level namespace for Android Redux UI functionalities */
object AndroidReduxUI {
  /**
   * This [IPropInjector] is a specialized version of [ReduxUI.IPropInjector]
   * that handles [LifecycleOwner] and deals with lifecycles automatically.
   */
  interface IPropInjector<State> {
    /**
     * Inject props into [lifecycleOwner] and handle lifecycle as well.
     */
    fun <LC, OutProps, StateProps, ActionProps> injectProps(
      lifecycleOwner: LC,
      outProps: OutProps,
      mapper: ReduxUI.IPropMapper<State, OutProps, StateProps, ActionProps>
    ) where
      LC : LifecycleOwner,
      LC : ReduxUI.IPropContainerView<State, StateProps, ActionProps>

    /**
     * Inject props into [view], basically a view that does not have internal
     * state and handles no interactions.
     */
    fun injectProps(view: ReduxUI.IStaticPropContainerView<State>)
  }

  /**
   * Use this [LifecycleObserver] to unsubscribe from a [Redux.Subscription].
   */
  private class ReduxLifecycleObserver private constructor(
    private val lifecycle: Lifecycle,
    private val subscription: Redux.Subscription
  ): LifecycleObserver {
    companion object {
      /**
       * Create a [ReduxLifecycleObserver] with [lifecycle] and [subscription].
       */
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
   * Default implementation for [IPropInjector].
   */
  class PropInjector<State>(
    private val injector: ReduxUI.IPropInjector<State>
  ): IPropInjector<State>, ReduxUI.IPropInjector<State> by injector {
    /**
     * Use the default [ReduxUI.PropInjector] implementation.
     */
    constructor(store: Redux.IStore<State>) : this(ReduxUI.PropInjector(store))

    private fun runOnUIThread(runnable: () -> Unit) {
      if (Looper.myLooper() == Looper.getMainLooper()) { runnable() } else {
        Handler(Looper.getMainLooper()).post { runnable() }
      }
    }

    override fun <OP, SP, AP> injectProps(
      view: ReduxUI.IPropContainerView<State, SP, AP>,
      outProps: OP,
      mapper: ReduxUI.IPropMapper<State, OP, SP, AP>
    ): Redux.Subscription {
      return this.injector.injectProps(
        object : ReduxUI.IPropContainerView<State, SP, AP> {
          override var staticProps: ReduxUI.StaticProps<State>?
            get() = view.staticProps
            set(value) { view.staticProps = value }

          override var variableProps: ReduxUI.VariableProps<SP, AP>?
            get() = view.variableProps
            set(value) { this@PropInjector.runOnUIThread {
              view.variableProps = value }
            }
        },
        outProps, mapper)
    }

    /**
     * When [ReduxLifecycleObserver.onStop] is called, unsubscribe from
     * [State] updates.
     */
    override fun <LC, OP, SP, AP> injectProps(
      lifecycleOwner: LC,
      outProps: OP,
      mapper: ReduxUI.IPropMapper<State, OP, SP, AP>
    ) where
      LC : LifecycleOwner,
      LC : ReduxUI.IPropContainerView<State, SP, AP>
    {
      val view: ReduxUI.IPropContainerView<State, SP, AP> = lifecycleOwner
      val subscription = this.injectProps(view, outProps, mapper)
      ReduxLifecycleObserver.register(lifecycleOwner.lifecycle, subscription)
    }

    override fun injectProps(view: ReduxUI.IStaticPropContainerView<State>) {
      view.staticProps = ReduxUI.StaticProps(this, Redux.Subscription {})
    }
  }
}

/**
 * Inject props into [lifecycleOwner], which is a view that only has a mutable
 * state but handles no actions.
 */
fun <State, LC, OP, SP> AndroidReduxUI.IPropInjector<State>.injectProps(
  lifecycleOwner: LC,
  outProps: OP,
  mapper: ReduxUI.IStatePropMapper<State, OP, SP>
) where
  LC : LifecycleOwner,
  LC : ReduxUI.IPropContainerView<State, SP, Unit>
  = this.injectProps(lifecycleOwner, outProps,
  object : ReduxUI.IPropMapper<State, OP, SP, Unit> {
    override fun mapAction(
      dispatch: ReduxDispatcher,
      state: State,
      outProps: OP
    ) = Unit

    override fun mapState(state: State, outProps: OP): SP {
      return mapper.mapState(state, outProps)
    }
  })
