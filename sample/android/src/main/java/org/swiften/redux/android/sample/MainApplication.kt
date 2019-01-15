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
import org.swiften.redux.android.ui.core.AndroidPropInjector
import org.swiften.redux.android.ui.core.endActivityInjection
import org.swiften.redux.android.ui.core.startActivityInjection
import org.swiften.redux.android.ui.router.SingleActivityRouter
import org.swiften.redux.core.ReduxSubscription
import org.swiften.redux.middleware.applyReduxMiddlewares
import org.swiften.redux.router.createRouterMiddlewareProvider
import org.swiften.redux.saga.createSagaMiddlewareProvider
import org.swiften.redux.store.FinalReduxStore
import org.swiften.redux.ui.injectStaticProps

/** Created by haipham on 2018/12/19 */
class MainApplication : Application() {
  private lateinit var activityCallback: ActivityLifecycleCallbacks
  private lateinit var subscription: ReduxSubscription

  override fun onCreate() {
    super.onCreate()
    if (LeakCanary.isInAnalyzerProcess(this)) { return }
    LeakCanary.install(this)
    val api = MainApi()
    val repository = MainRepository(api, JSONDecoder())

    val store = applyReduxMiddlewares(
      createRouterMiddlewareProvider<State, MainRedux.Screen>(
        SingleActivityRouter(this) { activity, screen ->
          val f: Fragment? = when (screen) {
            is MainRedux.Screen.MainScreen -> MainFragment()
            is MainRedux.Screen.MusicDetail -> MusicDetailFragment()

            is MainRedux.Screen.WebView -> {
              val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(screen.url))
              activity.startActivity(browserIntent)
              null
            }
          }

          f?.also {
            activity.supportFragmentManager
              .beginTransaction()
              .add(R.id.fragment, it, it.javaClass.canonicalName)
              .addToBackStack(null)
              .commitAllowingStateLoss()
          }
        }
      ).middleware,
      createSagaMiddlewareProvider(MainSaga.sagas(repository)).middleware
    )(FinalReduxStore(State(), MainRedux.Reducer))

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
