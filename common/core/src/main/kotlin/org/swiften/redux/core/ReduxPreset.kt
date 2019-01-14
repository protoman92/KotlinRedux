/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.core

/** Created by haipham on 2018/12/16 */
/** Default [IReduxAction] implementation */
sealed class DefaultReduxAction : IReduxAction {
  object Dummy : DefaultReduxAction()

  /** Replace the current [State] with [state] */
  class ReplaceState<out State>(val state: State) : DefaultReduxAction()

  /** Replace the current [State] with [fn] */
  class MapState<State>(val fn: (State) -> State) : DefaultReduxAction()
}

/** Default wrapper to handle [DefaultReduxAction] */
class ReduxReducerWrapper<State>(private val reducer: IReduxReducer<State>) :
  IReduxReducer<State> by reducer {
  @Suppress("UNCHECKED_CAST")
  @Throws(ClassCastException::class)
  override operator fun invoke(previous: State, action: IReduxAction): State {
    return when (action) {
      is DefaultReduxAction -> when (action) {
        is DefaultReduxAction.Dummy -> previous
        is DefaultReduxAction.ReplaceState<*> -> action.state as State
        is DefaultReduxAction.MapState<*> -> (action.fn as (State) -> State)(previous)
      }
      else -> reducer(previous, action)
    }
  }
}
