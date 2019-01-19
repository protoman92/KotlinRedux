/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.android.ui.lifecycle

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import org.swiften.redux.core.IReduxDispatcher
import org.swiften.redux.core.ReduxSubscription
import org.swiften.redux.ui.IReduxPropContainer
import org.swiften.redux.ui.IReduxPropInjector
import org.swiften.redux.ui.IReduxPropLifecycleOwner
import org.swiften.redux.ui.IReduxPropMapper
import org.swiften.redux.ui.IReduxStatePropMapper

/** Created by haipham on 2018/12/17 */
/** Callbacks for lifecycle for use with [LifecycleObserver] */
interface LifecycleCallback {
  /** Called on [Lifecycle.Event.ON_CREATE] */
  fun onCreate()

  /** Called on [Lifecycle.Event.ON_START] */
  fun onStart()

  /** Called on [Lifecycle.Event.ON_RESUME] */
  fun onResume()

  /** Called on [Lifecycle.Event.ON_PAUSE] */
  fun onPause()

  /** Called on [Lifecycle.Event.ON_STOP] */
  fun onStop()

  /** Called on [Lifecycle.Event.ON_DESTROY] */
  fun onDestroy()
}

/** Use this [LifecycleObserver] to unsubscribe from a [ReduxSubscription] */
internal class ReduxLifecycleObserver(
  private val lifecycleOwner: LifecycleOwner,
  private val callback: LifecycleCallback
) : LifecycleObserver, LifecycleCallback by callback {
  init { lifecycleOwner.lifecycle.addObserver(this) }

  @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
  override fun onCreate() { this.callback.onCreate() }

  @OnLifecycleEvent(Lifecycle.Event.ON_START)
  override fun onStart() { this.callback.onStart() }

  @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
  override fun onResume() { this.callback.onResume() }

  @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
  override fun onPause() { this.callback.onPause() }

  @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
  override fun onStop() { this.callback.onStop() }

  @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
  override fun onDestroy() {
    this.callback.onDestroy()
    this.lifecycleOwner.lifecycle.removeObserver(this)
  }
}

/** Call [IReduxPropInjector.injectProps] for [lifecycleOwner] */
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
    override fun onCreate() {
      subscription = this@injectLifecycleProps.injectProps(view, outProps, mapper)
    }

    override fun onDestroy() {
      subscription?.unsubscribe()
    }

    override fun onStart() {}
    override fun onStop() {}
    override fun onResume() {}
    override fun onPause() {}
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
