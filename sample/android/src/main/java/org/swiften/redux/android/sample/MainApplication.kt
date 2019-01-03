/*
 * Copyright (c) haipham 2018. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.android.sample

import android.app.Activity
import android.app.Application
import android.os.Bundle
import org.swiften.redux.android.ui.AndroidUI
import org.swiften.redux.core.SimpleReduxStore

/**
 * Created by haipham on 12/19/18.
 */
class MainApplication : Application() {
  override fun onCreate() {
    super.onCreate()
    val store = SimpleReduxStore(State(), MainRedux.Reducer)
    val injector = AndroidUI.PropInjector(store)
    val dependency = MainDependency(injector)

    this.registerActivityLifecycleCallbacks(
      object : Application.ActivityLifecycleCallbacks {
        override fun onActivityPaused(activity: Activity?) {}
        override fun onActivityResumed(activity: Activity?) {}

        override fun onActivityStarted(activity: Activity?) {
          when (activity) {
            is MainActivity -> dependency.injector.injectProps(activity)
          }
        }

        override fun onActivityStopped(activity: Activity?) {}
        override fun onActivityDestroyed(activity: Activity?) {}

        override fun onActivitySaveInstanceState(
          activity: Activity?,
          outState: Bundle?
        ) {}

        override fun onActivityCreated(
          activity: Activity?,
          savedInstanceState: Bundle?
        ) {}
      })
  }
}
