/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.ui

import org.swiften.redux.core.ReduxSubscription

/** Created by haipham on 2019/01/16 */
/** Container for an [IReduxPropContainer] static properties */
data class StaticProps<State>(
  val injector: IReduxPropInjector<State>,
  internal val subscription: ReduxSubscription
)

/** Container for an [IReduxPropContainer] mutable properties */
data class VariableProps<StateProps, ActionProps>(
  val previous: StateProps?,
  val next: StateProps,
  val actions: ActionProps
)

/** Container for [StaticProps] and [VariableProps] */
data class ReduxProps<State, StateProps, ActionProps>(
  val static: StaticProps<State>,
  val variable: VariableProps<StateProps, ActionProps>?
)
