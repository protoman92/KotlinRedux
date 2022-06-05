/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.android.lifecycle

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import org.swiften.redux.core.IReduxSubscription
import org.swiften.redux.ui.IFullPropInjector
import org.swiften.redux.ui.IPropLifecycleOwner

/**
 * Use this [LifecycleObserver] to unsubscribe from a [IReduxSubscription].
 * @param lifecycleOwner A [LifecycleOwner] instance.
 * @param [callback] An [ILifecycleCallback] instance.
 */
open class ReduxLifecycleObserver(
  private val lifecycleOwner: LifecycleOwner,
  private val callback: ILifecycleCallback
) : LifecycleObserver, ILifecycleCallback {
  init { this.lifecycleOwner.lifecycle.addObserver(this) }

  //region ILifecycleCallback
  override fun onSafeForStartingLifecycleAwareTasks() {
    this.callback.onSafeForStartingLifecycleAwareTasks()
    this.lifecycleOwner.lifecycle.addObserver(this)
  }

  override fun onSafeForEndingLifecycleAwareTasks() {
    this.callback.onSafeForEndingLifecycleAwareTasks()
    this.lifecycleOwner.lifecycle.removeObserver(this)
  }
  //endregion

  @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
  open fun onCreate() {}

  /**
   * We perform [IFullPropInjector.inject] in [ReduxLifecycleObserver.onStart] because by then
   * the views would have been initialized, and thus can be accessed in
   * [IPropLifecycleOwner.beforePropInjectionStarts]. To mirror this, unsubscription is done in
   * [ReduxLifecycleObserver.onStop] because said views are not destroyed yet.
   */
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

  @OnLifecycleEvent(Lifecycle.Event.ON_ANY)
  open fun onAny() {

  }

  override fun toString() = this.callback.toString()
}
