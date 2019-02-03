/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.core

/** Created by haipham on 2018/12/16 */
/** Map one [DispatchWrapper] to another. */
typealias DispatchMapper = (DispatchWrapper) -> DispatchWrapper

/**
 * Represents a Redux middleware that accepts an [MiddlewareInput] and produces a
 * [DispatchWrapper].
 * @param GlobalState The global state type.
 */
typealias IMiddleware<GlobalState> = (MiddlewareInput<GlobalState>) -> DispatchMapper

/**
 * Input for middlewares that includes some functionalities from [IReduxStore].
 * @param GlobalState The global state type.
 * @param stateGetter See [IReduxStore.lastState].
 * @param subscriber See [IReduxStore.subscribe].
 */
class MiddlewareInput<out GlobalState>(
  val stateGetter: IStateGetter<GlobalState>,
  val subscriber: IReduxSubscriber<GlobalState>
)

/**
 * Use this [DispatchWrapper] to track the ordering of [dispatch] wrapping using [id].
 * @param id A [String] value.
 * @param dispatch See [IReduxStore.dispatch].
 */
class DispatchWrapper(val id: String, val dispatch: IActionDispatcher) {
  companion object {
    const val ROOT_WRAPPER = "root"
  }
}

/**
 * Enhance a [store] by overriding its [IReduxStore.dispatch] with [dispatch].
 * @param GlobalState The global state type.
 * @param store An [IReduxStore] instance.
 * @param dispatch An overriding [IActionDispatcher] instance.
 */
private class EnhancedReduxStore<GlobalState>(
  private val store: IReduxStore<GlobalState>,
  override val dispatch: (IReduxAction) -> Unit
) : IReduxStore<GlobalState> by store

/**
 * Combine [middlewares] into a master [IMiddleware] and apply it to a [IReduxStore.dispatch] to
 * produce a [DispatchWrapper].
 * @param GlobalState The global state type.
 * @param middlewares The [IMiddleware] instances to be applied to an [IReduxStore].
 * @return Function that maps [IReduxStore.dispatch] to a [DispatchMapper].
 */
internal fun <GlobalState> combineMiddlewares(
  middlewares: Collection<IMiddleware<GlobalState>>
): (IReduxStore<GlobalState>) -> DispatchWrapper {
  return fun(store): DispatchWrapper {
    val input = MiddlewareInput(store.lastState, store.subscribe)
    val rootWrapper = DispatchWrapper(DispatchWrapper.ROOT_WRAPPER, store.dispatch)
    if (middlewares.isEmpty()) return rootWrapper

    return middlewares.reduce { acc, middleware ->
      { input -> { acc(input)(middleware(input)(it)) } }
    }(input)(rootWrapper)
  }
}

/**
 * Apply [middlewares] to a [IReduxStore] instance to get an enhanced [IReduxStore]
 * @param GlobalState The global state type.
 * @param middlewares The [IMiddleware] instances to be applied to an [IReduxStore].
 * @return Function that maps an [IReduxStore] to an [EnhancedReduxStore].
 */
fun <GlobalState> applyMiddlewares(
  middlewares: Collection<IMiddleware<GlobalState>>
): (IReduxStore<GlobalState>) -> IReduxStore<GlobalState> = fun(store): IReduxStore<GlobalState> {
  val wrapper = combineMiddlewares(middlewares)(store)
  return EnhancedReduxStore(store, wrapper.dispatch)
}

/**
 * Apply [middlewares] to a [IReduxStore] instance. This is a convenience method that uses
 * varargs.
 * @param GlobalState The global state type.
 * @param middlewares The [IMiddleware] instances to be applied to an [IReduxStore].
 * @return Function that maps an [IReduxStore] to an [EnhancedReduxStore].
 */
fun <GlobalState> applyMiddlewares(vararg middlewares: IMiddleware<GlobalState>): (IReduxStore<GlobalState>) -> IReduxStore<GlobalState> {
  return applyMiddlewares(middlewares.asList())
}
