/*
 * Copyright (c) haipham 2018. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.middleware

import org.swiften.redux.core.IReduxAction
import org.swiften.redux.core.IReduxDispatcher
import org.swiften.redux.core.IReduxStateGetter
import org.swiften.redux.core.IReduxStore

/** Created by haipham on 2018/12/16 */
/** Map one [ReduxDispatchWrapper] to another */
typealias ReduxDispatchMapper = Function1<ReduxDispatchWrapper, ReduxDispatchWrapper>

/**
 * Represents a Redux middleware that accepts an [ReduxMiddlewareInput] and produces a
 * [ReduxDispatchWrapper].
 */
typealias IReduxMiddleware<State> = Function1<ReduxMiddlewareInput<State>, ReduxDispatchMapper>

/** Implement [IReduxMiddlewareProvider] to provide a middleware instance */
interface IReduxMiddlewareProvider<State> {
  /** Wrap a [ReduxDispatchWrapper.dispatch] to provide extra functionalities */
  val middleware: IReduxMiddleware<State>
}

/** Input for middlewares that includes some functionalities from [IReduxStore] */
class ReduxMiddlewareInput<State>(val stateGetter: IReduxStateGetter<State>)

/** Use this [ReduxDispatchWrapper] to track the ordering of [dispatch] wrapping using [id] */
class ReduxDispatchWrapper(val id: String, val dispatch: IReduxDispatcher)

/** Enhance a [store] by overriding its [IReduxStore.dispatch] with [dispatch] */
private class EnhancedReduxStore<State>(
  val store: IReduxStore<State>,
  override val dispatch: Function1<IReduxAction, Unit>
) : IReduxStore<State> by store

/**
 * Combine [middlewares] into a master [IReduxMiddleware] and apply it to a
 * [IReduxStore.dispatch] to produce a [ReduxDispatchWrapper].
 */
internal fun <State> combineReduxMiddlewares(
  middlewares: Collection<IReduxMiddleware<State>>
): (IReduxStore<State>) -> ReduxDispatchWrapper {
  return fun(store): ReduxDispatchWrapper {
    val input = ReduxMiddlewareInput(store.stateGetter)
    val rootWrapper = ReduxDispatchWrapper("root", store.dispatch)
    if (middlewares.isEmpty()) return rootWrapper

    return middlewares.reduce { acc, middleware -> { input -> {
      acc(input)(middleware(input)(it))
    } } }(input)(rootWrapper)
  }
}

/** Apply [middlewares] to a [IReduxStore] instance to get an enhanced [IReduxStore] */
fun <State> applyReduxMiddlewares(
  middlewares: Collection<IReduxMiddleware<State>>
): (IReduxStore<State>) -> IReduxStore<State> = fun(store): IReduxStore<State> {
  val wrapper = combineReduxMiddlewares(middlewares)(store)
  return EnhancedReduxStore(store, wrapper.dispatch)
}

/**
 * Apply [middlewares] to a [IReduxStore] instance. This is a convenience method that uses
 * varargs.
 */
fun <State> applyReduxMiddlewares(vararg middlewares: IReduxMiddleware<State>) =
  applyReduxMiddlewares(middlewares.asList())
