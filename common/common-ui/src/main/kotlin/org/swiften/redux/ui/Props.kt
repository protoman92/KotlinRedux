/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.ui

/** Created by haipham on 2019/01/16 */
/**
 * Container for an [IPropContainer] mutable properties.
 * @param State State type that contains view information.
 * @param Action Action type that handles view interactions.
 */
interface IVariableProp<out State, out Action> {
  val firstTime: Boolean
  val state: State
  val action: Action
}

/**
 * Container for static dependencies.
 * @param LState The local state type that the global state must extend from.
 * @param injector An [IPropInjector] instance.
 */
data class StaticProp<LState, OutProp>(
  val injector: IPropInjector<LState>,
  val outProp: OutProp
)

/**
 * Container for [state] and [action].
 * @param State See [IVariableProp.state].
 * @param Action See [IVariableProp.action].
 * @param firstTime Whether this [ReduxProp] is the first one to be injected.
 * @param state A [State] instance.
 * @param action An [Action] instance.
 */
data class ReduxProp<State, Action>(
  override val firstTime: Boolean,
  override val state: State,
  override val action: Action
) : IVariableProp<State, Action>
