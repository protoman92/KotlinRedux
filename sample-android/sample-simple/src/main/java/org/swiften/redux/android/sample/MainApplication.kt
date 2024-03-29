/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.android.sample

import androidx.lifecycle.LifecycleOwner
import androidx.multidex.MultiDexApplication
import com.beust.klaxon.Klaxon
import org.swiften.redux.android.lifecycle.ILifecycleInjectionHelper
import org.swiften.redux.android.lifecycle.injectActivitySerializable
import org.swiften.redux.android.lifecycle.injectLifecycle
import org.swiften.redux.android.sample.Redux.State
import org.swiften.redux.android.ui.AndroidPropInjector
import org.swiften.redux.core.FinalStore
import org.swiften.redux.core.NestedRouter
import org.swiften.redux.core.RouterMiddleware
import org.swiften.redux.core.applyMiddlewares
import org.swiften.redux.saga.SagaMiddleware
import org.swiften.redux.ui.IPropInjector

/** Created by haipham on 26/1/19 */
class MainApplication : MultiDexApplication() {
  override fun onCreate() {
    super.onCreate()
    val api = API()
    val repository = Repository(Klaxon(), api)

    val store = applyMiddlewares<Redux.State>(
      SagaMiddleware.create(effects = Redux.Saga.allSagas(repository)),
      RouterMiddleware.create(NestedRouter.create { false })
    )(FinalStore(State(), Redux.Reducer))

    val injector = AndroidPropInjector(store)

    injector.injectActivitySerializable(this, object : ILifecycleInjectionHelper<Redux.State> {
      override fun inject(injector: IPropInjector<State>, owner: LifecycleOwner) {
        when (owner) {
          is MainActivity -> injector.injectLifecycle(Unit, owner, MainActivity)
          is MainFragment -> injector.injectLifecycle(Unit, owner, MainFragment)
          is SearchFragment -> injector.injectLifecycle(Unit, owner, SearchFragment)
          is DetailFragment -> injector.injectLifecycle(Unit, owner, DetailFragment)
        }
      }

      override fun deinitialize(owner: LifecycleOwner) {}
    })
  }
}
