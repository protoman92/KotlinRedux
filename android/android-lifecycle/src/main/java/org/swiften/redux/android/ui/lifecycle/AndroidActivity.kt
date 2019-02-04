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
import android.os.Parcelable
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import org.swiften.redux.core.DefaultReduxAction
import org.swiften.redux.ui.IPropInjector
import java.io.Serializable
import java.util.Date

/** Created by haipham on 2018/12/17 */
/** Handle saving/restoring [GState] instances. */
interface IBundleStateSaver<GState> {
  fun saveState(bundle: Bundle, state: GState)
  fun restoreState(bundle: Bundle): GState?
}

/**
 * Listen to [Activity] lifecycle callbacks and perform [inject] when necessary. We can also
 * declare [saveState] and [restoreState] to handle [GState] persistence.
 *
 * When [Application.ActivityLifecycleCallbacks.onActivityCreated] is called, perform [inject]
 * on the [AppCompatActivity] being created, and also call [injectFragment]. This is why
 * [inject] accepts [LifecycleOwner] as its only parameter so that it can handle both
 * [AppCompatActivity] and [Fragment].
 */
fun <GState, GExt> IPropInjector<GState, GExt>.injectActivity(
  application: Application,
  saver: IBundleStateSaver<GState>,
  inject: IPropInjector<GState, GExt>.(LifecycleOwner) -> Unit
): Application.ActivityLifecycleCallbacks where GState : Any, GExt : Any {
  val callback = object : Application.ActivityLifecycleCallbacks {
    override fun onActivityPaused(activity: Activity?) {}
    override fun onActivityResumed(activity: Activity?) {}
    override fun onActivityStopped(activity: Activity?) {}
    override fun onActivityDestroyed(activity: Activity?) {}

    override fun onActivitySaveInstanceState(activity: Activity?, outState: Bundle?) {
      outState?.also { saver.saveState(it, this@injectActivity.lastState()) }
    }

    override fun onActivityCreated(activity: Activity?, savedInstanceState: Bundle?) {
      try {
        savedInstanceState
          ?.run { restoreState(this) }
          ?.apply { this@injectActivity.dispatch(DefaultReduxAction.ReplaceState(this)) }
      } catch (e: Throwable) { }

      activity?.also {
        require(it is AppCompatActivity)
        inject(this@injectActivity, it)
        this@injectActivity.injectFragment(it, inject)
      }
    }

    override fun onActivityStarted(activity: Activity?) {}
  }

  application.registerActivityLifecycleCallbacks(callback)
  return callback
}

/**
 * Similar to [injectActivity], but provides default persistence for when [GState] is
 * [Serializable]
 */
inline fun <reified GState, GExt> IPropInjector<GState, GExt>.injectActivitySerializable(
  application: Application,
  noinline inject: IPropInjector<GState, GExt>.(LifecycleOwner) -> Unit
): Application.ActivityLifecycleCallbacks where GState : Any, GState : Serializable, GExt : Any {
  val key = "REDUX_STATE_${Date().time}"

  return this.injectActivity(application, object : IBundleStateSaver<GState> {
    override fun saveState(bundle: Bundle, state: GState) = bundle.putSerializable(key, state)

    override fun restoreState(bundle: Bundle): GState? {
      return bundle.getSerializable(key)?.takeIf { it is GState }?.run { this as GState }
    }
  }, inject)
}

/**
 * Similar to [injectActivity], but provides default persistence for when [GState] is
 * [Parcelable]
 */
inline fun <reified GState, GExt> IPropInjector<GState, GExt>.injectActivityParcelable(
  application: Application,
  noinline inject: IPropInjector<GState, GExt>.(LifecycleOwner) -> Unit
): Application.ActivityLifecycleCallbacks where GState : Any, GState : Parcelable, GExt : Any {
  val key = "REDUX_STATE_${Date().time}"

  return this.injectActivity(application, object : IBundleStateSaver<GState> {
    override fun saveState(bundle: Bundle, state: GState) = bundle.putParcelable(key, state)
    override fun restoreState(bundle: Bundle) = bundle.getParcelable<GState>(key)
  }, inject)
}
