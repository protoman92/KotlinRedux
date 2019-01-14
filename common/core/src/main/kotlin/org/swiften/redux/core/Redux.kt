/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.core

/** Created by haipham on 2018/03/31 */
/** Represents an [IReduxAction] dispatcher */
typealias IReduxDispatcher = Function1<IReduxAction, Unit>

/**
 * Represents a Redux reducer that reduce a [IReduxAction] onto a previous state to produce a new
 * state.
 */
typealias IReduxReducer<State> = Function2<State, IReduxAction, State>

/** Get the last internal state */
typealias IReduxStateGetter<State> = Function0<State>

/**
 * Subscribe to state updates with a callback. The subscriber id should be a unique id that
 * identifies that subscriber. The resulting [ReduxSubscription] can be used to unsubscribe.
 */
typealias IReduxSubscriber<State> = Function2<String, Function1<State, Unit>, ReduxSubscription>

/**
 * Use this class to perform some [unsubscribe] logic. For e.g.: terminate a [ReduxSubscription]
 * from [IReduxStore.subscribe].
 */
class ReduxSubscription(val unsubscribe: () -> Unit)

/** Represents a Redux action */
interface IReduxAction

/** Represents an object that can dispatch [IReduxAction] */
interface IReduxDispatchContainer {
  val dispatch: IReduxDispatcher
}

/** Represents an object that has some internal [State] and can fetch the latest [State] */
interface IReduxStateContainer<State> {
  val stateGetter: IReduxStateGetter<State>
}

/**
 * Represents a Redux store that can dispatch [IReduxAction] with a [IReduxDispatcher] to mutate
 * some internal [State]. Other objects can subscribe to [State] updates using [subscribe].
 */
interface IReduxStore<State> : IReduxDispatchContainer,
  IReduxStateContainer<State> {
  val subscribe: IReduxSubscriber<State>
}

/** Dispatch all [actions] in a [Collection] of [IReduxAction] */
fun <State> IReduxStore<State>.dispatch(actions: Collection<IReduxAction>) {
  actions.forEach { this.dispatch(it) }
}

/** Dispatch all [actions] passed via vararg parameters */
fun <State> IReduxStore<State>.dispatch(vararg actions: IReduxAction) {
  this.dispatch(actions.asList())
}
