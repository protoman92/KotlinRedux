/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.android.ui.lifecycle

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import org.swiften.redux.core.ReduxSubscription
import org.swiften.redux.ui.IReduxPropInjector

/** Created by haipham on 2018/12/17 */
/** Use this [LifecycleObserver] to unsubscribe from a [ReduxSubscription] */
internal class FragmentInjectionLifecycleObserver(
  private val lifecycleOwner: LifecycleOwner,
  private val callback: LifecycleCallback
) : LifecycleObserver, LifecycleCallback by callback {
  init { this.lifecycleOwner.lifecycle.addObserver(this) }

  @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
  override fun onCreate() {
    this.callback.onCreate()
  }

  @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
  override fun onDestroy() {
    this.callback.onDestroy()
    this.lifecycleOwner.lifecycle.removeObserver(this)
  }
}

/**
 * Listen to [Fragment] lifecycle callbacks and perform [inject] when necessary. This injection
 * session automatically disposes of itself when [LifecycleCallback.onDestroy] is called.
 */
internal fun <State> IReduxPropInjector<State>.startFragmentInjection(
  activity: AppCompatActivity,
  inject: IReduxPropInjector<State>.(LifecycleOwner) -> Unit
) {
  val callback = object : FragmentManager.FragmentLifecycleCallbacks() {
    override fun onFragmentStarted(fm: FragmentManager, f: Fragment) {
      inject(this@startFragmentInjection, f)
    }
  }

  FragmentInjectionLifecycleObserver(activity, object : LifecycleCallback {
    override fun onCreate() {
      activity.supportFragmentManager.registerFragmentLifecycleCallbacks(callback, true)
    }

    override fun onDestroy() {
      activity.supportFragmentManager.unregisterFragmentLifecycleCallbacks(callback)
    }

    override fun onStart() {}
    override fun onResume() {}
    override fun onPause() {}
    override fun onStop() {}
  })
}
