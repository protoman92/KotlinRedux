/*
 * Copyright (c) haipham 2018. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.core

/**
 * Created by haipham on 2018/12/16.
 */
/** Top-level namespace for presets */
object ReduxPreset {
  /** Default [Redux.IAction] implementation */
  sealed class DefaultAction: Redux.IAction {
    object Dummy: DefaultAction()

    /**
     * If this action is dispatched, replace the current state with [state].
     * Beware that we will do a force-cast in [ReducerWrapper] to expected
     * State type, so it will throw a [ClassCastException] if [State] is not
     * the correct type.
     */
    class ReplaceState<out State>(val state: State): DefaultAction() {
      override fun toString(): String = "Replacing state with $state"
    }
  }

  /** Default wrapper to handle [DefaultAction] */
  internal class ReducerWrapper<State>(private val reducer: ReduxReducer<State>) :
    ReduxReducer<State> by reducer
  {
    @Suppress("UNCHECKED_CAST")
    @Throws(ClassCastException::class)
    override operator fun invoke(previous: State, action: Redux.IAction): State {
      return when (action) {
        is DefaultAction -> when (action) {
          is DefaultAction.Dummy -> previous
          is DefaultAction.ReplaceState<*> -> action.state as State
        }
        else -> reducer(previous, action)
      }
    }
  }
}
