/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package com.google.samples.apps.sunflower

import android.app.Application
import com.google.samples.apps.sunflower.dependency.MainDependency
import com.google.samples.apps.sunflower.dependency.Redux
import org.swiften.redux.android.ui.core.AndroidPropInjector
import org.swiften.redux.android.ui.core.endActivityInjection
import org.swiften.redux.android.ui.core.startActivityInjection
import org.swiften.redux.store.FinalReduxStore
import org.swiften.redux.ui.injectStaticProps

/** Created by haipham on 2019/01/17 */
class GardenApplication : Application() {
  private lateinit var dependency: MainDependency
  private lateinit var activityCallbacks: ActivityLifecycleCallbacks

  override fun onCreate() {
    super.onCreate()
    val store = FinalReduxStore(Redux.State(), Redux.Reducer)
    val injector = AndroidPropInjector(store)
    val dependency = MainDependency(injector)
    this.dependency = dependency

    this.activityCallbacks = startActivityInjection(this, injector) {
      when (it) {
        is GardenActivity -> this.injectStaticProps(it)
      }
    }
  }

  override fun onTerminate() {
    super.onTerminate()
    endActivityInjection(this, this.activityCallbacks)
    this.dependency.injector.deinitialize()
  }
}
