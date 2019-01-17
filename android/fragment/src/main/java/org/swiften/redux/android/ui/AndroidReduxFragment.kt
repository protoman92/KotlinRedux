/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.android.ui

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import org.swiften.redux.ui.IReduxPropContainer
import org.swiften.redux.ui.StaticProps

/** Created by haipham on 2018/12/17 */
/** Listen to [Fragment] lifecycle callbacks and perform [inject] when necessary */
fun <State, Activity> startFragmentInjection(
  activity: Activity,
  inject: StaticProps<State>.(Fragment) -> Unit
): FragmentManager.FragmentLifecycleCallbacks where
  Activity : AppCompatActivity,
  Activity : IReduxPropContainer<State, *, *>
{
  val callback = object : FragmentManager.FragmentLifecycleCallbacks() {
    override fun onFragmentStarted(fm: FragmentManager, f: Fragment) {
      activity.reduxProps.static.also { inject(it, f) }
    }
  }

  activity.supportFragmentManager.registerFragmentLifecycleCallbacks(callback, true)
  return callback
}

/** End lifecycle [callback] for [Fragment] */
fun endFragmentInjection(
  activity: AppCompatActivity,
  callback: FragmentManager.FragmentLifecycleCallbacks
) = activity.supportFragmentManager.unregisterFragmentLifecycleCallbacks(callback)
