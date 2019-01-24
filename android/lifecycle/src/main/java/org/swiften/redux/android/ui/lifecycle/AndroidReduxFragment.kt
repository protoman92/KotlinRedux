/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.android.ui.lifecycle

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.LifecycleOwner
import org.swiften.redux.ui.IReduxPropInjector

/** Created by haipham on 2018/12/17 */
/**
 * Listen to [Fragment] lifecycle callbacks and perform [inject] when necessary. This injection
 * session automatically disposes of itself when [LifecycleCallback.onDestroy] is called.
 */
internal fun <GlobalState> IReduxPropInjector<GlobalState>.startFragmentInjection(
  activity: AppCompatActivity,
  inject: IReduxPropInjector<GlobalState>.(LifecycleOwner) -> Unit
) {
  val callback = object : FragmentManager.FragmentLifecycleCallbacks() {
    override fun onFragmentStarted(fm: FragmentManager, f: Fragment) {
      inject(this@startFragmentInjection, f)
    }
  }

  ReduxLifecycleObserver(activity, object : LifecycleCallback() {
    override fun onCreate() {
      super.onCreate()
      activity.supportFragmentManager.registerFragmentLifecycleCallbacks(callback, true)
    }

    override fun onDestroy() {
      super.onDestroy()
      activity.supportFragmentManager.unregisterFragmentLifecycleCallbacks(callback)
    }

    override fun onSafeForStartingLifecycleAwareTasks() {}
    override fun onSafeForEndingLifecycleAwareTasks() {}
  })
}
