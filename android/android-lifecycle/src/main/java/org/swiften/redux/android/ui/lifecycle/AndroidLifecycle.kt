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
import org.swiften.redux.ui.IFullPropInjector
import org.swiften.redux.ui.IPropContainer
import org.swiften.redux.ui.IPropInjector
import org.swiften.redux.ui.IPropLifecycleOwner
import org.swiften.redux.ui.IPropMapper
import org.swiften.redux.ui.ReduxProp
import org.swiften.redux.ui.StaticProp
import org.swiften.redux.ui.inject

/** Created by haipham on 2018/12/17 */
/** Callback for use with [LifecycleObserver]. */
interface ILifecycleCallback {
  /**
   * This method will be called when it is safe to perform lifecycle-aware tasks, such as
   * [IFullPropInjector.inject].
   */
  fun onSafeForStartingLifecycleAwareTasks()

  /**
   * This method will be called when it is safe to terminate lifecycle-aware tasks, such as
   * [IReduxSubscription.unsubscribe].
   */
  fun onSafeForEndingLifecycleAwareTasks()
}

/**
 * Use this [LifecycleObserver] to unsubscribe from a [IReduxSubscription].
 * @param lifecycleOwner A [LifecycleOwner] instance.
 * @param [callback] An [ILifecycleCallback] instance.
 */
@Suppress("unused")
open class ReduxLifecycleObserver(
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

/**
 * Call [IFullPropInjector.inject] for [lifecycleOwner].
 * @receiver An [IPropInjector] instance.
 * @param GState The global state type.
 * @param LState The local state type that [GState] must extend from.
 * @param Owner [LifecycleOwner] type that also implements [IPropContainer].
 * @param OutProp Property as defined by [lifecycleOwner]'s parent.
 * @param State See [ReduxProp.state].
 * @param Action See [ReduxProp.action].
 * @param outProp An [OutProp] instance.
 * @param lifecycleOwner A [Owner] instance.
 * @param mapper An [IPropMapper] instance.
 * @return The injected [Owner] instance.
 */
fun <GState, LState, Owner, OutProp, State, Action> IPropInjector<GState>.injectLifecycle(
  outProp: OutProp,
  lifecycleOwner: Owner,
  mapper: IPropMapper<LState, OutProp, State, Action>
): Owner where
  GState : LState,
  LState : Any,
  Owner : LifecycleOwner,
  Owner : IPropContainer<State, Action>,
  Owner : IPropLifecycleOwner<LState, OutProp>,
  State : Any,
  Action : Any {
  var subscription: IReduxSubscription? = null

  /**
   * We perform [IFullPropInjector.inject] in [ReduxLifecycleObserver.onStart] because by then
   * the views would have been initialized, and thus can be accessed in
   * [IPropLifecycleOwner.beforePropInjectionStarts]. To mirror this, unsubscription is done in
   * [ReduxLifecycleObserver.onStop] because said views are not destroyed yet.
   */
  ReduxLifecycleObserver(lifecycleOwner, object : ILifecycleCallback {
    override fun onSafeForStartingLifecycleAwareTasks() {
      subscription = this@injectLifecycle.inject(outProp,
        object : IPropContainer<State, Action>, IPropLifecycleOwner<LState, OutProp> {
          override var reduxProp: ReduxProp<State, Action>
            get() = lifecycleOwner.reduxProp

            /**
             * If [Lifecycle.getCurrentState] is [Lifecycle.State.DESTROYED], do not set
             * [IPropContainer.reduxProp] since there's no point in doing so.
             */
            set(value) = lifecycleOwner.lifecycle.currentState
              .takeUnless { it == Lifecycle.State.DESTROYED }
              .let { lifecycleOwner.reduxProp = value }

          override fun toString() = lifecycleOwner.toString()

          override fun beforePropInjectionStarts(sp: StaticProp<LState, OutProp>) {
            lifecycleOwner.beforePropInjectionStarts(sp)
          }

          override fun afterPropInjectionEnds() {
            lifecycleOwner.afterPropInjectionEnds()
          }
        }, mapper)
    }

    override fun onSafeForEndingLifecycleAwareTasks() {
      subscription?.unsubscribe()
    }
  })

  return lifecycleOwner
}
