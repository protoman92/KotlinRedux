/*
 * Copyright (c) haipham 2018. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.core

/**
 * Created by haipham on 2018/03/31.
 */
/**
 * Top-level namespace for Redux root components.
 */
object Redux {
  /**
   * Use this class to perform some [unsubscribe] logic. For e.g.: terminate
   * a [Subscription] from [IStore.subscribe].
   */
  class Subscription(val unsubscribe: () -> Unit)

  /**
   * Represents a Redux action.
   */
  interface IAction

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
   * Get the last internal [State].
   */
  interface ILastState<State> {
    operator fun invoke(): State
  }

  /**
   * Dispatch an [IAction] and feed it to an [IReducer].
   */
  interface IDispatcher {
    operator fun invoke(action: IAction)
  }

  /**
   * Subscribe to [State] updates with a callback.
   */
  interface ISubscribe<State> {
    /**
     * Subscribe to [State] updates with [callback]. The [subscriberId] should
     * be a unique id that identifies that subscriber.
     * The resulting [Subscription] can be used to unsubscribe.
     */
    operator fun invoke(subscriberId: String, callback: (State) -> Unit): Subscription
  }

  /**
   * Represents a Redux store that can dispatch [IAction] with an [IDispatcher]
   * to mutate some internal [State]. Other objects can subscribe to [State]
   * updates using [subscribe].
   */
  interface IStore<State> {
    val lastState: ILastState<State>
    val dispatch: IDispatcher
    val subscribe: ISubscribe<State>
  }
}

/**
 * Dispatch all [actions] in a [Collection] of [Redux.IAction].
 */
fun <State> Redux.IStore<State>.dispatch(actions: Collection<Redux.IAction>) {
  actions.forEach { this.dispatch(it) }
}

/**
 * Dispatch all [actions] passed via vararg parameters.
 */
fun <State> Redux.IStore<State>.dispatch(vararg actions: Redux.IAction) {
  this.dispatch(actions.asList())
}
