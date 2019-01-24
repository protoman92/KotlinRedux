/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.core

import java.util.concurrent.atomic.AtomicBoolean

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

/** Perform some initialization logic */
typealias IReduxInitializer = Function0<Unit>

/** Perform some deinitialization logic */
typealias IReduxDeinitializer = Function0<Unit>

/** Represents a Redux action */
interface IReduxAction

/** Represents an object that provides [IReduxReducer] */
interface IReduxReducerProvider<State> {
  val reducer: IReduxReducer<State>
}

/** Represents an object that provides [IReduxDispatcher] */
interface IReduxDispatcherProvider {
  val dispatch: IReduxDispatcher
}

/** Represents an object that provides [IReduxStateGetter] */
interface IReduxStateGetterProvider<State> {
  val lastState: IReduxStateGetter<State>
}

/** Represents an object that provides [IReduxDeinitializer] */
interface IReduxDeinitializerProvider {
  val deinitialize: IReduxDeinitializer
}

/** Represents an object that provides [IReduxSubscriber] */
interface IReduxSubscriberProvider<State> {
  val subscribe: IReduxSubscriber<State>
}

/**
 * Represents a Redux store that can dispatch [IReduxAction] with a [IReduxDispatcher] to mutate
 * some internal [State]. Other objects can subscribe to [State] updates using [subscribe].
 */
interface IReduxStore<State> :
  IReduxReducerProvider<State>,
  IReduxDispatcherProvider,
  IReduxStateGetterProvider<State>,
  IReduxSubscriberProvider<State>,
  IReduxDeinitializerProvider
