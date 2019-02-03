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
/** Interface to wrap some functionalities for [AppCompatActivity] */
internal interface IAppCompatActivity : LifecycleOwner {
  val supportFragmentManager: FragmentManager
}

/** Wrap an [AppCompatActivity] to implement [IAppCompatActivity] */
class AppCompatActivityWrapper(private val activity: AppCompatActivity) : IAppCompatActivity {
  override val supportFragmentManager = this.activity.supportFragmentManager
  override fun getLifecycle() = this.activity.lifecycle
  override fun toString() = this.activity.toString()
}

/**
 * Listen to [Fragment] lifecycle callbacks and perform [inject] when necessary. This injection
 * session automatically disposes of itself when [ILifecycleCallback.onDestroy] is called.
 */
internal fun <GlobalState, GlobalExt> IPropInjector<GlobalState, GlobalExt>.injectFragment(
  activity: IAppCompatActivity,
  inject: IPropInjector<GlobalState, GlobalExt>.(LifecycleOwner) -> Unit
) {
  val callback = object : FragmentManager.FragmentLifecycleCallbacks() {
    override fun onFragmentStarted(fm: FragmentManager, f: Fragment) {
      inject(this@injectFragment, f)
    }
  }

  object : LifecycleObserver(activity, object : ILifecycleCallback {
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

/** Call [injectFragment] with an [AppCompatActivity] */
internal fun <GlobalState, GlobalExt> IPropInjector<GlobalState, GlobalExt>.injectFragment(
  activity: AppCompatActivity,
  inject: IPropInjector<GlobalState, GlobalExt>.(LifecycleOwner) -> Unit
) {
  return this.injectFragment(AppCompatActivityWrapper(activity), inject)
}
