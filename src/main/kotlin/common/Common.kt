package common

import Option

/**
 * Created by haipham on 31/3/18.
 */

/**
 * Represents a Redux action.
 */
interface ReduxActionType

/**
 * Default [ReduxActionType].
 */
enum class DefaultReduxAction: ReduxActionType {
  DUMMY
}

/**
 * Represents a Redux store that can dispatch [ReduxActionType] to mutate some
 * internal [State].
 */
interface ReduxStoreType<State> {
  val lastState: Option<State>

  /**
   * Dispatch [actions] to mutate some internal state.
   */
  fun dispatch(actions: Collection<ReduxActionType>)

  /**
   * Convenience method to dispatch [actions].
   */
  fun dispatch(vararg actions: ReduxActionType) {
    dispatch(actions.asList())
  }
}

/**
 * Represents a redux reducer that reduce a [ReduxActionType] onto a previous
 * [State] to produce a new [State]
 */
interface ReduxReducer<State> {
  /**
   * Reduce [action] onto [previous] to produce a new [State].
   */
  operator fun invoke(previous: State, action: ReduxActionType): State
}