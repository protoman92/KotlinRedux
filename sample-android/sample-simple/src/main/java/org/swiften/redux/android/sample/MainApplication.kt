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
import org.swiften.redux.android.ui.lifecycle.injectLifecycleOwner
import org.swiften.redux.android.ui.lifecycle.injectApplicationSerializable
import org.swiften.redux.middleware.applyMiddlewares
import org.swiften.redux.router.createRouterMiddleware
import org.swiften.redux.saga.common.createSagaMiddleware
import org.swiften.redux.store.FinalStore

/** Created by haipham on 2018/12/19 */
class MainApplication : Application() {
  override fun onCreate() {
    super.onCreate()
    if (LeakCanary.isInAnalyzerProcess(this)) { return }
    LeakCanary.install(this)
    val api = MainApi()
    val repository = MainRepository(api, JSONDecoder())

    val store = applyMiddlewares(
      createRouterMiddleware(
        createSingleActivityRouter<MainActivity, MainRedux.Screen>(this) { activity, screen ->
          val f: Fragment? = when (screen) {
            is MainRedux.Screen.MusicDetail -> MusicDetailFragment()

            is MainRedux.Screen.WebView -> {
              val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(screen.url))
              activity.startActivity(browserIntent)
              null
            }

            else -> null
          }

          f?.also {
            activity.supportFragmentManager
              .beginTransaction()
              .add(R.id.fragment, it, it.javaClass.canonicalName)
              .addToBackStack(null)
              .commitAllowingStateLoss()
          }
        }
      ),
      createSagaMiddleware<State>(MainSaga.sagas(repository))
    )(FinalStore(State(), MainRedux.Reducer))

    val injector = AndroidPropInjector(store)

    injector.injectApplicationSerializable(this) {
      when (it) {
        is SearchFragment -> this.injectLifecycleOwner(it, Unit, it)
        is MusicDetailFragment -> this.injectLifecycleOwner(it, Unit, it)
      }
    }
  }
}