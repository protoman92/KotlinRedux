/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.android.ui

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
  fun onCreate()

  /** Called on [Lifecycle.Event.ON_START] */
  fun onStart()

  /** Called on [Lifecycle.Event.ON_RESUME] */
  fun onResume()

  /** Called on [Lifecycle.Event.ON_PAUSE] */
  fun onPause()

  /** Called on [Lifecycle.Event.ON_STOP] */
  fun onStop()

  fun onDestroy()
}

/** Use this [LifecycleObserver] to unsubscribe from a [ReduxSubscription] */
internal class ReduxLifecycleObserver(
  private val lifecycleOwner: LifecycleOwner,
  private val callback: LifecycleCallback
) : LifecycleObserver, LifecycleCallback by callback {
  init { lifecycleOwner.lifecycle.addObserver(this) }

  @OnLifecycleEvent(Lifecycle.Event.ON_START)
  override fun onStart() { this.callback.onStart() }

  @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
  override fun onStop() {
    this.callback.onStop()
    this.lifecycleOwner.lifecycle.removeObserver(this)
  }
}

/**
 * - When [ReduxLifecycleObserver.onStart] is called, create the [ReduxSubscription].
 * - When [ReduxLifecycleObserver.onStop] is called, unsubscribe from [State] updates.
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
    override fun onCreate() {}
    override fun onDestroy() {}

    override fun onStart() {
      subscription = this@injectLifecycleProps.injectProps(view, outProps, mapper)
    }

    override fun onStop() {
      subscription?.unsubscribe()
    }

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
