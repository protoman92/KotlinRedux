/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.android.lifecycle

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
import org.swiften.redux.ui.IFullPropInjector
import java.io.Serializable
import java.util.Date

/** Created by haipham on 2018/12/17 */
/**
 * Handle saving/restoring [GState] instances.
 * @param GState The global state type.
 */
interface IBundleStateSaver<GState> {
  /**
   * Save [state] to [bundle].
   * @param bundle A [Bundle] instance.
   * @param state A [GState] instance.
   */
  fun saveState(bundle: Bundle, state: GState)

  /**
   * Restore a [GState] from [bundle].
   * @param bundle A [Bundle] instance.
   * @return A [GState] instance.
   */
  fun restoreState(bundle: Bundle): GState?
}

/**
 * Listen to [Activity] lifecycle callbacks and perform [injectionHelper] when necessary. We can also declare
 * [saveState] and [restoreState] to handle [GState] persistence.
 *
 * When [Application.ActivityLifecycleCallbacks.onActivityCreated] is called, perform
 * [injectionHelper] on the [AppCompatActivity] being created, and also call [injectFragment]. This
 * is why [injectionHelper] accepts [LifecycleOwner] as its only parameter so that it can handle
 * both [AppCompatActivity] and [Fragment].
 * @receiver An [IFullPropInjector] instance.
 * @param GState The global state type.
 * @param application An [Application] instance.
 * @param saver An [IBundleStateSaver] instance.
 * @param injectionHelper An [ILifecycleInjectionHelper] instance.
 * @return An [Application.ActivityLifecycleCallbacks] instance.
 */
fun <GState> IFullPropInjector<GState>.injectActivity(
  application: Application,
  saver: IBundleStateSaver<GState>,
  injectionHelper: ILifecycleInjectionHelper<GState>
): Application.ActivityLifecycleCallbacks where GState : Any {
  val callback = object : Application.ActivityLifecycleCallbacks {
    override fun onActivityPaused(activity: Activity) {}
    override fun onActivityResumed(activity: Activity) {}

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
      outState.also { saver.saveState(it, this@injectActivity.lastState()) }
    }

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
      try {
        savedInstanceState
          ?.run { saver.restoreState(this) }
          ?.apply { this@injectActivity.dispatch(DefaultReduxAction.ReplaceState(this)) }
      } catch (e: Throwable) { }

      activity.also {
        require(it is AppCompatActivity)
        this@injectActivity.injectFragment(it, injectionHelper)
      }
    }

    override fun onActivityStarted(activity: Activity) {
      activity.also {
        require(it is AppCompatActivity)
        injectionHelper.inject(this@injectActivity, it)
      }
    }

    override fun onActivityStopped(activity: Activity) {
      activity.also {
        require(it is AppCompatActivity)
        injectionHelper.deinitialize(it)
      }
    }

    override fun onActivityDestroyed(activity: Activity) {}
  }

  application.registerActivityLifecycleCallbacks(callback)
  return callback
}

/**
 * Similar to [injectActivity], but provides default persistence for when [GState] is
 * [Serializable].
 * @receiver An [IFullPropInjector] instance.
 * @param GState The global state type.
 * @param application An [Application] instance.
 * @param injectionHelper An [ILifecycleInjectionHelper] instance.
 * @return An [Application.ActivityLifecycleCallbacks] instance.
 */
inline fun <reified GState> IFullPropInjector<GState>.injectActivitySerializable(
  application: Application,
  injectionHelper: ILifecycleInjectionHelper<GState>
): Application.ActivityLifecycleCallbacks where GState : Any, GState : Serializable {
  val key = "REDUX_STATE_${Date().time}"

  return this.injectActivity(application, object : IBundleStateSaver<GState> {
    override fun saveState(bundle: Bundle, state: GState) = bundle.putSerializable(key, state)

    override fun restoreState(bundle: Bundle): GState? {
      return bundle.getSerializable(key)?.takeIf { it is GState }?.run { this as GState }
    }
  }, injectionHelper)
}

/**
 * Similar to [injectActivity], but provides default persistence for when [GState] is
 * [Parcelable].
 * @receiver An [IFullPropInjector] instance.
 * @param GState The global state type.
 * @param application An [Application] instance.
 * @param injectionHelper An [ILifecycleInjectionHelper] instance.
 * @return An [Application.ActivityLifecycleCallbacks] instance.
 */
inline fun <reified GState> IFullPropInjector<GState>.injectActivityParcelable(
  application: Application,
  injectionHelper: ILifecycleInjectionHelper<GState>
): Application.ActivityLifecycleCallbacks where GState : Any, GState : Parcelable {
  val key = "REDUX_STATE_${Date().time}"

  return this.injectActivity(application, object : IBundleStateSaver<GState> {
    override fun saveState(bundle: Bundle, state: GState) = bundle.putParcelable(key, state)
    override fun restoreState(bundle: Bundle) = bundle.getParcelable<GState>(key)
  }, injectionHelper)
}
