/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.android.sample

import android.app.Application
import com.beust.klaxon.Klaxon
import com.squareup.leakcanary.LeakCanary
import org.swiften.redux.android.sample.Redux.State
import org.swiften.redux.android.ui.AndroidPropInjector
import org.swiften.redux.android.ui.lifecycle.injectActivitySerializable
import org.swiften.redux.android.ui.lifecycle.injectLifecycle
import org.swiften.redux.async.createAsyncMiddleware
import org.swiften.redux.core.FinalStore
import org.swiften.redux.core.applyMiddlewares
import org.swiften.redux.saga.common.createSagaMiddleware

/** Created by haipham on 26/1/19 */
class MainApplication : Application() {
  override fun onCreate() {
    super.onCreate()
    if (LeakCanary.isInAnalyzerProcess(this)) { return }
    LeakCanary.install(this)
    val api = API()
    val repository = Repository(Klaxon(), api)

    val store = applyMiddlewares<Redux.State>(
      createSagaMiddleware(Redux.Saga.allSagas(repository)),
      createAsyncMiddleware()
    )(FinalStore(State(), Redux.Reducer))

    val injector = AndroidPropInjector(store)

    injector.injectActivitySerializable(this) {
      when (it) {
        is MainFragment -> this.injectLifecycle(Unit, it, MainFragment)
        is SearchFragment -> this.injectLifecycle(Unit, it, SearchFragment)
        is DetailFragment -> this.injectLifecycle(Unit, it, DetailFragment)
      }
    }
  }
}
