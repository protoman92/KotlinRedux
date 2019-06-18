/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.saga.common

import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import org.swiften.redux.core.DefaultReduxAction
import org.swiften.redux.core.DispatchMapper
import org.swiften.redux.core.DispatchWrapper
import org.swiften.redux.core.IAsyncJob
import org.swiften.redux.core.IMiddleware
import org.swiften.redux.core.IReduxAction
import org.swiften.redux.core.JustJob
import org.swiften.redux.core.MiddlewareInput
import org.swiften.redux.core.ThreadSafeDispatcher
import java.util.concurrent.locks.ReentrantLock
import kotlin.coroutines.CoroutineContext

/** Created by haipham on 2018/12/22 */
/**
 * [IMiddleware] implementation for [ISagaEffect]. Every time an [IReduxAction] is received, call
 * [ISagaOutput.onAction].
 * @param effects The [List] of [ISagaEffect] to run.
 * @param context The [CoroutineContext] with which to perform asynchronous work on.
 */
class SagaMiddleware private constructor (
  private val context: CoroutineContext,
  private val monitor: SagaMonitor,
  private val effects: Collection<ISagaEffect<*>>
) : IMiddleware<Any> {
  companion object {
    /**
     * Create a [SagaMiddleware] with [effects].
     * @param context See [SagaMiddleware.context].
     * @param monitor A [SagaMonitor] instance.
     * @param effects See [SagaMiddleware.effects].
     * @return A [SagaMiddleware] instance.
     */
    internal fun create(
      context: CoroutineContext,
      monitor: SagaMonitor,
      effects: Collection<ISagaEffect<*>>
    ): IMiddleware<Any> {
      return SagaMiddleware(context, monitor, effects)
    }

    /**
     * Create a [SagaMiddleware] with [effects] and a default [CoroutineContext] so this
     * [SagaMiddleware] does not have to share its [CoroutineContext] with any other user.
     * @param effects See [SagaMiddleware.effects].
     * @return A [SagaMiddleware] instance.
     */
    fun create(effects: Collection<ISagaEffect<*>>): IMiddleware<Any> {
      return this.create(SupervisorJob(), SagaMonitor(), effects)
    }
  }

  override fun invoke(p1: MiddlewareInput<Any>): DispatchMapper {
    return { wrapper ->
      val lock = ReentrantLock()
      val monitor = this@SagaMiddleware.monitor
      val sagaInput = SagaInput(this@SagaMiddleware.context, monitor, p1.lastState, p1.dispatch)
      val outputs = this@SagaMiddleware.effects.map { it(sagaInput) }
      outputs.forEach { it.subscribe({}) }

      /**
       * We use a [ThreadSafeDispatcher] and [IAsyncJob.await] here to ensure the store receives the
       * latest state by the time [ISagaOutput.onAction] happens, so that it is available for state
       * value selection.
       */
      DispatchWrapper.wrap(wrapper, "saga", ThreadSafeDispatcher(lock) { action ->
        val dispatchResult = wrapper.dispatch(action).await()
        monitor.dispatch(action).await()

        /** If [action] is [DefaultReduxAction.Deinitialize], dispose of all [ISagaOutput]. */
        if (action == DefaultReduxAction.Deinitialize) {
          outputs.forEach { it.dispose() }
          this@SagaMiddleware.context.cancel()
        }

        JustJob(dispatchResult)
      })
    }
  }
}
