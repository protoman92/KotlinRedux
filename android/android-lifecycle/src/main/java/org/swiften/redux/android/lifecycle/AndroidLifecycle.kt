/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.android.lifecycle

import androidx.lifecycle.LifecycleOwner
import org.swiften.redux.core.IReduxSubscription
import org.swiften.redux.core.IUniqueIDProvider
import org.swiften.redux.ui.IFullPropInjector
import org.swiften.redux.ui.IPropContainer
import org.swiften.redux.ui.IPropInjector
import org.swiften.redux.ui.IPropLifecycleOwner
import org.swiften.redux.ui.IPropMapper
import org.swiften.redux.ui.ReduxProp

/** Created by haipham on 2018/12/17 */
/**
 * Call [IFullPropInjector.inject] for [lifecycleOwner].
 * @receiver An [IPropInjector] instance.
 * @param GState The global state type.
 * @param LState The local state type that [GState] must extend from.
 * @param Owner [LifecycleOwner] type that also implements [IPropContainer].
 * @param OutProp Property as defined by [lifecycleOwner]'s parent.
 * @param State See [ReduxProp.state].
 * @param Action See [ReduxProp.action].
 * @param outProp An [OutProp] instance.
 * @param lifecycleOwner A [Owner] instance.
 * @param mapper An [IPropMapper] instance.
 * @return The injected [Owner] instance.
 */
fun <GState, LState, Owner, OutProp, State, Action> IPropInjector<GState>.injectLifecycle(
  outProp: OutProp,
  lifecycleOwner: Owner,
  mapper: IPropMapper<LState, OutProp, State, Action>
): Owner where
  GState : LState,
  Owner : LifecycleOwner,
  Owner : IUniqueIDProvider,
  Owner : IPropContainer<State, Action>,
  Owner : IPropLifecycleOwner<LState, OutProp>
{
  var subscription: IReduxSubscription? = null

  ReduxLifecycleObserver(lifecycleOwner, object : ILifecycleCallback {
    override fun onSafeForStartingLifecycleAwareTasks() {
      subscription = this@injectLifecycle.inject(outProp, lifecycleOwner, mapper)
    }

    override fun onSafeForEndingLifecycleAwareTasks() {
      subscription?.also { this@injectLifecycle.unsubscribe(it.uniqueID) }
    }
  })

  return lifecycleOwner
}
