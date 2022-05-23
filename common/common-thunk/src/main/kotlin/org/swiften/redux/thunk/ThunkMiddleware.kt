/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.thunk

import org.swiften.redux.core.BatchAwaitable
import org.swiften.redux.core.DefaultReduxAction
import org.swiften.redux.core.DispatchMapper
import org.swiften.redux.core.DispatchWrapper
import org.swiften.redux.core.EmptyAwaitable
import org.swiften.redux.core.FutureAwaitable
import org.swiften.redux.core.IActionDispatcher
import org.swiften.redux.core.IAwaitable
import org.swiften.redux.core.IMiddleware
import org.swiften.redux.core.IReduxAction
import org.swiften.redux.core.IStateGetter
import org.swiften.redux.core.JustAwaitable
import org.swiften.redux.core.MiddlewareInput
import org.swiften.redux.core.ThreadSafeDispatcher
import org.swiften.redux.core.map
import java.util.concurrent.Callable
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.Future
import java.util.concurrent.locks.ReentrantLock

/** Created by haipham on 2019/02/05 */
/**
 * A thunk function is one that can be resolved asynchronously. It is used mainly as
 * [IReduxThunkAction.payload].
 * @param GState The global state type.
 * @param GExt The external argument type.
 */
typealias ThunkFunction<GState, GExt> = (IActionDispatcher, IStateGetter<GState>, GExt) -> Unit

/**
 * A thunk action represents an [IReduxAction] whose [payload] is a function that can be resolved
 * asynchronously. We can use these functions to perform simple async logic, such as fetching data
 * from a remote API and then dispatch the result to populate the [GState] implementation with said
 * data.
 *
 * Beware that there is no way to properly type check these [IReduxAction], so it is up to the
 * developer to ensure [GState], [GExt] and [Param] do not fail casting checks with
 * [ClassCastException].
 * @param GState The global state type.
 * @param GExt The external argument type.
 * @param Param The parameters with which to construct this [IReduxThunkAction]. This is mainly
 * used for equality check.
 */
interface IReduxThunkAction<GState, GExt, Param> : IReduxAction {
  val params: Param
  val payload: ThunkFunction<GState, GExt>
}

/**
 * [IMiddleware] implementation that handles [IReduxThunkAction].
 * @param GExt The external argument type.
 * @param executorService The [ExecutorService] that will be used to schedule concurrent work.
 * @param external The external [GExt] argument.
 */
class ThunkMiddleware<GExt> internal constructor(
  private val executorService: ExecutorService,
  private val external: GExt,
) : IMiddleware<Any> {
  companion object {
    private const val DEFAULT_THREAD_POOL_THREAD_COUNT = 5

    /**
     * Create a [ThunkMiddleware].
     * @param GExt The external argument type.
     * @param executorService The [ExecutorService] that will be used to schedule concurrent work.
     * @param external The external [GExt] argument.
     * @return A [ThunkMiddleware] instance.
     */
    fun <GExt> create(executorService: ExecutorService, external: GExt, ): IMiddleware<Any> {
      return ThunkMiddleware(executorService = executorService, external = external)
    }

    /**
     * Create a [ThunkMiddleware] with a default fixed-thread pool [ExecutorService].
     * @param GExt The external argument type.
     * @param threadPoolThreadCount The number of threads to use for the thread pool.
     * @param external The external [GExt] argument.
     * @return A [ThunkMiddleware] instance.
     */
    fun <GExt> create(threadPoolThreadCount: Int, external: GExt): IMiddleware<Any> {
      return create(
        executorService = Executors.newFixedThreadPool(threadPoolThreadCount),
        external = external,
      )
    }

    /**
     * Create a [ThunkMiddleware] with a default thread pool thread count.
     * @param GExt The external argument type.
     * @param external The external [GExt] argument.
     * @return A [ThunkMiddleware] instance.
     */
    fun <GExt> create(external: GExt): IMiddleware<Any> {
      return create(
        external = external,
        threadPoolThreadCount = DEFAULT_THREAD_POOL_THREAD_COUNT
      )
    }
  }

  @Suppress("UNCHECKED_CAST")
  override fun invoke(p1: MiddlewareInput<Any>): DispatchMapper {
    val lock = ReentrantLock()

    return { wrapper ->
      DispatchWrapper.wrap(wrapper, "thunk", ThreadSafeDispatcher(lock) { action ->
        BatchAwaitable(
          executor = { runnable -> executorService.submit(runnable) },
          wrapper.dispatch(action) as IAwaitable<Any>,
          when (action) {
            is IReduxThunkAction<*, *, *> -> {
              val castAction = action as IReduxThunkAction<Any, Any, Any>

              this@ThunkMiddleware.executorService.submit {
                castAction.payload(p1.dispatch, p1.lastState, Unit)
              }.let { FutureAwaitable(it as Future<Any>) }
            }

            is DefaultReduxAction.Deinitialize -> {
              this@ThunkMiddleware.executorService.shutdownNow()
              EmptyAwaitable
            }

            else -> EmptyAwaitable
          } as IAwaitable<Any>
        ).map { it.first() }
      })
    }
  }
}
