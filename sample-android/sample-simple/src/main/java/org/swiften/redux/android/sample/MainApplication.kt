/*
 * Copyright (c) haipham 2018. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.android.sample

import android.app.Application
import android.content.Intent
import android.net.Uri
import androidx.fragment.app.Fragment
import com.squareup.leakcanary.LeakCanary
import org.swiften.redux.android.router.createSingleActivityRouter
import org.swiften.redux.android.ui.AndroidPropInjector
import org.swiften.redux.android.ui.lifecycle.injectActivitySerializable
import org.swiften.redux.android.ui.lifecycle.injectLifecycle
import org.swiften.redux.async.createAsyncMiddleware
import org.swiften.redux.core.FinalStore
import org.swiften.redux.core.applyMiddlewares
import org.swiften.redux.core.createRouterMiddleware
import org.swiften.redux.saga.common.createSagaMiddleware

/** Created by haipham on 2018/12/19 */
class MainApplication : Application() {
  override fun onCreate() {
    super.onCreate()
    if (LeakCanary.isInAnalyzerProcess(this)) { return }
    LeakCanary.install(this)
    val api = MainApi()
    val repository = MainRepository(api, JSONDecoder())

    val store = applyMiddlewares<MainRedux.State>(
      createRouterMiddleware(MainRouter(this)),
      createSagaMiddleware(MainSaga.sagas(repository)),
      createAsyncMiddleware()
    )(FinalStore(MainRedux.State(), MainRedux.Reducer))

    val injector = AndroidPropInjector(store, Unit)

    injector.injectActivitySerializable(this) {
      when (it) {
        is SearchFragment -> this.injectLifecycle(it, Unit, SearchFragment)
        is MusicDetailFragment -> this.injectLifecycle(it, Unit, MusicDetailFragment)
      }
    }
  }
}
