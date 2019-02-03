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
/** Handle saving/restoring [GlobalState] instances. */
interface IBundleStateSaver<GlobalState> {
  fun saveState(bundle: Bundle, state: GlobalState)
  fun restoreState(bundle: Bundle): GlobalState?
}

/**
 * Listen to [Activity] lifecycle callbacks and perform [inject] when necessary. We can also
 * declare [saveState] and [restoreState] to handle [GlobalState] persistence.
 *
 * When [Application.ActivityLifecycleCallbacks.onActivityCreated] is called, perform [inject]
 * on the [AppCompatActivity] being created, and also call [injectFragment]. This is why
 * [inject] accepts [LifecycleOwner] as its only parameter so that it can handle both
 * [AppCompatActivity] and [Fragment].
 */
fun <GlobalState, GlobalExt> IPropInjector<GlobalState, GlobalExt>.injectActivity(
  application: Application,
  saver: IBundleStateSaver<GlobalState>,
  inject: IPropInjector<GlobalState, GlobalExt>.(LifecycleOwner) -> Unit
): Application.ActivityLifecycleCallbacks {
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
 * Similar to [injectActivity], but provides default persistence for when [GlobalState] is
 * [Serializable]
 */
inline fun <reified GlobalState, GlobalExt> IPropInjector<GlobalState, GlobalExt>.injectActivitySerializable(
  application: Application,
  noinline inject: IPropInjector<GlobalState, GlobalExt>.(LifecycleOwner) -> Unit
): Application.ActivityLifecycleCallbacks where GlobalState : Serializable {
  val key = "REDUX_STATE_${Date().time}"

  return this.injectActivity(application, object : IBundleStateSaver<GlobalState> {
    override fun saveState(bundle: Bundle, state: GlobalState) = bundle.putSerializable(key, state)

    override fun restoreState(bundle: Bundle): GlobalState? {
      return bundle.getSerializable(key)?.takeIf { it is GlobalState }?.run { this as GlobalState }
    }
  }, inject)
}

/**
 * Similar to [injectActivity], but provides default persistence for when [GlobalState] is
 * [Parcelable]
 */
inline fun <reified GlobalState, GlobalExt> IPropInjector<GlobalState, GlobalExt>.injectActivityParcelable(
  application: Application,
  noinline inject: IPropInjector<GlobalState, GlobalExt>.(LifecycleOwner) -> Unit
): Application.ActivityLifecycleCallbacks where GlobalState : Parcelable {
  val key = "REDUX_STATE_${Date().time}"

  return this.injectActivity(application, object : IBundleStateSaver<GlobalState> {
    override fun saveState(bundle: Bundle, state: GlobalState) = bundle.putParcelable(key, state)
    override fun restoreState(bundle: Bundle) = bundle.getParcelable<GlobalState>(key)
  }, inject)
}
