/*
 * Copyright (c) haipham 2018. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.android.sample

import android.app.Application
import org.swiften.redux.android.ui.core.endActivityInjection
import org.swiften.redux.android.ui.core.startActivityInjection
import org.swiften.redux.core.SimpleReduxStore
import org.swiften.redux.middleware.applyReduxMiddlewares
import org.swiften.redux.saga.ReduxSagaMiddleware
import org.swiften.redux.ui.ReduxPropInjector
import org.swiften.redux.ui.injectStaticProps

/** Created by haipham on 2018/12/19 */
class MainApplication : Application() {
  private lateinit var activityCallback: ActivityLifecycleCallbacks

  override fun onCreate() {
    super.onCreate()
    val api = MainApi()
    val repository = MainRepository(api, JSONDecoder())

    val store = applyReduxMiddlewares(
      ReduxSagaMiddleware.Provider(MainSaga.sagas(repository)).middleware
    )(SimpleReduxStore(State(), MainRedux.Reducer))

    val injector = ReduxPropInjector(store)
    val dependency = MainDependency(injector)

    this.activityCallback = startActivityInjection(this, injector) {
      when (it) {
        is MainActivity -> dependency.injector.injectStaticProps(it)
      }
    }
  }

  override fun onTerminate() {
    super.onTerminate()
    endActivityInjection(this, this.activityCallback)
  }
}
