/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.android.dagger

import androidx.multidex.MultiDexApplication
import org.swiften.redux.android.ui.AndroidPropInjector
import org.swiften.redux.android.lifecycle.injectActivitySerializable
import org.swiften.redux.core.FinalStore
import org.swiften.redux.core.RouterMiddleware
import org.swiften.redux.core.applyMiddlewares
import org.swiften.redux.saga.SagaMiddleware

/** Created by haipham on 26/1/19 */
class MainApplication : MultiDexApplication() {
  override fun onCreate() {
    super.onCreate()

    val component = DaggerMainComponent.builder()
      .dependencyLevel2Module(DependencyLevel2Module())
      .dependencyLevel3Module(DependencyLevel3Module())
      .build()

    val store = applyMiddlewares<Redux.State>(
      RouterMiddleware.create(Router(this)),
      SagaMiddleware.create(effects = Redux.Saga.allSagas(component))
    )(FinalStore(Redux.State(), Redux.Reducer))

    val injector = AndroidPropInjector(store)
    val injectionHelper = InjectionHelper(component)
    injector.injectActivitySerializable(this, injectionHelper)
  }
}
