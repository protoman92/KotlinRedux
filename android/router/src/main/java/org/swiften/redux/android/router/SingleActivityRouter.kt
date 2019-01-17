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
import org.swiften.redux.core.IReduxDeinitializer
import org.swiften.redux.router.IReduxRouter
import org.swiften.redux.router.IReduxRouterScreen

/** Created by haipham on 2019/01/12 */
/** [IReduxRouter] that works for a single [AppCompatActivity] and multiple [Fragment] */
@PublishedApi
internal class SingleActivityRouter<AT, Screen>(
  private val application: Application,
  private val cls: Class<AT>,
  private val navigate: (AT, Screen) -> Unit
) : IReduxRouter<Screen> where AT : AppCompatActivity, Screen : IReduxRouterScreen {
  private var activity: AT? = null

  private val callbacks by lazy {
    object : Application.ActivityLifecycleCallbacks {
      override fun onActivityPaused(activity: Activity?) {}
      override fun onActivityResumed(activity: Activity?) {}
      override fun onActivityStarted(activity: Activity?) {}
      override fun onActivityDestroyed(activity: Activity?) {}
      override fun onActivitySaveInstanceState(activity: Activity?, outState: Bundle?) {}
      override fun onActivityStopped(activity: Activity?) {}

      override fun onActivityCreated(activity: Activity?, savedInstanceState: Bundle?) {
        activity?.also {
          require(this@SingleActivityRouter.cls.isInstance(activity))
          this@SingleActivityRouter.activity = this@SingleActivityRouter.cls.cast(it)
        }
      }
    }
  }

  init { this.application.registerActivityLifecycleCallbacks(this.callbacks) }

  override val deinitialize: IReduxDeinitializer get() = {
    this.application.unregisterActivityLifecycleCallbacks(this.callbacks)
  }

  override fun navigate(screen: Screen) {
    this.activity?.also { this.navigate(it, screen) }
  }
}

/** Create a [SingleActivityRouter] */
inline fun <reified AT, Screen> createSingleActivityRouter(
  application: Application,
  noinline navigate: (AT, Screen) -> Unit
) : IReduxRouter<Screen> where AT : AppCompatActivity, Screen : IReduxRouterScreen =
  SingleActivityRouter(application, AT::class.java, navigate)
