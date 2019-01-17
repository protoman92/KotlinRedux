/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.android.ui

import android.app.Activity
import android.app.Application
import android.os.Bundle
import org.swiften.redux.core.DefaultReduxAction
import org.swiften.redux.ui.IReduxPropInjector
import java.io.Serializable
import java.util.Date

/** Created by haipham on 2018/12/17 */
/**
 * Listen to [Activity] lifecycle callbacks and perform [inject] when necessary. We can also
 * declare [saveState] and [restoreState] to handle [State] persistence.
 */
fun <State> startActivityInjection(
  application: Application,
  injector: IReduxPropInjector<State>,
  inject: IReduxPropInjector<State>.(Activity) -> Unit,
  saveState: State.(Bundle) -> Unit = {},
  restoreState: (Bundle) -> State? = { null }
): Application.ActivityLifecycleCallbacks {
  val callback = object : Application.ActivityLifecycleCallbacks {
    override fun onActivityPaused(activity: Activity?) {}
    override fun onActivityResumed(activity: Activity?) {}
    override fun onActivityStopped(activity: Activity?) {}
    override fun onActivityDestroyed(activity: Activity?) {}

    override fun onActivitySaveInstanceState(activity: Activity?, outState: Bundle?) {
      outState?.also { saveState(injector.lastState(), it) }
    }

    override fun onActivityCreated(activity: Activity?, savedInstanceState: Bundle?) {
      savedInstanceState
        ?.run { restoreState(this) }
        ?.apply { injector.dispatch(DefaultReduxAction.ReplaceState(this)) }
    }

    override fun onActivityStarted(activity: Activity?) {
      activity?.also { inject(injector, it) }
    }
  }

  application.registerActivityLifecycleCallbacks(callback)
  return callback
}

/**
 * Similar to [startActivityInjection], but provides default persistence for when [State] is
 * [Serializable]
 */
inline fun <reified State> startActivityInjection(
  application: Application,
  injector: IReduxPropInjector<State>,
  noinline inject: IReduxPropInjector<State>.(Activity) -> Unit
): Application.ActivityLifecycleCallbacks where State : Serializable {
  val stateKey = "REDUX_STATE_${Date().time}"

  return startActivityInjection(
    application,
    injector,
    inject,
    { it.putSerializable(stateKey, this) },
    { b -> b.getSerializable(stateKey)?.takeIf { it is State }?.run { this as State } }
  )
}

/** End lifecycle [callback] for [Activity] */
fun endActivityInjection(
  application: Application,
  callback: Application.ActivityLifecycleCallbacks
) {
  application.unregisterActivityLifecycleCallbacks(callback)
}
