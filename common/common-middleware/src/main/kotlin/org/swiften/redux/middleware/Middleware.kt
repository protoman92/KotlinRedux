/*
 * Copyright (c) haipham 2018. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.middleware

import org.swiften.redux.core.IActionDispatcher
import org.swiften.redux.core.IReduxAction
import org.swiften.redux.core.IReduxStore
import org.swiften.redux.core.IStateGetter

/** Created by haipham on 2018/12/16 */
/** Map one [DispatchWrapper] to another */
typealias DispatchMapper = (DispatchWrapper) -> DispatchWrapper

/**
 * Represents a Redux middleware that accepts an [MiddlewareInput] and produces a
 * [DispatchWrapper].
 */
typealias IMiddleware<GlobalState> = (MiddlewareInput<GlobalState>) -> DispatchMapper

/** Input for middlewares that includes some functionalities from [IReduxStore] */
class MiddlewareInput<out GlobalState>(val stateGetter: IStateGetter<GlobalState>)

/** Use this [DispatchWrapper] to track the ordering of [dispatch] wrapping using [id] */
class DispatchWrapper(val id: String, val dispatch: IActionDispatcher)

/** Enhance a [store] by overriding its [IReduxStore.dispatch] with [dispatch] */
private class EnhancedReduxStore<GlobalState>(
  private val store: IReduxStore<GlobalState>,
  override val dispatch: (IReduxAction) -> Unit
) : IReduxStore<GlobalState> by store

/**
 * Combine [middlewares] into a master [IMiddleware] and apply it to a [IReduxStore.dispatch] to
 * produce a [DispatchWrapper].
 */
internal fun <GlobalState> combineMiddlewares(
  middlewares: Collection<IMiddleware<GlobalState>>
): (IReduxStore<GlobalState>) -> DispatchWrapper {
  return fun(store): DispatchWrapper {
    val input = MiddlewareInput(store.lastState)
    val rootWrapper = DispatchWrapper("root", store.dispatch)
    if (middlewares.isEmpty()) return rootWrapper

    return middlewares.reduce { acc, middleware ->
      { input -> { acc(input)(middleware(input)(it)) } }
    }(input)(rootWrapper)
  }
}

/** Apply [middlewares] to a [IReduxStore] instance to get an enhanced [IReduxStore] */
fun <GlobalState> applyMiddlewares(
  middlewares: Collection<IMiddleware<GlobalState>>
): (IReduxStore<GlobalState>) -> IReduxStore<GlobalState> = fun(store): IReduxStore<GlobalState> {
  val wrapper = combineMiddlewares(middlewares)(store)
  return EnhancedReduxStore(store, wrapper.dispatch)
}

/**
 * Apply [middlewares] to a [IReduxStore] instance. This is a convenience method that uses
 * varargs.
 */
fun <GlobalState> applyMiddlewares(vararg middlewares: IMiddleware<GlobalState>) =
  applyMiddlewares(middlewares.asList())
