/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.ui

import org.swiften.redux.core.IReduxSubscription

/** Created by haipham on 2019/01/16 */
/**
 * Container for an [IPropContainer] mutable properties.
 * @param State State type.
 * @param Action Action type.
 */
interface IVariableProps<State, Action> {
  val state: State?
  val action: Action?
}

/**
 * Container for an static dependencies.
 * @param GState The global state type.
 * @param GExt The global external argument.
 * @param injector See [IStaticProps.injector].
 * @param subscription See [IStaticProps.subscription].
 */
data class StaticProps<GState, GExt>(
  val injector: IPropInjector<GState, GExt>,
  val subscription: IReduxSubscription
)

/**
 * Container for [StaticProps] and [VariableProps].
 * @param State The state type.
 * @param Action The action type.
 * @param s An [IReduxSubscription] instance.
 * @param state A [State] instance.
 * @param action An [Action] instance.
 */
data class ReduxProps<State, Action>(
  val s: IReduxSubscription,
  override val state: State?,
  override val action: Action?
) : IVariableProps<State, Action>
