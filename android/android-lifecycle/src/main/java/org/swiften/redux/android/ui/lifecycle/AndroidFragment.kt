/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.android.ui.lifecycle

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.LifecycleOwner
import org.swiften.redux.ui.IPropInjector

/** Created by haipham on 2018/12/17 */
/** Interface to wrap some functionalities for [AppCompatActivity]. */
internal interface IAppCompatActivity : LifecycleOwner {
  val supportFragmentManager: FragmentManager
}

/**
 * Wrap an [AppCompatActivity] to implement [IAppCompatActivity].
 * @param activity An [AppCompatActivity] instance.
 */
class AppCompatActivityWrapper(private val activity: AppCompatActivity) : IAppCompatActivity {
  override val supportFragmentManager = this.activity.supportFragmentManager
  override fun getLifecycle() = this.activity.lifecycle
  override fun toString() = this.activity.toString()
}

/**
 * Listen to [Fragment] lifecycle callbacks and perform [inject] when necessary. This injection
 * session automatically disposes of itself when [ReduxLifecycleObserver.onDestroy] is called.
 * @receiver An [IPropInjector] instance.
 * @param GState The global state type.
 * @param activity An [IAppCompatActivity] instance.
 * @param inject Function that performs injections on [LifecycleOwner] instances passing through.
 */
internal fun <GState> IPropInjector<GState>.injectFragment(
  activity: IAppCompatActivity,
  inject: IPropInjector<GState>.(LifecycleOwner) -> Unit
) where GState : Any {
  val callback = object : FragmentManager.FragmentLifecycleCallbacks() {
    override fun onFragmentStarted(fm: FragmentManager, f: Fragment) {
      inject(this@injectFragment, f)
    }
  }

  object : ReduxLifecycleObserver(activity, object : ILifecycleCallback {
    override fun onSafeForStartingLifecycleAwareTasks() {}
    override fun onSafeForEndingLifecycleAwareTasks() {}
  }) {
    override fun onCreate() {
      super.onCreate()
      activity.supportFragmentManager.registerFragmentLifecycleCallbacks(callback, true)
    }

    override fun onDestroy() {
      super.onDestroy()
      activity.supportFragmentManager.unregisterFragmentLifecycleCallbacks(callback)
    }
  }
}

/**
 * Call [injectFragment] with an [AppCompatActivity].
 * @param GState The global state type.
 * @param activity An [AppCompatActivity] instance.
 * @param inject Function that performs injections on [LifecycleOwner] instances passing through.
 */
internal fun <GState> IPropInjector<GState>.injectFragment(
  activity: AppCompatActivity,
  inject: IPropInjector<GState>.(LifecycleOwner) -> Unit
) where GState : Any {
  return this.injectFragment(AppCompatActivityWrapper(activity), inject)
}
