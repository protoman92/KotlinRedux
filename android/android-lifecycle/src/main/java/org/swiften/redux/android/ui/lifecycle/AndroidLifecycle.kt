/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.android.ui.lifecycle

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import org.swiften.redux.core.IReduxSubscription
import org.swiften.redux.ui.IPropContainer
import org.swiften.redux.ui.IPropInjector
import org.swiften.redux.ui.IPropLifecycleOwner
import org.swiften.redux.ui.IPropMapper
import org.swiften.redux.ui.ReduxProps

/** Created by haipham on 2018/12/17 */
/** Callback for use with [LifecycleObserver] */
interface ILifecycleCallback {
  /**
   * This method will be called when it is safe to perform lifecycle-aware tasks, such as
   * [IPropInjector.inject].
   */
  fun onSafeForStartingLifecycleAwareTasks()

  /**
   * This method will be called when it is safe to terminate lifecycle-aware tasks, such as
   * [IReduxSubscription.unsubscribe].
   */
  fun onSafeForEndingLifecycleAwareTasks()
}

/** Use this [LifecycleObserver] to unsubscribe from a [IReduxSubscription] */
@Suppress("unused")
open class LifecycleObserver(
  private val lifecycleOwner: LifecycleOwner,
  private val callback: ILifecycleCallback
) : LifecycleObserver, ILifecycleCallback {
  init { this.lifecycleOwner.lifecycle.addObserver(this) }

  @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
  open fun onCreate() {}

  @OnLifecycleEvent(Lifecycle.Event.ON_START)
  fun onStart() {
    this.onSafeForStartingLifecycleAwareTasks()
  }

  @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
  open fun onResume() {}

  @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
  open fun onPause() {}

  @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
  fun onStop() {
    this.onSafeForEndingLifecycleAwareTasks()
  }

  @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
  open fun onDestroy() {}

  override fun onSafeForStartingLifecycleAwareTasks() {
    this.callback.onSafeForStartingLifecycleAwareTasks()
    this.lifecycleOwner.lifecycle.addObserver(this)
  }

  override fun onSafeForEndingLifecycleAwareTasks() {
    this.callback.onSafeForEndingLifecycleAwareTasks()
    this.lifecycleOwner.lifecycle.removeObserver(this)
  }

  override fun toString() = this.callback.toString()
}

/** Call [IPropInjector.inject] for [lifecycleOwner] */
fun <GlobalState, LC, OP, S, A> IPropInjector<GlobalState>.injectLifecycle(
  lifecycleOwner: LC,
  outProps: OP,
  mapper: IPropMapper<GlobalState, OP, S, A>
): LC where
  LC : LifecycleOwner,
  LC : IPropContainer<GlobalState, S, A>,
  LC : IPropLifecycleOwner<GlobalState> {
  var subscription: IReduxSubscription? = null

  /**
   * We perform [IPropInjector.inject] in [ILifecycleCallback.onStart] because by then
   * the views would have been initialized, and thus can be accessed in
   * [IPropLifecycleOwner.beforePropInjectionStarts]. To mirror this, unsubscription is done
   * in [ILifecycleCallback.onStop] because said views are not destroyed yet.
   */
  LifecycleObserver(lifecycleOwner, object : ILifecycleCallback {
    override fun onSafeForStartingLifecycleAwareTasks() {
      subscription = inject(
        object : IPropContainer<GlobalState, S, A> by lifecycleOwner {
          override var reduxProps: ReduxProps<GlobalState, S, A>
            get() = lifecycleOwner.reduxProps

            /**
             * If [Lifecycle.getCurrentState] is [Lifecycle.State.DESTROYED], do not set
             * [IPropContainer.reduxProps] since there's no point in doing so.
             */
            set(value) = lifecycleOwner.lifecycle.currentState
              .takeUnless { it == Lifecycle.State.DESTROYED }
              .let { lifecycleOwner.reduxProps = value }

          override fun toString() = lifecycleOwner.toString()
        },
        outProps, mapper
      )
    }

    override fun onSafeForEndingLifecycleAwareTasks() { subscription?.unsubscribe() }
  })

  return lifecycleOwner
}

/** Call [IPropInjector.inject] for [lifecycleOwner] but it also implements [IPropMapper] */
fun <GlobalState, LC, OP, S, A> IPropInjector<GlobalState>.injectLifecycle(
  lifecycleOwner: LC,
  outProps: OP
): LC where
  LC : LifecycleOwner,
  LC : IPropContainer<GlobalState, S, A>,
  LC : IPropLifecycleOwner<GlobalState>,
  LC : IPropMapper<GlobalState, OP, S, A> =
  this.injectLifecycle(lifecycleOwner, outProps, lifecycleOwner)

/**
 * Call [IPropInjector.inject] for [lifecycleOwner] but it also implements [IPropMapper] and
 * out props is [Unit].
 */
fun <GlobalState, LC, S, A> IPropInjector<GlobalState>.injectLifecycle(lifecycleOwner: LC): LC where
  LC : LifecycleOwner,
  LC : IPropContainer<GlobalState, S, A>,
  LC : IPropLifecycleOwner<GlobalState>,
  LC : IPropMapper<GlobalState, Unit, S, A> =
  this.injectLifecycle(lifecycleOwner, Unit)
