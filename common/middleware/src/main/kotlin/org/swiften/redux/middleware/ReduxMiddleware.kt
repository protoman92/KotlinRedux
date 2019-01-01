/*
 * Copyright (c) haipham 2018. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.middleware

import org.swiften.redux.core.Redux
import org.swiften.redux.core.ReduxDispatcher
import org.swiften.redux.core.ReduxStateGetter

/**
 * Created by haipham on 2018/12/16.
 */

/** Map one [ReduxMiddleware.DispatchWrapper] to another */
typealias ReduxDispatchMapper =
  Function1<ReduxMiddleware.DispatchWrapper, ReduxMiddleware.DispatchWrapper>

/**
 * Represents a Redux middleware that accepts an [ReduxMiddleware.Input] and
 * produces a [ReduxMiddleware.DispatchWrapper].
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

  /**
   * [Input] for middlewares that includes some functionalities from
   * [Redux.IStore].
   */
  class Input<State>(val stateGetter: ReduxStateGetter<State>)

  /**
   * Use this [DispatchWrapper] to track the ordering of [dispatch] wrapping
   * using [id].
   */
  class DispatchWrapper(val id: String, val dispatch: ReduxDispatcher)

  /**
   * Enhance a [store] by overriding its [Redux.IStore.dispatch] with
   * [dispatch].
   */
  private class EnhancedStore<State>(
    val store: Redux.IStore<State>,
    override val dispatch: Function1<Redux.IAction, Unit>
  ): Redux.IStore<State> by store

  /**
   * Combine [middlewares] into a master [ReduxMiddlewareCreator] and apply it
   * to a [Redux.IStore.dispatch] to produce a [DispatchWrapper].
   */
  fun <State> combineMiddlewares(
    middlewares: Collection<ReduxMiddlewareCreator<State>>
  ): (Redux.IStore<State>) -> DispatchWrapper {
    return fun(store): DispatchWrapper {
      val input = Input(store.stateGetter)
      val rootWrapper = DispatchWrapper("root", store.dispatch)
      if (middlewares.isEmpty()) return rootWrapper

      return middlewares.reduce { acc, middleware -> { input -> {
        acc(input)(middleware(input)(it))
      } } }(input)(rootWrapper)
    }
  }

  /**
   * Apply [middlewares] to a [Redux.IStore] instance to get an enhanced
   * [Redux.IStore].
   */
  fun <State> applyMiddlewares(
    middlewares: Collection<ReduxMiddlewareCreator<State>>
  ): (Redux.IStore<State>) -> Redux.IStore<State> =
    fun(store): Redux.IStore<State> {
      val wrapper = combineMiddlewares(middlewares)(store)
      return EnhancedStore(store, wrapper.dispatch)
    }

  /**
   * Apply [middlewares] to a [Redux.IStore] instance. This is a convenience
   * method that uses varargs.
   */
  fun <State> applyMiddlewares(
    vararg middlewares: ReduxMiddlewareCreator<State>
  ) = applyMiddlewares(middlewares.asList())
}
