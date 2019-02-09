/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.core

/** Created by haipham on 2018/03/31 */
/** Represents an [IReduxAction] dispatcher. */
typealias IActionDispatcher = (IReduxAction) -> Unit

/**
 * Represents a Redux reducer that reduce a [IReduxAction] onto a previous state to produce a new
 * state.
 * @param GState The global state type.
 */
typealias IReducer<GState> = (GState, IReduxAction) -> GState

/**
 * Get the last internal state.
 * @param GState The global state type.
 */
typealias IStateGetter<GState> = () -> GState

/**
 * Subscribe to state updates with a callback. The subscriber id should be a unique id that
 * identifies that subscriber. The resulting [IReduxSubscription] can be used to unsubscribe.
 * @param GState The global state type.
 */
typealias IReduxSubscriber<GState> = (String, (GState) -> Unit) -> IReduxSubscription

/** Perform some deinitialization logic. */
typealias IDeinitializer = Function0<Unit>

/** Represents a Redux action. */
interface IReduxAction

/**
 * Represents an object that provides [IReducer].
 * @param GState The global state type.
 */
interface IReducerProvider<GState> {
  val reducer: IReducer<GState>
}

/** Represents an object that provides [IActionDispatcher]. */
interface IDispatcherProvider {
  val dispatch: IActionDispatcher
}

/**
 * Represents an object that provides [IStateGetter].
 * @param GState The global state type.
 */
interface IStateGetterProvider<out GState> {
  val lastState: IStateGetter<GState>
}

/** Represents an object that provides [IDeinitializer]. */
interface IDeinitializerProvider {
  val deinitialize: IDeinitializer
}

/**
 * Represents an object that provides [IReduxSubscriber].
 * @param GState The global state type.
 */
interface IReduxSubscriberProvider<out GState> {
  val subscribe: IReduxSubscriber<GState>
}

/**
 * Represents a Redux store that can dispatch [IReduxAction] with a [IActionDispatcher] to mutate
 * some internal [GState]. Other objects can subscribe to [GState] updates using
 * [subscribe].
 * @param GState The global state type.
 */
interface IReduxStore<GState> :
  IReducerProvider<GState>,
  IDispatcherProvider,
  IStateGetterProvider<GState>,
  IReduxSubscriberProvider<GState>,
  IDeinitializerProvider
