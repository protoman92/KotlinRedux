/*
 * Copyright (c) haipham 2018. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.core

/** Created by haipham on 2018/12/16 */
/** Default [IReduxAction] implementation */
sealed class DefaultAction : IReduxAction {
  object Dummy : DefaultAction()

  /** Replace the current [State] with [state] */
  class ReplaceState<out State>(val state: State) : DefaultAction()

  /** Replace the current [State] with [fn] */
  class MapState<State>(val fn: (State) -> State) : DefaultAction()
}

/** Default wrapper to handle [DefaultAction] */
class ReducerWrapper<State>(private val reducer: IReduxReducer<State>) :
  IReduxReducer<State> by reducer {
  @Suppress("UNCHECKED_CAST")
  @Throws(ClassCastException::class)
  override operator fun invoke(previous: State, action: IReduxAction): State {
    return when (action) {
      is DefaultAction -> when (action) {
        is DefaultAction.Dummy -> previous
        is DefaultAction.ReplaceState<*> -> action.state as State
        is DefaultAction.MapState<*> -> (action.fn as (State) -> State)(previous)
      }
      else -> reducer(previous, action)
    }
  }
}
