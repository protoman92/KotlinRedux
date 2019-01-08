/*
 * Copyright (c) haipham 2018. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.core

/** Created by haipham on 2018/03/31 */
/** Represents an [Redux.IAction] dispatcher */
typealias ReduxDispatcher = Function1<Redux.IAction, Unit>

/**
 * Represents a Redux reducer that reduce a [Redux.IAction] onto a previous [State] to produce a
 * new [State].
 */
typealias ReduxReducer<State> = Function2<State, Redux.IAction, State>

/** Get the last internal [State] */
typealias ReduxStateGetter<State> = Function0<State>

/**
 * Subscribe to state updates with a callback. The subscriber id should be a unique id that
 * identifies that subscriber. The resulting [Redux.Subscription] can be used to unsubscribe.
 */
typealias ReduxSubscriber<State> = Function2<String, Function1<State, Unit>, Redux.Subscription>

/** Top-level namespace for Redux root components */
object Redux {
  /**
   * Use this class to perform some [unsubscribe] logic. For e.g.: terminate a [Subscription] from
   * [IStore.subscribe].
   */
  class Subscription(val unsubscribe: () -> Unit)

  /** Represents a Redux action */
  interface IAction

  /**
   * Represents a Redux store that can dispatch [IAction] with a [ReduxDispatcher] to mutate some
   * internal [State]. Other objects can subscribe to [State] updates using [subscribe].
   */
  interface IStore<State> {
    val stateGetter: ReduxStateGetter<State>
    val dispatch: ReduxDispatcher
    val subscribe: ReduxSubscriber<State>
  }
}

/** Dispatch all [actions] in a [Collection] of [Redux.IAction] */
fun <State> Redux.IStore<State>.dispatch(actions: Collection<Redux.IAction>) {
  actions.forEach { this.dispatch(it) }
}

/** Dispatch all [actions] passed via vararg parameters */
fun <State> Redux.IStore<State>.dispatch(vararg actions: Redux.IAction) {
  this.dispatch(actions.asList())
}
