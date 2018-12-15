package org.swiften.redux

class ReduxPreset {
  /**
   * Default [Redux.IAction] implementation.
   */
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

  /**
   * Default wrapper to handle [DefaultAction].
   */
  internal class ReducerWrapper<State>(
    private val reducer: Redux.IReducer<State>
  ):
      Redux.IReducer<State> by reducer
  {
    @Suppress("UNCHECKED_CAST")
    @Throws(ClassCastException::class)
    override operator fun invoke(previous: State, action: Redux.IAction): State {
      return when {
        action is DefaultAction ->
          when (action) {
            is DefaultAction.Dummy -> previous
            is DefaultAction.ReplaceState<*> -> action.state as State
          }
        else -> reducer(previous, action)
      }
    }
  }
}
