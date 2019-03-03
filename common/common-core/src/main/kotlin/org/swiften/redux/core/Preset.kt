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

  /** Replace the current [State] with [state]. */
  data class ReplaceState<out State>(val state: State) : DefaultReduxAction()

  /** Replace the current [GState] with [fn]. */
  class MapState<GState>(val fn: (GState) -> GState) : DefaultReduxAction()
}

/**
 * Default wrapper to handle [DefaultReduxAction]. Pass in a [reducer] instance to handle non-
 * [DefaultReduxAction].
 * @param GState The global state type.
 * @param reducer See [IReduxStore.reducer].
 */
class ReduxReducerWrapper<GState>(private val reducer: IReducer<GState, IReduxAction>) :
  IReducer<GState, IReduxAction> by reducer {
  @Suppress("UNCHECKED_CAST")
  @Throws(ClassCastException::class)
  override operator fun invoke(previous: GState, action: IReduxAction): GState {
    return when (action) {
      is DefaultReduxAction -> when (action) {
        is DefaultReduxAction.Dummy -> previous
        is DefaultReduxAction.ReplaceState<*> -> action.state as GState
        is DefaultReduxAction.MapState<*> -> (action.fn as (GState) -> GState)(previous)
        is DefaultReduxAction.Deinitialize -> this.reducer(previous, action)
      }
      else -> this.reducer(previous, action)
    }
  }
}
