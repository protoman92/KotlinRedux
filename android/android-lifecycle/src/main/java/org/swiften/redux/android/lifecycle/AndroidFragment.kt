/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.android.lifecycle

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import org.swiften.redux.ui.IFullPropInjector
import org.swiften.redux.ui.IPropInjector
import org.swiften.redux.ui.IPropLifecycleOwner

/** Created by haipham on 2018/12/17 */
/**
 * Listen to [Fragment] lifecycle callbacks and perform [injectionHelper] when necessary. This injection
 * session automatically disposes of itself when [ReduxLifecycleObserver.onDestroy] is called.
 * @receiver An [IPropInjector] instance.
 * @param GState The global state type.
 * @param activity An [IAppCompatActivity] instance.
 * @param injectionHelper An [ILifecycleInjectionHelper] instance.
 * @return A [FragmentManager.FragmentLifecycleCallbacks] instance.
 */
internal fun <GState> IPropInjector<GState>.injectFragment(
  activity: IAppCompatActivity,
  injectionHelper: ILifecycleInjectionHelper<GState>
): FragmentManager.FragmentLifecycleCallbacks {
  val callback = object : FragmentManager.FragmentLifecycleCallbacks() {
    /**
     * We perform [IFullPropInjector.inject] in [onFragmentStarted] because by then the views
     * would have been initialized, and thus can be accessed in
     * [IPropLifecycleOwner.beforePropInjectionStarts]. To mirror this, unsubscription is done in
     * [onFragmentStopped] because said views are not destroyed yet.
     */
    override fun onFragmentStarted(fm: FragmentManager, f: Fragment) {
      super.onFragmentStarted(fm, f)
      injectionHelper.inject(this@injectFragment, f)
    }

    override fun onFragmentStopped(fm: FragmentManager, f: Fragment) {
      super.onFragmentStopped(fm, f)
      injectionHelper.deinitialize(f)
    }
  }

  object : ReduxLifecycleObserver(activity, NoopLifecycleCallback) {
    override fun onCreate() {
      super.onCreate()
      activity.supportFragmentManager.registerFragmentLifecycleCallbacks(callback, true)
    }

    override fun onDestroy() {
      super.onDestroy()
      activity.supportFragmentManager.unregisterFragmentLifecycleCallbacks(callback)
    }
  }

  return callback
}

/**
 * Call [injectFragment] with an [AppCompatActivity].
 * @receiver An [IPropInjector] instance.
 * @param GState The global state type.
 * @param activity An [AppCompatActivity] instance.
 * @param injectionHelper An [ILifecycleInjectionHelper] instance.
 * @return A [FragmentManager.FragmentLifecycleCallbacks] instance.
 */
fun <GState> IPropInjector<GState>.injectFragment(
  activity: AppCompatActivity,
  injectionHelper: ILifecycleInjectionHelper<GState>
): FragmentManager.FragmentLifecycleCallbacks {
  return this.injectFragment(AppCompatActivityWrapper(activity), injectionHelper)
}
