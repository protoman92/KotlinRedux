/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package com.google.samples.apps.sunflower

import android.app.Application
import com.google.samples.apps.sunflower.dependency.IDependency
import com.google.samples.apps.sunflower.dependency.Redux
import com.google.samples.apps.sunflower.dependency.Router
import com.google.samples.apps.sunflower.utilities.InjectorUtils
import com.squareup.leakcanary.LeakCanary
import com.squareup.picasso.Picasso
import org.swiften.redux.android.ui.AndroidPropInjector
import org.swiften.redux.android.ui.lifecycle.injectActivityParcelable
import org.swiften.redux.android.ui.lifecycle.injectLifecycle
import org.swiften.redux.core.FinalStore
import org.swiften.redux.core.applyMiddlewares
import org.swiften.redux.core.createAsyncMiddleware
import org.swiften.redux.core.createRouterMiddleware
import org.swiften.redux.saga.common.createSagaMiddleware
import org.swiften.redux.thunk.createThunkMiddleware

/** Created by haipham on 2019/01/17 */
class GardenApplication : Application() {
  override fun onCreate() {
    super.onCreate()
    if (LeakCanary.isInAnalyzerProcess(this)) { return }
    LeakCanary.install(this)

    val dependency: IDependency = object : IDependency {
      override val picasso = Picasso.get()
    }

    val store = applyMiddlewares<Redux.State>(
      createAsyncMiddleware(),
      createRouterMiddleware(Router(this)),
      createSagaMiddleware(
        arrayListOf(
          arrayListOf(Redux.Saga.CoreSaga.watchNetworkConnectivity(this)),
          Redux.Saga.GardenPlantingSaga.allSagas(InjectorUtils.getGardenPlantingRepository(this)),
          Redux.Saga.PlantSaga.allSagas(InjectorUtils.getPlantRepository(this))
        ).flatten()
      ),
      createThunkMiddleware(dependency)
    )(FinalStore(Redux.State(), Redux.Reducer))

    val injector = AndroidPropInjector(store)

    injector.injectActivityParcelable(this) {
      when (it) {
        is GardenFragment -> this.injectLifecycle(dependency, it, GardenFragment)
        is PlantDetailFragment -> this.injectLifecycle(dependency, it, PlantDetailFragment)
        is PlantListFragment -> this.injectLifecycle(dependency, it, PlantListFragment)
      }
    }
  }
}
