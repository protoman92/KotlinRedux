/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.ui

import org.swiften.redux.core.IReduxSubscription

/** Created by haipham on 2019/01/16 */
/**
 * Container for an [IPropContainer] static properties.
 * @param GlobalState The global state type.
 * @param GlobalExt The global external argument.
 */
interface IStaticProps<GlobalState, GlobalExt> {
  val injector: IPropInjector<GlobalState, GlobalExt>
  val subscription: IReduxSubscription
}

/**
 * Container for an [IPropContainer] mutable properties.
 * @param State State type.
 * @param Action Action type.
 */
interface IVariableProps<State, Action> {
  val state: State
  val action: Action
}

/**
 * [IStaticProps] implementation.
 * @param GlobalState The global state type.
 * @param GlobalExt The global external argument.
 * @param injector See [IStaticProps.injector].
 * @param subscription See [IStaticProps.subscription].
 */
data class StaticProps<GlobalState, GlobalExt>(
  override val injector: IPropInjector<GlobalState, GlobalExt>,
  override val subscription: IReduxSubscription
) : IStaticProps<GlobalState, GlobalExt>

/**
 * [IVariableProps] implementation.
 * @param State The state type.
 * @param Action The action type.
 * @param state See [IVariableProps.state].
 * @param action See [IVariableProps.action].
 */
data class VariableProps<State, Action>(
  override val state: State,
  override val action: Action
) : IVariableProps<State, Action>

/**
 * Container for [StaticProps] and [VariableProps].
 * @param GlobalState The global state type.
 * @param GlobalExt The global external argument.
 * @param State The state type.
 * @param Action The action type.
 * @param s An [IStaticProps] instance.
 * @param v An [IVariableProps] instance.
 */
data class ReduxProps<GlobalState, GlobalExt, State, Action>(
  val s: IStaticProps<GlobalState, GlobalExt>,
  val v: IVariableProps<State, Action>?
)
