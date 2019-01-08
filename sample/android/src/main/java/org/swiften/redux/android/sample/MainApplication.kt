/*
 * Copyright (c) haipham 2018. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.android.sample

import android.app.Activity
import android.app.Application
import android.os.Bundle
import org.swiften.redux.android.ui.AndroidRedux
import org.swiften.redux.core.SimpleReduxStore
import org.swiften.redux.middleware.ReduxMiddleware.applyMiddlewares
import org.swiften.redux.saga.ReduxSagaMiddleware

/** Created by haipham on 2018/12/19 */
class MainApplication : Application() {
  override fun onCreate() {
    super.onCreate()
    val api = MainApi()
    val repository = MainRepository(api, JSONDecoder())

    val store = applyMiddlewares(
      ReduxSagaMiddleware.Provider(MainSaga.sagas(repository)).middleware
    )(SimpleReduxStore(State(), MainRedux.Reducer))

    val injector = AndroidRedux.PropInjector(store)
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
