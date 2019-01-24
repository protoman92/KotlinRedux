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
typealias IReduxReducer<GlobalState> = Function2<GlobalState, IReduxAction, GlobalState>

/** Get the last internal state */
typealias IReduxStateGetter<GlobalState> = Function0<GlobalState>

/**
 * Subscribe to state updates with a callback. The subscriber id should be a unique id that
 * identifies that subscriber. The resulting [ReduxSubscription] can be used to unsubscribe.
 */
typealias IReduxSubscriber<GlobalState> =
  Function2<String, Function1<GlobalState, Unit>, ReduxSubscription>

/** Perform some initialization logic */
typealias IReduxInitializer = Function0<Unit>

/** Perform some deinitialization logic */
typealias IReduxDeinitializer = Function0<Unit>

/** Represents a Redux action */
interface IReduxAction

/** Represents an object that provides [IReduxReducer] */
interface IReduxReducerProvider<GlobalState> {
  val reducer: IReduxReducer<GlobalState>
}

/** Represents an object that provides [IReduxDispatcher] */
interface IReduxDispatcherProvider {
  val dispatch: IReduxDispatcher
}

/** Represents an object that provides [IReduxStateGetter] */
interface IReduxStateGetterProvider<GlobalState> {
  val lastState: IReduxStateGetter<GlobalState>
}

/** Represents an object that provides [IReduxDeinitializer] */
interface IReduxDeinitializerProvider {
  val deinitialize: IReduxDeinitializer
}

/** Represents an object that provides [IReduxSubscriber] */
interface IReduxSubscriberProvider<GlobalState> {
  val subscribe: IReduxSubscriber<GlobalState>
}

/**
 * Represents a Redux store that can dispatch [IReduxAction] with a [IReduxDispatcher] to mutate
 * some internal [GlobalState]. Other objects can subscribe to [GlobalState] updates using
 * [subscribe].
 */
interface IReduxStore<GlobalState> :
  IReduxReducerProvider<GlobalState>,
  IReduxDispatcherProvider,
  IReduxStateGetterProvider<GlobalState>,
  IReduxSubscriberProvider<GlobalState>,
  IReduxDeinitializerProvider
