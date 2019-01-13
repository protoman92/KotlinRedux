/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.android.ui.router

import android.app.Activity
import android.app.Application
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import org.swiften.redux.router.IReduxRouter
import org.swiften.redux.router.IReduxRouterScreen

/** Created by haipham on 2019/01/12 */
/** [IReduxRouter] that works for a single [AppCompatActivity] and multiple [Fragment] */
class SingleActivityRouter<Screen>(
  private val application: Application,
  private val navigate: (AppCompatActivity, Screen) -> Unit
) : IReduxRouter<Screen> where Screen : IReduxRouterScreen {
  private lateinit var activity: AppCompatActivity

  init {
    this.application.registerActivityLifecycleCallbacks(
      object : Application.ActivityLifecycleCallbacks {
        override fun onActivityPaused(activity: Activity?) {}
        override fun onActivityResumed(activity: Activity?) {}
        override fun onActivityStarted(activity: Activity?) {}
        override fun onActivityDestroyed(activity: Activity?) {}
        override fun onActivitySaveInstanceState(activity: Activity?, outState: Bundle?) {}
        override fun onActivityStopped(activity: Activity?) {}

        override fun onActivityCreated(activity: Activity?, savedInstanceState: Bundle?) {
          activity?.also {
            require(it is AppCompatActivity)
            this@SingleActivityRouter.activity = it
          }
        }
      })
  }

  override fun navigate(screen: Screen) = this.navigate(this.activity, screen)
}
