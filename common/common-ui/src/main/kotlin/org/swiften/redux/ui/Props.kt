/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.ui

import org.swiften.redux.core.IReduxSubscription

/** Created by haipham on 2019/01/16 */
/**
 * Container for an [IPropContainer] mutable properties.
 * @param State State type that contains view information.
 * @param Action Action type that handles view interactions.
 */
interface IVariableProp<out State, out Action> where State : Any, Action : Any {
  val state: State?
  val action: Action?
}

/**
 * Container for static dependencies.
 * @param LState The local state type that the global state must extend from.
 * @param injector An [IPropInjector] instance.
 */
data class StaticProp<LState, OutProp>(
  val injector: IPropInjector<LState>,
  val outProp: OutProp
) where LState : Any

/**
 * Container for [subscription], [state] and [action].
 * @param State See [IVariableProp.state].
 * @param Action See [IVariableProp.action].
 * @param subscription An [IReduxSubscription] instance.
 * @param state A [State] instance.
 * @param action An [Action] instance.
 */
data class ReduxProp<State, Action>(
  val subscription: IReduxSubscription,
  override val state: State,
  override val action: Action
) : IVariableProp<State, Action> where State : Any, Action : Any
