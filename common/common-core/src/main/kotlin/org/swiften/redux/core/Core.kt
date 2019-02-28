/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.core

/** Created by haipham on 2018/03/31 */
/** Represents an [IReduxAction] dispatcher. */
typealias IActionDispatcher = (IReduxAction) -> IAsyncJob<*>

/**
 * Represents a Redux reducer that reduce a [IReduxAction] onto a previous state to produce a new
 * state.
 * @param GState The global state type.
 * @param Action The [IReduxAction] type.
 */
typealias IReducer<GState, Action> = (GState, Action) -> GState

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

/** Unsubscribe from state updates for a specified subscriber id. */
typealias IReduxUnsubscriber = (String) -> Unit

/** Represents a Redux action. */
interface IReduxAction

/** Represents a [IReduxAction] that is identifiable by [key]. */
interface IReduxActionWithKey : IReduxAction {
  val key: String
}

/**
 * Represents an object that provides [IReducer].
 * @param GState The global state type.
 * @param Action The [IReduxAction] type.
 */
interface IReducerProvider<GState, Action> where GState : Any, Action : IReduxAction {
  val reducer: IReducer<GState, Action>
}

/** Represents an object that provides [IActionDispatcher]. */
interface IDispatcherProvider {
  val dispatch: IActionDispatcher
}

/**
 * Represents an object that provides [IStateGetter].
 * @param GState The global state type.
 */
interface IStateGetterProvider<out GState> where GState : Any {
  val lastState: IStateGetter<GState>
}

/** Represents an object that can perform some deinitialization logic. */
interface IDeinitializerProvider {
  /** Perform some deinitialization logic. */
  fun deinitialize()
}

/**
 * Represents an object that provides [IReduxSubscriber].
 * @param GState The global state type.
 */
interface IReduxSubscriberProvider<out GState> where GState : Any {
  val subscribe: IReduxSubscriber<GState>
}

/** Represents an object that provides [IReduxUnsubscriber]. */
interface IReduxUnsubscriberProvider {
  val unsubscribe: IReduxUnsubscriber
}

/**
 * Represents a Redux store that can dispatch [IReduxAction] with a [IActionDispatcher] to mutate
 * some internal [GState]. Other objects can subscribe to [GState] updates using [subscribe].
 * @param GState The global state type.
 */
interface IReduxStore<GState> :
  IReducerProvider<GState, IReduxAction>,
  IDispatcherProvider,
  IStateGetterProvider<GState>,
  IReduxSubscriberProvider<GState>,
  IReduxUnsubscriberProvider,
  IDeinitializerProvider
  where GState : Any

/** [IActionDispatcher] that does not do any dispatching and simply returns [EmptyJob]. */
object NoopActionDispatcher : IActionDispatcher {
  override fun invoke(p1: IReduxAction): IAsyncJob<*> = EmptyJob
}
