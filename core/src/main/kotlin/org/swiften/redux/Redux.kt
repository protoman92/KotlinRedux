package org.swiften.redux

import org.swiften.kotlinfp.Option

/**
 * Created by haipham on 31/3/18.
 */
class Redux {
  class Subscription(val unsubscribe: () -> Unit)

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
    /**
     * Get the last [State] instance.
     */
    fun lastState(): State

    /**
     * Dispatch an [action] to mutate some internal [State].
     */
    fun dispatch(action: IAction)

    /**
     * Subscribe to [State] updates with [callback]. The [subscriberId] should
     * be a unique id that identifies that subscriber.
     * The resulting [Subscription] can be used to unsubscribe.
     */
    fun subscribe(subscriberId: String, callback: (State) -> Unit): Subscription
  }
}
