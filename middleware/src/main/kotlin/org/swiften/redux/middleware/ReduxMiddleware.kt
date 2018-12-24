/*
 * Copyright (c) haipham 2018. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.middleware

import org.swiften.redux.core.Redux

/**
 * Created by haipham on 2018/12/16.
 */
/** Top-level namespace for Redux middlewares */
object ReduxMiddleware {
  /** Map one [DispatchWrapper] to another */
  interface IDispatchMapper {
    operator fun invoke(wrapper: DispatchWrapper): DispatchWrapper
  }

  /**
   * Represents a Redux middleware that accepts an [Input] and produces a
   * [DispatchWrapper].
   */
  interface IMiddleware<State> {
    operator fun invoke(input: Input<State>): IDispatchMapper
  }

  /** Implement [IProvider] to provide a middleware instance */
  interface IProvider<State> {
    /** Wrap a [DispatchWrapper.dispatch] to provide extra functionalities */
    val middleware: IMiddleware<State>
  }

  /**
   * [Input] for middlewares that includes some functionalities from
   * [Redux.IStore].
   */
  class Input<State>(val lastState: Redux.ILastState<State>)

  /**
   * Use this [DispatchWrapper] to track the ordering of [dispatch] wrapping
   * using [id].
   */
  class DispatchWrapper(val id: String, val dispatch: Redux.IDispatcher)

  /**
   * Enhance a [store] by overriding its [Redux.IStore.dispatch] with
   * [dispatch].
   */
  private class EnhancedStore<State>(
    val store: Redux.IStore<State>,
    override val dispatch: Redux.IDispatcher
  ): Redux.IStore<State> by store

  /**
   * Combine [middlewares] into a master [IMiddleware] and apply it to a
   * [Redux.IStore.dispatch] to produce a [DispatchWrapper].
   */
  fun <State> combineMiddlewares(
    middlewares: Collection<IMiddleware<State>>
  ): (Redux.IStore<State>) -> DispatchWrapper {
    return fun(store): DispatchWrapper {
      val input = Input(store.lastState)
      val rootWrapper = DispatchWrapper("root", store.dispatch)
      if (middlewares.isEmpty()) return rootWrapper

      return middlewares.reduce { acc, middleware -> object : IMiddleware<State> {
        override operator fun invoke(input: Input<State>) = object : IDispatchMapper {
          override operator fun invoke(wrapper: DispatchWrapper): DispatchWrapper {
            return acc(input)(middleware(input)(wrapper))
          }
        }
      } }(input)(rootWrapper)
    }
  }

  /**
   * Apply [middlewares] to a [Redux.IStore] instance to get an enhanced
   * [Redux.IStore].
   */
  fun <State> applyMiddlewares(
    middlewares: Collection<IMiddleware<State>>
  ): (Redux.IStore<State>) -> Redux.IStore<State> {
    return fun(store): Redux.IStore<State> {
      val wrapper = combineMiddlewares(middlewares)(store)
      return EnhancedStore(store, wrapper.dispatch)
    }
  }

  /**
   * Apply [middlewares] to a [Redux.IStore] instance. This is a convenience
   * method that uses varargs.
   */
  fun <State> applyMiddlewares(
    vararg middlewares: IMiddleware<State>
  ): (Redux.IStore<State>) -> Redux.IStore<State> {
    return applyMiddlewares(middlewares.asList())
  }
}
