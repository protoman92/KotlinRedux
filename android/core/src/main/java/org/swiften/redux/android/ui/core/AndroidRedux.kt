/*
 * Copyright (c) haipham 2018. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.android.ui.core

import android.app.Activity
import android.app.Application
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import org.swiften.redux.core.DefaultReduxAction
import org.swiften.redux.core.IReduxDispatcher
import org.swiften.redux.core.IReduxStore
import org.swiften.redux.core.ReduxSubscription
import org.swiften.redux.ui.IReduxPropContainer
import org.swiften.redux.ui.IReduxPropInjector
import org.swiften.redux.ui.IReduxPropLifecycleOwner
import org.swiften.redux.ui.IReduxPropMapper
import org.swiften.redux.ui.IReduxStatePropMapper
import org.swiften.redux.ui.ReduxPropInjector
import org.swiften.redux.ui.ReduxProps
import org.swiften.redux.ui.StaticProps
import java.io.Serializable
import java.util.Date

/** Created by haipham on 2018/12/17 */
/** Callbacks for lifecycle for use with [LifecycleObserver] */
internal interface LifecycleCallback {
  /** Called on [Lifecycle.Event.ON_START] */
  fun onStart()

  /** Called on [Lifecycle.Event.ON_RESUME] */
  fun onResume()

  /** Called on [Lifecycle.Event.ON_PAUSE] */
  fun onPause()

  /** Called on [Lifecycle.Event.ON_STOP] */
  fun onStop()
}

/** Use this [LifecycleObserver] to unsubscribe from a [ReduxSubscription] */
internal class ReduxLifecycleObserver<LC> constructor(
  private val lifecycleOwner: LC,
  private val callback: LifecycleCallback
) : LifecycleObserver where LC : LifecycleOwner, LC : IReduxPropLifecycleOwner {
  init { lifecycleOwner.lifecycle.addObserver(this) }

  @OnLifecycleEvent(Lifecycle.Event.ON_START)
  fun onStart() { this.callback.onStart() }

  @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
  fun onResume() { this.callback.onResume() }

  @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
  fun onPause() { this.callback.onPause() }

  @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
  fun onStop() {
    this.callback.onStop()
    this.lifecycleOwner.lifecycle.removeObserver(this)
  }
}

internal fun runOnUIThread(runnable: () -> Unit) {
  if (Looper.myLooper() == Looper.getMainLooper()) { runnable() } else {
    Handler(Looper.getMainLooper()).post { runnable() }
  }
}

/**
 * - When [ReduxLifecycleObserver.onStart] is called, create the [ReduxSubscription].
 * - When [ReduxLifecycleObserver.onStop] is called, unsubscribe from [State] updates.
 *
 * This method is applicable to [Activity] and [Fragment].
 */
fun <State, LC, OP, SP, AP> IReduxPropInjector<State>.injectLifecycleProps(
  lifecycleOwner: LC,
  outProps: OP,
  mapper: IReduxPropMapper<State, OP, SP, AP>
): LC where
  LC : LifecycleOwner,
  LC : IReduxPropContainer<State, SP, AP>,
  LC : IReduxPropLifecycleOwner
{
  val view: IReduxPropContainer<State, SP, AP> = lifecycleOwner
  var subscription: ReduxSubscription? = null

  ReduxLifecycleObserver(lifecycleOwner, object : LifecycleCallback {
    override fun onStart() {
      subscription = this@injectLifecycleProps.injectProps(view, outProps, mapper)
    }

    override fun onResume() {}
    override fun onPause() {}
    override fun onStop() { subscription?.unsubscribe() }
  })

  return lifecycleOwner
}

/**
 * Inject props into [lifecycleOwner], which is a view that only has a mutable [SP] but handles
 * no actions.
 */
fun <State, LC, OP, SP> IReduxPropInjector<State>.injectLifecycleProps(
  lifecycleOwner: LC,
  outProps: OP,
  mapper: IReduxStatePropMapper<State, OP, SP>
): LC where
  LC : LifecycleOwner,
  LC : IReduxPropContainer<State, SP, Unit>,
  LC : IReduxPropLifecycleOwner =
  this.injectLifecycleProps<State, LC, OP, SP, Unit>(lifecycleOwner, outProps,
    object : IReduxPropMapper<State, OP, SP, Unit> {
      override fun mapAction(dispatch: IReduxDispatcher, state: State, outProps: OP) = Unit
      override fun mapState(state: State, outProps: OP) = mapper.mapState(state, outProps)
    })

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

/** Listen to [Fragment] lifecycle callbacks and perform [inject] when necessary */
fun <State, Activity> startFragmentInjection(
  activity: Activity,
  inject: StaticProps<State>.(Fragment) -> Unit
): FragmentManager.FragmentLifecycleCallbacks where
  Activity : AppCompatActivity,
  Activity : IReduxPropContainer<State, *, *>
{
  val callback = object : FragmentManager.FragmentLifecycleCallbacks() {
    override fun onFragmentStarted(fm: FragmentManager, f: Fragment) {
      activity.reduxProps.static.also { inject(it, f) }
    }
  }

  activity.supportFragmentManager.registerFragmentLifecycleCallbacks(callback, true)
  return callback
}

/** End lifecycle [callback] for [Fragment] */
fun endFragmentInjection(
  activity: AppCompatActivity,
  callback: FragmentManager.FragmentLifecycleCallbacks
) = activity.supportFragmentManager.unregisterFragmentLifecycleCallbacks(callback)

/**
 * [IReduxPropInjector] specifically for Android that calls [injectProps] on the main thread. We
 * use inheritance here to ensure [StaticProps.injector] is set with this class instance.
 */
class AndroidPropInjector<State>(store: IReduxStore<State>) : ReduxPropInjector<State>(store) {
  override fun <OutProps, StateProps, ActionProps> injectProps(
    view: IReduxPropContainer<State, StateProps, ActionProps>,
    outProps: OutProps,
    mapper: IReduxPropMapper<State, OutProps, StateProps, ActionProps>
  ) = super.injectProps(
    object : IReduxPropContainer<State, StateProps, ActionProps> {
      override var reduxProps: ReduxProps<State, StateProps, ActionProps>
        get() = view.reduxProps
        set(value) { runOnUIThread { view.reduxProps = value } }

      override fun didSetReduxProps(props: ReduxProps<State, StateProps, ActionProps>) {
        runOnUIThread { view.didSetReduxProps(props) }
      }

      override fun beforePropInjectionStarts() = runOnUIThread { view.beforePropInjectionStarts() }
      override fun afterPropInjectionEnds() = runOnUIThread { view.afterPropInjectionEnds() }
    },
    outProps, mapper
  )
}
