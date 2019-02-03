/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.core

/** Created by haipham on 2018/12/16 */
/** Default [IReduxAction] implementation */
sealed class DefaultReduxAction : IReduxAction {
  object Dummy : DefaultReduxAction()
  object Deinitialize : DefaultReduxAction()

  /** Replace the current [State] with [state] */
  class ReplaceState<out State>(val state: State) : DefaultReduxAction()

  /** Replace the current [GlobalState] with [fn] */
  class MapState<GlobalState>(val fn: (GlobalState) -> GlobalState) : DefaultReduxAction()
}

/**
 * Default wrapper to handle [DefaultReduxAction]. Pass in a [reducer] instance to handle non-
 * [DefaultReduxAction].
 * @param GlobalState The global state type.
 * @param reducer See [IReduxStore.reducer].
 */
class ReduxReducerWrapper<GlobalState>(private val reducer: IReducer<GlobalState>) :
  IReducer<GlobalState> by reducer {
  @Suppress("UNCHECKED_CAST")
  @Throws(ClassCastException::class)
  override operator fun invoke(previous: GlobalState, action: IReduxAction): GlobalState {
    return when (action) {
      is DefaultReduxAction -> when (action) {
        is DefaultReduxAction.Dummy -> previous
        is DefaultReduxAction.ReplaceState<*> -> action.state as GlobalState
        is DefaultReduxAction.MapState<*> -> (action.fn as (GlobalState) -> GlobalState)(previous)
        is DefaultReduxAction.Deinitialize -> this.reducer(previous, action)
      }
      else -> this.reducer(previous, action)
    }
  }
}
