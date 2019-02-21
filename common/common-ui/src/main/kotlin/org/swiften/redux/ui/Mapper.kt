/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.ui

import org.swiften.redux.core.IActionDispatcher

/** Created by viethai.pham on 2019/02/21 */
/**
 * Maps [LState] to [State] for a [IPropContainer].
 * @param LState The local state type that the global state must extend from.
 * @param OutProp Property as defined by a view's parent.
 * @param State The container state.
 */
interface IStateMapper<in LState, in OutProp, out State> where LState : Any, State : Any {
  /**
   * Map [LState] to [State] using [OutProp]
   * @param state The latest [LState] instance.
   * @param outProp The [OutProp] instance.
   * @return A [State] instance.
   */
  fun mapState(state: LState, outProp: OutProp): State
}

/**
 * Maps [IActionDispatcher] to [Action] for a [IPropContainer]. Note that [Action] can include
 * external, non-Redux related dependencies provided by [OutProp].
 *
 * For example, if the app wants to load an image into a view, it's probably not a good idea to
 * download that image and store in the global state to be mapped into [ReduxProp.state]. It is
 * better to inject an image downloader in [Action] using [OutProp].
 * @param OutProp Property as defined by a view's parent.
 * @param Action See [ReduxProp.action].
 */
interface IActionMapper<in OutProp, out Action> where Action : Any {
  /**
   * Map [IActionDispatcher] to [Action] using [OutProp].
   * @param dispatch An [IActionDispatcher] instance.
   * @param outProp The [OutProp] instance.
   * @return An [Action] instance.
   */
  fun mapAction(dispatch: IActionDispatcher, outProp: OutProp): Action
}

/**
 * Maps [LState] to [State] and [Action] for a [IPropContainer]. [OutProp] is the view's immutable
 * property as dictated by its parent.
 *
 * For example, a parent view, which contains a list of child views, wants to call
 * [IFullPropInjector.inject] for said children. The [OutProp] generic for these children should
 * therefore be an [Int] that corresponds to their respective indexes in the parent.
 * @param LState The local state type that the global state must extend from.
 * @param OutProp Property as defined by a view's parent.
 * @param State See [ReduxProp.state].
 * @param Action See [ReduxProp.action].
 */
interface IPropMapper<in LState, in OutProp, out State, out Action> :
  IStateMapper<LState, OutProp, State>,
  IActionMapper<OutProp, Action>
  where LState : Any, State : Any, Action : Any
