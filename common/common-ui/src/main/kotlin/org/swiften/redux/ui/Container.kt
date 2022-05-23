/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.ui

import org.swiften.redux.core.IReduxSubscription

/** Created by viethai.pham on 2019/02/21 */
/**
 * Handle lifecycles for a target of [IFullPropInjector].
 * @param LState The local state type that the global state must extend from.
 * @param OutProp Property as defined by a view's parent.
 */
interface IPropLifecycleOwner<LState, OutProp> {
  /**
   * This is called before [IFullPropInjector.inject] is called.
   * @param sp A [StaticProp] instance.
   */
  fun beforePropInjectionStarts(sp: StaticProp<LState, OutProp>)

  /**
   * This is called after [IReduxSubscription.unsubscribe] is called.
   * @param sp A [StaticProp] instance.
   */
  fun afterPropInjectionEnds(sp: StaticProp<LState, OutProp>)
}

/**
 * Use this class as a delegate for [IPropLifecycleOwner] if the target does not want to implement
 * lifecycles.
 * @param LState The local state type that the global state must extend from.
 * @param OutProp Property as defined by a view's parent.
 */
class NoopPropLifecycleOwner<LState, OutProp> : IPropLifecycleOwner<LState, OutProp> {
  override fun beforePropInjectionStarts(sp: StaticProp<LState, OutProp>) {}
  override fun afterPropInjectionEnds(sp: StaticProp<LState, OutProp>) {}
}

/**
 * Represents a container for [ReduxProp].
 * @param State See [ReduxProp.state].
 * @param Action See [ReduxProp.action].
 */
interface IPropContainer<State, Action> {
  var reduxProp: ReduxProp<State, Action>
}
