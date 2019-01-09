/*
 * Copyright (c) haipham 2018. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.middleware

import org.swiften.redux.core.IReduxAction
import org.swiften.redux.core.IReduxStore
import org.swiften.redux.core.IReduxDispatcher
import org.swiften.redux.core.IReduxStateGetter

/** Created by haipham on 2018/12/16 */
/** Map one [ReduxMiddleware.DispatchWrapper] to another */
typealias ReduxDispatchMapper =
  Function1<ReduxMiddleware.DispatchWrapper, ReduxMiddleware.DispatchWrapper>

/**
 * Represents a Redux middleware that accepts an [ReduxMiddleware.Input] and produces a
 * [ReduxMiddleware.DispatchWrapper].
 */
typealias ReduxMiddlewareCreator<State> =
  Function1<ReduxMiddleware.Input<State>, ReduxDispatchMapper>

/** Top-level namespace for Redux middlewares */
object ReduxMiddleware {

  /** Implement [IProvider] to provide a middleware instance */
  interface IProvider<State> {
    /** Wrap a [DispatchWrapper.dispatch] to provide extra functionalities */
    val middleware: ReduxMiddlewareCreator<State>
  }

  /** [Input] for middlewares that includes some functionalities from [IReduxStore] */
  class Input<State>(val stateGetter: IReduxStateGetter<State>)

  /** Use this [DispatchWrapper] to track the ordering of [dispatch] wrapping using [id] */
  class DispatchWrapper(val id: String, val dispatch: IReduxDispatcher)

  /** Enhance a [store] by overriding its [IReduxStore.dispatch] with [dispatch] */
  private class EnhancedStore<State>(
    val store: IReduxStore<State>,
    override val dispatch: Function1<IReduxAction, Unit>
  ) : IReduxStore<State> by store

  /**
   * Combine [middlewares] into a master [ReduxMiddlewareCreator] and apply it to a
   * [IReduxStore.dispatch] to produce a [DispatchWrapper].
   */
  fun <State> combineMiddlewares(
    middlewares: Collection<ReduxMiddlewareCreator<State>>
  ): (IReduxStore<State>) -> DispatchWrapper {
    return fun(store): DispatchWrapper {
      val input = Input(store.stateGetter)
      val rootWrapper = DispatchWrapper("root", store.dispatch)
      if (middlewares.isEmpty()) return rootWrapper

      return middlewares.reduce { acc, middleware -> { input -> {
        acc(input)(middleware(input)(it))
      } } }(input)(rootWrapper)
    }
  }

  /** Apply [middlewares] to a [IReduxStore] instance to get an enhanced [IReduxStore] */
  fun <State> applyMiddlewares(
    middlewares: Collection<ReduxMiddlewareCreator<State>>
  ): (IReduxStore<State>) -> IReduxStore<State> =
    fun(store): IReduxStore<State> {
      val wrapper = combineMiddlewares(middlewares)(store)
      return EnhancedStore(store, wrapper.dispatch)
    }

  /**
   * Apply [middlewares] to a [IReduxStore] instance. This is a convenience method that uses
   * varargs.
   */
  fun <State> applyMiddlewares(
    vararg middlewares: ReduxMiddlewareCreator<State>
  ) = applyMiddlewares(middlewares.asList())
}
