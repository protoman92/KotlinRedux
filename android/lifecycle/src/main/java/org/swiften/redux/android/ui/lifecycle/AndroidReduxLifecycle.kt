/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.android.ui.lifecycle

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import org.swiften.redux.core.ReduxSubscription
import org.swiften.redux.ui.IReduxPropContainer
import org.swiften.redux.ui.IReduxPropInjector
import org.swiften.redux.ui.IReduxPropLifecycleOwner
import org.swiften.redux.ui.IReduxPropMapper
import org.swiften.redux.ui.ReduxProps

/** Created by haipham on 2018/12/17 */
/** Callbacks for lifecycle for use with [LifecycleObserver] */
interface ILifecycleCallback {
  /** Called on [Lifecycle.Event.ON_CREATE] */
  fun onCreate()

  /** Called on [Lifecycle.Event.ON_START] */
  fun onStart()

  /** Called on [Lifecycle.Event.ON_RESUME] */
  fun onResume()

  /** Called on [Lifecycle.Event.ON_PAUSE] */
  fun onPause()

  /** Called on [Lifecycle.Event.ON_STOP] */
  fun onStop()

  /** Called on [Lifecycle.Event.ON_DESTROY] */
  fun onDestroy()
}

/** Empty implementation for [ILifecycleCallback] to use with delegates */
object EmptyLifecycleCallback : ILifecycleCallback {
  override fun onCreate() {}
  override fun onStart() {}
  override fun onResume() {}
  override fun onPause() {}
  override fun onStop() {}
  override fun onDestroy() {}
}

/** Use this [LifecycleObserver] to unsubscribe from a [ReduxSubscription] */
internal class ReduxLifecycleObserver(
  private val lifecycleOwner: LifecycleOwner,
  private val callback: ILifecycleCallback
) : LifecycleObserver, ILifecycleCallback by callback {
  init { lifecycleOwner.lifecycle.addObserver(this) }

  @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
  override fun onCreate() { this.callback.onCreate() }

  @OnLifecycleEvent(Lifecycle.Event.ON_START)
  override fun onStart() { this.callback.onStart() }

  @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
  override fun onResume() { this.callback.onResume() }

  @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
  override fun onPause() { this.callback.onPause() }

  @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
  override fun onStop() { this.callback.onStop() }

  @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
  override fun onDestroy() {
    this.callback.onDestroy()
    this.lifecycleOwner.lifecycle.removeObserver(this)
  }
}

/** Call [IReduxPropInjector.injectProps] for [lifecycleOwner] */
fun <State, LC, OP, SP, AP> IReduxPropInjector<State>.injectLifecycleProps(
  lifecycleOwner: LC,
  outProps: OP,
  mapper: IReduxPropMapper<State, OP, SP, AP>
): LC where
  LC : LifecycleOwner,
  LC : IReduxPropContainer<State, SP, AP>,
  LC : IReduxPropLifecycleOwner<State>
{
  var subscription: ReduxSubscription? = null

  /**
   * We perform [IReduxPropInjector.injectProps] in [ILifecycleCallback.onStart] because by then
   * the views would have been initialized, and thus can be accessed in
   * [IReduxPropLifecycleOwner.beforePropInjectionStarts]. To mirror this, unsubscription is done
   * in [ILifecycleCallback.onStop] because said views are not destroyed yet.
   */
  ReduxLifecycleObserver(lifecycleOwner, object : ILifecycleCallback by EmptyLifecycleCallback {
    override fun onStart() {
      subscription = this@injectLifecycleProps.injectProps(
        object : IReduxPropContainer<State, SP, AP> by lifecycleOwner {
          override var reduxProps: ReduxProps<State, SP, AP>?
            get() = lifecycleOwner.reduxProps

            /**
             * If [Lifecycle.getCurrentState] is [Lifecycle.State.DESTROYED], do not set
             * [IReduxPropContainer.reduxProps] since there's no point in doing so.
             */
            set(value) {
              if (lifecycleOwner.lifecycle.currentState != Lifecycle.State.DESTROYED) {
                lifecycleOwner.reduxProps = value
              }
            }
        },
        outProps, mapper
      )
    }

    override fun onStop() { subscription?.unsubscribe() }
  })

  return lifecycleOwner
}
