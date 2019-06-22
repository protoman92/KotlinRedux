/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.saga.common

import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import org.swiften.redux.core.DefaultReduxAction
import org.swiften.redux.core.DispatchMapper
import org.swiften.redux.core.DispatchWrapper
import org.swiften.redux.core.IAwaitable
import org.swiften.redux.core.IMiddleware
import org.swiften.redux.core.IReduxAction
import org.swiften.redux.core.JustAwaitable
import org.swiften.redux.core.MiddlewareInput
import org.swiften.redux.core.ThreadSafeDispatcher
import kotlin.coroutines.CoroutineContext

/** Created by haipham on 2018/12/22 */
/**
 * [IMiddleware] implementation for [ISagaEffect]. Every time an [IReduxAction] is received, call
 * [ISagaOutput.onAction].
 * @param context The [CoroutineContext] with which to perform asynchronous work on.
 * @param monitor A [SagaMonitor] instance.
 * @param scheduler A [Scheduler] instance.
 * @param effects The [List] of [ISagaEffect] to run.
 */
class SagaMiddleware private constructor (
  private val context: CoroutineContext,
  private val monitor: SagaMonitor,
  private val scheduler: Scheduler,
  private val effects: Collection<ISagaEffect<*>>
) : IMiddleware<Any> {
  companion object {
    /**
     * Create a [SagaMiddleware] with [effects].
     * @param context See [SagaMiddleware.context].
     * @param monitor See [SagaMiddleware.monitor].
     * @param scheduler See [SagaMiddleware.scheduler].
     * @param effects See [SagaMiddleware.effects].
     * @return A [SagaMiddleware] instance.
     */
    internal fun create(
      context: CoroutineContext,
      monitor: SagaMonitor,
      scheduler: Scheduler,
      effects: Collection<ISagaEffect<*>>
    ): SagaMiddleware {
      return SagaMiddleware(context, monitor, scheduler, effects)
    }

    /**
     * Create a [SagaMiddleware] with [effects] and a default [CoroutineContext] so this
     * [SagaMiddleware] does not have to share its [CoroutineContext] with any other user.
     * @param effects See [SagaMiddleware.effects].
     * @return A [SagaMiddleware] instance.
     */
    fun create(
      scheduler: Scheduler = Schedulers.computation(),
      effects: Collection<ISagaEffect<*>>
    ): IMiddleware<Any> {
      return this.create(SupervisorJob(), SagaMonitor(), scheduler, effects)
    }
  }

  internal val composite = CompositeDisposable()

  override fun invoke(p1: MiddlewareInput<Any>): DispatchMapper {
    return { wrapper ->
      val monitor = this@SagaMiddleware.monitor
      val sagaInput = SagaInput(this@SagaMiddleware.context, p1.dispatch, p1.lastState, monitor)
      val outputs = this@SagaMiddleware.effects.map { it(sagaInput) }

      /**
       * We use a [ThreadSafeDispatcher] and [IAwaitable.await] here to ensure the store receives the
       * latest state by the time [ISagaOutput.onAction] happens, so that it is available for state
       * value selection.
       */
      val newWrapper = DispatchWrapper.wrap(wrapper, "saga") { action ->
        val dispatchResult = wrapper.dispatch(action).await()
        monitor.dispatch(action).await()

        /** If [action] is [DefaultReduxAction.Deinitialize], dispose of all [ISagaOutput]. */
        if (action == DefaultReduxAction.Deinitialize) {
          this@SagaMiddleware.composite.dispose()
          this@SagaMiddleware.context.cancel()
        }

        JustAwaitable(dispatchResult)
      }

      outputs.forEach { this@SagaMiddleware.composite.add(it.subscribe({})) }
      newWrapper
    }
  }
}
