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
import org.swiften.redux.ui.IPropContainer
import org.swiften.redux.ui.IPropInjector
import org.swiften.redux.ui.IPropLifecycleOwner
import org.swiften.redux.ui.IPropMapper
import org.swiften.redux.ui.ReduxProps

/** Created by haipham on 2018/12/17 */
/** Callbacks for lifecycle for use with [LifecycleObserver] */
abstract class LifecycleCallback {
  /** Called on [Lifecycle.Event.ON_CREATE] */
  open fun onCreate() {}

  /** Called on [Lifecycle.Event.ON_START] */
  open fun onStart() { this.onSafeForStartingLifecycleAwareTasks() }

  /** Called on [Lifecycle.Event.ON_RESUME] */
  open fun onResume() {}

  /** Called on [Lifecycle.Event.ON_PAUSE] */
  open fun onPause() {}

  /** Called on [Lifecycle.Event.ON_STOP] */
  open fun onStop() { this.onSafeForEndingLifecycleAwareTasks() }

  /** Called on [Lifecycle.Event.ON_DESTROY] */
  open fun onDestroy() {}

  /**
   * This method will be called when it is safe to perform lifecycle-aware tasks, such as
   * [IPropInjector.injectProps].
   */
  abstract fun onSafeForStartingLifecycleAwareTasks()

  /**
   * This method will be called when it is safe to terminate lifecycle-aware tasks, such as
   * [ReduxSubscription.unsubscribe].
   */
  abstract fun onSafeForEndingLifecycleAwareTasks()
}

/** Use this [LifecycleObserver] to unsubscribe from a [ReduxSubscription] */
class LifecycleObserver(
  private val lifecycleOwner: LifecycleOwner,
  private val callback: LifecycleCallback
) : LifecycleObserver, LifecycleCallback() {
  init { this.lifecycleOwner.lifecycle.addObserver(this) }

  @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
  override fun onCreate() {
    super.onCreate()
    this.callback.onCreate()
  }

  @OnLifecycleEvent(Lifecycle.Event.ON_START)
  override fun onStart() {
    super.onStart()
    this.callback.onStart()
  }

  @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
  override fun onResume() {
    super.onResume()
    this.callback.onResume()
  }

  @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
  override fun onPause() {
    super.onPause()
    this.callback.onPause()
  }

  @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
  override fun onStop() {
    super.onStop()
    this.callback.onStop()
  }

  @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
  override fun onDestroy() {
    super.onDestroy()
    this.callback.onDestroy()
  }

  override fun onSafeForStartingLifecycleAwareTasks() {
    this.callback.onSafeForStartingLifecycleAwareTasks()
    this.lifecycleOwner.lifecycle.addObserver(this)
  }

  override fun onSafeForEndingLifecycleAwareTasks() {
    this.callback.onSafeForEndingLifecycleAwareTasks()
    this.lifecycleOwner.lifecycle.removeObserver(this)
  }
}

/** Call [IPropInjector.injectProps] for [lifecycleOwner] */
fun <GlobalState, LC, OP, S, A> IPropInjector<GlobalState>.injectLifecycleProps(
  lifecycleOwner: LC,
  outProps: OP,
  mapper: IPropMapper<GlobalState, OP, S, A>
): LC where
  LC : LifecycleOwner,
  LC : IPropContainer<GlobalState, S, A>,
  LC : IPropLifecycleOwner<GlobalState> {
  var subscription: ReduxSubscription? = null

  /**
   * We perform [IPropInjector.injectProps] in [LifecycleCallback.onStart] because by then
   * the views would have been initialized, and thus can be accessed in
   * [IPropLifecycleOwner.beforePropInjectionStarts]. To mirror this, unsubscription is done
   * in [LifecycleCallback.onStop] because said views are not destroyed yet.
   */
  LifecycleObserver(lifecycleOwner, object : LifecycleCallback() {
    override fun onSafeForStartingLifecycleAwareTasks() {
      subscription = injectProps(
        object : IPropContainer<GlobalState, S, A> by lifecycleOwner {
          override var reduxProps: ReduxProps<GlobalState, S, A>?
            get() = lifecycleOwner.reduxProps

            /**
             * If [Lifecycle.getCurrentState] is [Lifecycle.State.DESTROYED], do not set
             * [IPropContainer.reduxProps] since there's no point in doing so.
             */
            set(value) {
              lifecycleOwner.lifecycle.currentState
                .takeUnless { it == Lifecycle.State.DESTROYED }
                .also { lifecycleOwner.reduxProps = value }
            }
        },
        outProps, mapper
      )
    }

    override fun onSafeForEndingLifecycleAwareTasks() { subscription?.unsubscribe() }
  })

  return lifecycleOwner
}
