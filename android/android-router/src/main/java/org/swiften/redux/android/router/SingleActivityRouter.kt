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
import org.swiften.redux.android.util.AndroidUtil
import org.swiften.redux.core.IRouter
import org.swiften.redux.core.IRouterScreen

/** Created by haipham on 2019/01/12 */
/**
 * [IRouter] that works for a single [AppCompatActivity] and multiple [Fragment].
 * @param AT The [AppCompatActivity] type used by the [application].
 * @param Screen The [IRouterScreen] type used by the [application].
 * @param cls The [AT] [Class] instance.
 * @param application The main [Application] instance.
 * @param runner An [AndroidUtil.IMainThreadRunner] instance.
 * @param navigate Function that performs the navigation.
 */
@PublishedApi
internal class SingleActivityRouter<AT, Screen>(
  private val cls: Class<AT>,
  private val application: Application,
  private val runner: AndroidUtil.IMainThreadRunner,
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

  override fun navigate(screen: Screen) {
    this.runner.invoke { this.activity?.let { this.navigate(it, screen) } }
  }

  override fun deinitialize() {
    this.runner.invoke { this.application.unregisterActivityLifecycleCallbacks(this.callbacks) }
  }
}

/**
 * Create a [SingleActivityRouter].
 * @param AT The [AppCompatActivity] type used by the [application].
 * @param Screen The [IRouterScreen] type used by the [application].
 * @param application The main [Application] instance.
 * @param runner An [AndroidUtil.IMainThreadRunner] instance.
 * @param navigate Function that performs the navigation.
 * @return A [SingleActivityRouter] instance.
 */
inline fun <reified AT, Screen> createSingleActivityRouter(
  application: Application,
  runner: AndroidUtil.IMainThreadRunner = AndroidUtil.MainThreadRunner,
  noinline navigate: (AT, Screen) -> Unit
): IRouter<Screen> where AT : AppCompatActivity, Screen : IRouterScreen {
  return SingleActivityRouter(AT::class.java, application, runner, navigate)
}

/**
 * Create a [SingleActivityRouter] with the default [AndroidUtil.MainThreadRunner].
 * @param AT The [AppCompatActivity] type used by the [application].
 * @param Screen The [IRouterScreen] type used by the [application].
 * @param application The main [Application] instance.
 * @param navigate Function that performs the navigation.
 * @return A [SingleActivityRouter] instance.
 */
inline fun <reified AT, Screen> createSingleActivityRouter(
  application: Application,
  noinline navigate: (AT, Screen) -> Unit
): IRouter<Screen> where AT : AppCompatActivity, Screen : IRouterScreen {
  return SingleActivityRouter(AT::class.java, application, AndroidUtil.MainThreadRunner, navigate)
}
