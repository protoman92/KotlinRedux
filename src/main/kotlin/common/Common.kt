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
sealed class DefaultReduxAction: ReduxActionType {
  object Dummy: DefaultReduxAction()

  /**
   * If this action is dispatched, replace the current state with [state].
   * Beware that we will do a force-cast in [ReduxReducerWrapper] to expected
   * State type, so it will throw a [ClassCastException] if [State] is not the
   * correct type.
   */
  class ReplaceState<out State>(val state: State): DefaultReduxAction() {
    override fun toString(): String = "Replacing state with $state"
  }
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

/**
 * Default wrapper to handle [DefaultReduxAction].
 */
internal class ReduxReducerWrapper<State>(private val reducer: ReduxReducer<State>):
  ReduxReducer<State> by reducer
{
  @Suppress("UNCHECKED_CAST")
  @Throws(ClassCastException::class)
  override operator fun invoke(previous: State, action: ReduxActionType): State {
    return when (action) {
      is DefaultReduxAction ->
        when (action) {
          is DefaultReduxAction.Dummy -> previous
          is DefaultReduxAction.ReplaceState<*> -> action.state as State
        }

      else -> reducer.invoke(previous, action)
    }
  }
}