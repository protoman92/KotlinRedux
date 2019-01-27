/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.ui

import org.swiften.redux.core.IReduxSubscription

/** Created by haipham on 2019/01/16 */
/** Container for an [IPropContainer] static properties */
interface IStaticProps<GlobalState> {
  val injector: IPropInjector<GlobalState>
  val subscription: IReduxSubscription
}

/** Container for an [IPropContainer] mutable properties */
interface IVariableProps<State, Action> {
  val state: State
  val action: Action
}

/** [IStaticProps] implementation */
data class StaticProps<GlobalState>(
  override val injector: IPropInjector<GlobalState>,
  override val subscription: IReduxSubscription
) : IStaticProps<GlobalState>

/** [IVariableProps] implementation */
data class VariableProps<State, Action>(override val state: State, override val action: Action) :
  IVariableProps<State, Action>

/** Container for [StaticProps] and [VariableProps] */
data class ReduxProps<GlobalState, State, Action>(
  val static: IStaticProps<GlobalState>,
  val variable: IVariableProps<State, Action>?
)
