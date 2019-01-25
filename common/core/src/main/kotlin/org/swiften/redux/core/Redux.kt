/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.core

/** Created by haipham on 2018/03/31 */
/** Represents an [IReduxAction] dispatcher */
typealias IActionDispatcher = (IReduxAction) -> Unit

/**
 * Represents a Redux reducer that reduce a [IReduxAction] onto a previous state to produce a new
 * state.
 */
typealias IReducer<GlobalState> = (GlobalState, IReduxAction) -> GlobalState

/** Get the last internal state */
typealias IStateGetter<GlobalState> = () -> GlobalState

/**
 * Subscribe to state updates with a callback. The subscriber id should be a unique id that
 * identifies that subscriber. The resulting [ReduxSubscription] can be used to unsubscribe.
 */
typealias IReduxSubscriber<GlobalState> = (String, (GlobalState) -> Unit) -> IReduxSubscription

/** Perform some deinitialization logic */
typealias IDeinitializer = Function0<Unit>

/** Represents a Redux action */
interface IReduxAction

/** Represents an object that provides [IReducer] */
interface IReducerProvider<GlobalState> {
  val reducer: IReducer<GlobalState>
}

/** Represents an object that provides [IActionDispatcher] */
interface IDispatcherProvider {
  val dispatch: IActionDispatcher
}

/** Represents an object that provides [IStateGetter] */
interface IStateGetterProvider<GlobalState> {
  val lastState: IStateGetter<GlobalState>
}

/** Represents an object that provides [IDeinitializer] */
interface IDeinitializerProvider {
  val deinitialize: IDeinitializer
}

/** Represents an object that provides [IReduxSubscriber] */
interface IReduxSubscriberProvider<GlobalState> {
  val subscribe: IReduxSubscriber<GlobalState>
}

/**
 * Represents a Redux store that can dispatch [IReduxAction] with a [IActionDispatcher] to mutate
 * some internal [GlobalState]. Other objects can subscribe to [GlobalState] updates using
 * [subscribe].
 */
interface IReduxStore<GlobalState> :
  IReducerProvider<GlobalState>,
  IDispatcherProvider,
  IStateGetterProvider<GlobalState>,
  IReduxSubscriberProvider<GlobalState>,
  IDeinitializerProvider
