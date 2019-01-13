/*
 * Copyright (c) haipham 2018. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.android.sample

import android.app.Application
import android.content.Intent
import android.net.Uri
import androidx.fragment.app.Fragment
import org.swiften.redux.android.ui.core.AndroidPropInjector
import org.swiften.redux.android.ui.core.endActivityInjection
import org.swiften.redux.android.ui.core.startActivityInjection
import org.swiften.redux.android.ui.router.SingleActivityRouter
import org.swiften.redux.core.AsyncReduxStore
import org.swiften.redux.core.ReduxSubscription
import org.swiften.redux.middleware.applyReduxMiddlewares
import org.swiften.redux.router.createRouterMiddlewareProvider
import org.swiften.redux.saga.ReduxSagaMiddlewareProvider
import org.swiften.redux.ui.injectStaticProps

/** Created by haipham on 2018/12/19 */
class MainApplication : Application() {
  private lateinit var activityCallback: ActivityLifecycleCallbacks
  private lateinit var subscription: ReduxSubscription

  override fun onCreate() {
    super.onCreate()
    val api = MainApi()
    val repository = MainRepository(api, JSONDecoder())

    val store = applyReduxMiddlewares(
      createRouterMiddlewareProvider<State, MainRedux.Screen>(
        SingleActivityRouter(this) { activity, screen ->
          val f: Fragment? = when (screen) {
            is MainRedux.Screen.MusicDetail -> MusicDetailFragment()

            is MainRedux.Screen.WebView -> {
              val browserIntent = Intent (Intent.ACTION_VIEW, Uri.parse(screen.url))
              activity.startActivity(browserIntent)
              null
            }
          }

          f?.also {
            activity.supportFragmentManager
              .beginTransaction()
              .replace(R.id.fragment, it)
              .commit()
          }
        }
      ).middleware,
      ReduxSagaMiddlewareProvider(MainSaga.sagas(repository)).middleware
    )(AsyncReduxStore(State(), MainRedux.Reducer))

    val injector = AndroidPropInjector(store)
    val dependency = MainDependency(injector)

    this.activityCallback = startActivityInjection(this, injector) {
      when (it) {
        is MainActivity -> dependency.injector.injectStaticProps(it)
      }
    }
  }

  override fun onTerminate() {
    super.onTerminate()
    this.subscription.unsubscribe()
    endActivityInjection(this, this.activityCallback)
  }
}
