package org.swiften.redux

import org.swiften.kotlinfp.Option

/**
 * Created by haipham on 31/3/18.
 */
class Redux {
  /**
   * Represents a Redux action.
   */
  interface IAction {}

  /**
   * Represents a redux reducer that reduce a [IAction] onto a previous
   * [State] to produce a new [State]
   */
  interface IReducer<State> {
    /**
     * Reduce [action] onto [previous] to produce a new [State].
     */
    operator fun invoke(previous: State, action: IAction): State
  }

  /**
   * Represents a Redux store that can dispatch [IAction] to mutate some
   * internal [State].
   */
  interface IStore<State> {
    val lastState: Option<State>

    /**
     * Dispatch [actions] to mutate some internal state.
     */
    fun dispatch(actions: Collection<IAction>)

    /**
     * Convenience method to dispatch [actions].
     */
    fun dispatch(vararg actions: IAction) {
      dispatch(actions.asList())
    }
  }
}
