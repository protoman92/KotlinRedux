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
interface IVariableProps<out State, out Action> where State : Any, Action : Any {
  val state: State?
  val action: Action?
}

/**
 * Container for an static dependencies.
 * @param LState The local state type that the global state must extend from.
 * @param OutProps See [IPropInjector.external].
 * @param injector An [IPropInjector] instance.
 */
data class StaticProps<LState, OutProps>(
  val injector: IPropInjector<LState>,
  val outProps: OutProps
) where LState : Any

/**
 * Container for [s], [state] and [action].
 * @param State See [IVariableProps.state].
 * @param Action See [IVariableProps.action].
 * @param s An [IReduxSubscription] instance.
 * @param state A [State] instance.
 * @param action An [Action] instance.
 */
data class ReduxProps<State, Action>(
  val s: IReduxSubscription,
  override val state: State?,
  override val action: Action?
) : IVariableProps<State, Action> where State : Any, Action : Any
