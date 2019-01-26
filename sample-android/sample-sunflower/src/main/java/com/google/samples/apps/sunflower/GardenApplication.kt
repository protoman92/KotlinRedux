/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package com.google.samples.apps.sunflower

import android.app.Application
import com.google.samples.apps.sunflower.dependency.Redux
import com.google.samples.apps.sunflower.dependency.Router
import com.google.samples.apps.sunflower.utilities.InjectorUtils
import com.squareup.leakcanary.LeakCanary
import org.swiften.redux.android.ui.AndroidPropInjector
import org.swiften.redux.android.ui.lifecycle.injectLifecycleOwner
import org.swiften.redux.android.ui.lifecycle.injectParcelableInjections
import org.swiften.redux.middleware.applyMiddlewares
import org.swiften.redux.router.createRouterMiddleware
import org.swiften.redux.saga.common.createSagaMiddleware
import org.swiften.redux.store.FinalStore

/** Created by haipham on 2019/01/17 */
class GardenApplication : Application() {
  override fun onCreate() {
    super.onCreate()
    if (LeakCanary.isInAnalyzerProcess(this)) { return }
    LeakCanary.install(this)

    val store = applyMiddlewares(
      createRouterMiddleware(Router(this)),
      createSagaMiddleware<Redux.State>(
        arrayListOf(
          arrayListOf(Redux.Saga.CoreSaga.watchNetworkConnectivity(this)),
          Redux.Saga.GardenPlantingSaga.allSagas(InjectorUtils.getGardenPlantingRepository(this)),
          Redux.Saga.PlantSaga.allSagas(InjectorUtils.getPlantRepository(this))
        ).flatten()
      )
    )(FinalStore(Redux.State(), Redux.Reducer))

    val injector = AndroidPropInjector(store)

    injector.injectParcelableInjections(this) {
      when (it) {
        is GardenFragment -> this.injectLifecycleOwner(it, Unit, it)
        is PlantDetailFragment -> this.injectLifecycleOwner(it, Unit, it)
        is PlantListFragment -> this.injectLifecycleOwner(it, Unit, it)
      }
    }
  }
}