/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.android.ui.lifecycle

import android.app.Activity
import android.app.Application
import android.net.http.SslCertificate.restoreState
import android.net.http.SslCertificate.saveState
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import org.swiften.redux.core.DefaultReduxAction
import org.swiften.redux.ui.IReduxPropInjector
import java.io.Serializable
import java.util.*

/** Created by haipham on 2018/12/17 */
/** Handle saving/restoring [State] instances. */
interface IReduxInstanceStateSaver<State> {
  fun saveState(bundle: Bundle, state: State)
  fun restoreState(bundle: Bundle): State?
}

/**
 * Listen to [Activity] lifecycle callbacks and perform [inject] when necessary. We can also
 * declare [saveState] and [restoreState] to handle [State] persistence.
 *
 * When [Application.ActivityLifecycleCallbacks.onActivityCreated] is called, perform [inject]
 * on the [AppCompatActivity] being created, and also call [startFragmentInjection]. This is why
 * [inject] accepts [LifecycleOwner] as its only parameter so that it can handle both
 * [AppCompatActivity] and [Fragment].
 */
fun <State> IReduxPropInjector<State>.startLifecycleInjections(
  application: Application,
  inject: IReduxPropInjector<State>.(LifecycleOwner) -> Unit,
  saver: IReduxInstanceStateSaver<State>
): Application.ActivityLifecycleCallbacks {
  val callback = object : Application.ActivityLifecycleCallbacks {
    override fun onActivityPaused(activity: Activity?) {}
    override fun onActivityResumed(activity: Activity?) {}
    override fun onActivityStopped(activity: Activity?) {}
    override fun onActivityDestroyed(activity: Activity?) {}

    override fun onActivitySaveInstanceState(activity: Activity?, outState: Bundle?) {
      outState?.also { saver.saveState(it, this@startLifecycleInjections.lastState()) }
    }

    override fun onActivityCreated(activity: Activity?, savedInstanceState: Bundle?) {
      savedInstanceState
        ?.run { restoreState(this) }
        ?.apply { this@startLifecycleInjections.dispatch(DefaultReduxAction.ReplaceState(this)) }

      activity?.also {
        require(it is AppCompatActivity)
        inject(this@startLifecycleInjections, it)
        this@startLifecycleInjections.startFragmentInjection(it, inject)
      }
    }

    override fun onActivityStarted(activity: Activity?) {}
  }

  application.registerActivityLifecycleCallbacks(callback)
  return callback
}

/**
 * Similar to [startLifecycleInjections], but provides default persistence for when [State] is
 * [Serializable]
 */
inline fun <reified State> IReduxPropInjector<State>.startLifecycleInjections(
  application: Application,
  noinline inject: IReduxPropInjector<State>.(LifecycleOwner) -> Unit
): Application.ActivityLifecycleCallbacks where State : Serializable {
  val key = "REDUX_STATE_${Date().time}"

  return this.startLifecycleInjections(application, inject,
    object : IReduxInstanceStateSaver<State> {
      override fun saveState(bundle: Bundle, state: State) = bundle.putSerializable(key, state)

      override fun restoreState(bundle: Bundle) =
        bundle.getSerializable(key)?.takeIf { it is State }?.run { this as State }
    }
  )
}
