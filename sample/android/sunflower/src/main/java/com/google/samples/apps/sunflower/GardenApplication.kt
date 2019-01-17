/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package com.google.samples.apps.sunflower

import android.app.Application
import com.google.samples.apps.sunflower.dependency.Dependency
import com.google.samples.apps.sunflower.dependency.Redux
import com.google.samples.apps.sunflower.dependency.Router
import com.google.samples.apps.sunflower.dependency.Saga
import com.google.samples.apps.sunflower.utilities.InjectorUtils
import org.swiften.redux.android.ui.AndroidPropInjector
import org.swiften.redux.android.ui.endActivityInjection
import org.swiften.redux.android.ui.startActivityInjection
import org.swiften.redux.middleware.applyReduxMiddlewares
import org.swiften.redux.router.createRouterMiddleware
import org.swiften.redux.saga.createSagaMiddleware
import org.swiften.redux.store.FinalReduxStore
import org.swiften.redux.ui.injectStaticProps

/** Created by haipham on 2019/01/17 */
class GardenApplication : Application() {
  private lateinit var dependency: Dependency
  private lateinit var activityCallbacks: ActivityLifecycleCallbacks

  override fun onCreate() {
    super.onCreate()

    val store = applyReduxMiddlewares(
      createRouterMiddleware(Router(this)),
      createSagaMiddleware<Redux.State>(
        Saga.Plant.allSagas(InjectorUtils.getPlantRepository(this))
      )
    )(FinalReduxStore(Redux.State(), Redux.Reducer))

    val injector = AndroidPropInjector(store)
    val dependency = Dependency(injector)
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
