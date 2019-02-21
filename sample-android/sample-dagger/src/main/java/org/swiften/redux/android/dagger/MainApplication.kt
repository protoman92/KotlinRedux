/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.android.dagger

import android.app.Application
import com.squareup.leakcanary.LeakCanary
import org.swiften.redux.android.ui.AndroidPropInjector
import org.swiften.redux.android.ui.lifecycle.injectActivitySerializable
import org.swiften.redux.core.FinalStore
import org.swiften.redux.core.applyMiddlewares
import org.swiften.redux.core.createAsyncMiddleware
import org.swiften.redux.core.createRouterMiddleware
import org.swiften.redux.saga.common.createSagaMiddleware

/** Created by haipham on 26/1/19 */
class MainApplication : Application() {
  override fun onCreate() {
    super.onCreate()
    if (LeakCanary.isInAnalyzerProcess(this)) { return }
    LeakCanary.install(this)

    val mainComponent = DaggerMainComponent.builder()
      .dependencyLevel2Module(DependencyLevel2Module())
      .dependencyLevel3Module(DependencyLevel3Module())
      .build()

    val store = applyMiddlewares<Redux.State>(
      createAsyncMiddleware(),
      createRouterMiddleware(Router(this)),
      createSagaMiddleware(Redux.Saga.allSagas())
    )(FinalStore(Redux.State(), Redux.Reducer))

    val injector = AndroidPropInjector(store)
    val injectionHelper = InjectionHelper(mainComponent)
    injector.injectActivitySerializable(this) { injectionHelper.inject(this, it) }
  }
}
