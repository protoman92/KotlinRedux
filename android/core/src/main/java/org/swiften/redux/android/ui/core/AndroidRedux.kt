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
import org.swiften.redux.android.ui.core.AndroidRedux.ReduxLifecycleObserver
import org.swiften.redux.core.DefaultReduxAction
import org.swiften.redux.core.IReduxDispatcher
import org.swiften.redux.core.ReduxSubscription
import org.swiften.redux.ui.IReduxLifecycleOwner
import org.swiften.redux.ui.IReduxPropContainer
import org.swiften.redux.ui.IReduxPropInjector
import org.swiften.redux.ui.IReduxPropMapper
import org.swiften.redux.ui.IReduxStatePropMapper
import org.swiften.redux.ui.IStaticReduxPropContainer
import org.swiften.redux.ui.StaticProps
import org.swiften.redux.ui.VariableProps
import java.io.Serializable
import java.util.Date

/** Created by haipham on 2018/12/17 */
/** Top-level namespace for Android Redux UI functionalities */
internal object AndroidRedux {
  /** Use this [LifecycleObserver] to unsubscribe from a [ReduxSubscription] */
  class ReduxLifecycleObserver<LC> constructor(
    private val lifecycleOwner: LC,
    private val subscription: ReduxSubscription
  ) : LifecycleObserver where LC : LifecycleOwner, LC : IReduxLifecycleOwner {
    init { lifecycleOwner.lifecycle.addObserver(this) }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onResume() {
      this.lifecycleOwner.onPropInjectionCompleted()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun onPause() {
      this.lifecycleOwner.onPropInjectionStopped()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onStop() {
      this.subscription.unsubscribe()
      this.lifecycleOwner.lifecycle.removeObserver(this)
    }
  }
}

@Suppress("unused")
internal fun IReduxPropInjector<*>.runOnUIThread(runnable: () -> Unit) {
  if (Looper.myLooper() == Looper.getMainLooper()) { runnable() } else {
    Handler(Looper.getMainLooper()).post { runnable() }
  }
}

/** Call [IReduxPropInjector.injectRecyclerViewProps] on the main thread */
internal fun <State, OP, SP, AP> IReduxPropInjector<State>.injectPropsOnMainThread(
  view: IReduxPropContainer<State, SP, AP>,
  outProps: OP,
  mapper: IReduxPropMapper<State, OP, SP, AP>
) = this.injectRecyclerViewProps(
  object : IReduxPropContainer<State, SP, AP> {
    override var staticProps: StaticProps<State>
      get() = view.staticProps
      set(value) { view.staticProps = value }

    override var variableProps: VariableProps<SP, AP>?
      get() = view.variableProps
      set(value) { this@injectPropsOnMainThread.runOnUIThread { view.variableProps = value } }

    override fun onPropInjectionCompleted() {
      super.onPropInjectionCompleted()
      view.onPropInjectionCompleted()
    }

    override fun onPropInjectionStopped() {
      super.onPropInjectionStopped()
      view.onPropInjectionStopped()
    }
  },
  outProps, mapper
)

/**
 * - When [ReduxLifecycleObserver.onResume] is called, call
 * [IReduxLifecycleOwner.onPropInjectionCompleted].
 * - When [ReduxLifecycleObserver.onStop] is called, unsubscribe from [State] updates.
 * - When [ReduxLifecycleObserver.onPause] is called, call
 * [IReduxLifecycleOwner.onPropInjectionStopped]
 */
fun <State, LC, OP, SP, AP> IReduxPropInjector<State>.injectLifecycleProps(
  lifecycleOwner: LC,
  outProps: OP,
  mapper: IReduxPropMapper<State, OP, SP, AP>
) where
  LC : LifecycleOwner,
  LC : IReduxPropContainer<State, SP, AP>
{
  val view: IReduxPropContainer<State, SP, AP> = lifecycleOwner
  val subscription = this.injectPropsOnMainThread(view, outProps, mapper)
  ReduxLifecycleObserver(lifecycleOwner, subscription)
}

/**
 * Inject props into [lifecycleOwner], which is a view that only has a mutable [SP] but handles
 * no actions.
 */
fun <State, LC, OP, SP> IReduxPropInjector<State>.injectLifecycleProps(
  lifecycleOwner: LC,
  outProps: OP,
  mapper: IReduxStatePropMapper<State, OP, SP>
) where
  LC : LifecycleOwner,
  LC : IReduxPropContainer<State, SP, Unit> =
  this.injectPropsOnMainThread(lifecycleOwner, outProps,
    object : IReduxPropMapper<State, OP, SP, Unit> {
      override fun mapAction(dispatch: IReduxDispatcher, state: State, outProps: OP) = Unit
      override fun mapState(state: State, outProps: OP) = mapper.mapState(state, outProps)
    })

/**
 * Listen to [Activity] lifecycle callbacks and perform [inject] when necessary. We can also
 * declare [saveState] and [restoreState] to handle [State] persistence.
 */
@Suppress("unused")
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
      if (outState != null) saveState(injector.stateGetter(), outState)
    }

    override fun onActivityCreated(activity: Activity?, savedInstanceState: Bundle?) {
      savedInstanceState
        ?.run { restoreState(this) }
        ?.apply { injector.dispatch(DefaultReduxAction.ReplaceState(this)) }
    }

    override fun onActivityStarted(activity: Activity?) {
      if (activity != null) inject(injector, activity)
    }
  }

  application.registerActivityLifecycleCallbacks(callback)
  return callback
}

/**
 * Similar to [startActivityInjection], but provides default persistence for when [State] is
 * [Serializable]
 */
@Suppress("unused")
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
    { it.getSerializable(stateKey)?.takeIf { it is State }?.run { this as State } }
  )
}

/** End lifecycle [callback] for [Activity] */
@Suppress("unused")
fun endActivityInjection(
  application: Application,
  callback: Application.ActivityLifecycleCallbacks
) {
  application.unregisterActivityLifecycleCallbacks(callback)
}

/** Listen to [Fragment] lifecycle callbacks and perform [inject] when necessary */
@Suppress("unused")
fun <State, Activity> startFragmentInjection(
  activity: Activity,
  inject: StaticProps<State>.(Fragment) -> Unit
): FragmentManager.FragmentLifecycleCallbacks where
  Activity : AppCompatActivity,
  Activity : IStaticReduxPropContainer<State>
{
  val callback = object : FragmentManager.FragmentLifecycleCallbacks() {
    override fun onFragmentStarted(fm: FragmentManager, f: Fragment) {
      inject(activity.staticProps, f)
    }
  }

  activity.supportFragmentManager.registerFragmentLifecycleCallbacks(callback, true)
  return callback
}

/** End lifecycle [callback] for [Fragment] */
@Suppress("unused")
fun endFragmentInjection(
  activity: AppCompatActivity,
  callback: FragmentManager.FragmentLifecycleCallbacks
) {
  activity.supportFragmentManager.unregisterFragmentLifecycleCallbacks(callback)
}
