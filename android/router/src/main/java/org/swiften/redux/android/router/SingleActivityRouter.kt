/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.android.router

import android.app.Activity
import android.app.Application
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import org.swiften.redux.core.IDeinitializer
import org.swiften.redux.router.IRouter
import org.swiften.redux.router.IRouterScreen

/** Created by haipham on 2019/01/12 */
/** [IRouter] that works for a single [AppCompatActivity] and multiple [Fragment] */
@PublishedApi
internal class SingleActivityRouter<AT, Screen>(
  private val application: Application,
  private val cls: Class<AT>,
  private val navigate: (AT, Screen) -> Unit
) : IRouter<Screen> where AT : AppCompatActivity, Screen : IRouterScreen {
  private var activity: AT? = null

  private val callbacks by lazy {
    object : Application.ActivityLifecycleCallbacks {
      override fun onActivityPaused(activity: Activity?) {}
      override fun onActivityResumed(activity: Activity?) {}

      override fun onActivityStarted(activity: Activity?) {
        activity?.also {
          require(this@SingleActivityRouter.cls.isInstance(activity))
          this@SingleActivityRouter.activity = this@SingleActivityRouter.cls.cast(it)
        }
      }

      override fun onActivityStopped(activity: Activity?) {
        this@SingleActivityRouter.activity = null
      }

      override fun onActivitySaveInstanceState(activity: Activity?, outState: Bundle?) {}
      override fun onActivityCreated(activity: Activity?, savedInstanceState: Bundle?) {}
      override fun onActivityDestroyed(activity: Activity?) {}
    }
  }

  init { this.application.registerActivityLifecycleCallbacks(this.callbacks) }

  override val deinitialize: IDeinitializer get() = {
    this.application.unregisterActivityLifecycleCallbacks(this.callbacks)
  }

  override fun navigate(screen: Screen) {
    this.activity?.let { this.navigate(it, screen) }
  }
}

/** Create a [SingleActivityRouter] */
inline fun <reified AT, Screen> createSingleActivityRouter(
  application: Application,
  noinline navigate: (AT, Screen) -> Unit
): IRouter<Screen> where AT : AppCompatActivity, Screen : IRouterScreen =
  SingleActivityRouter(application, AT::class.java, navigate)
