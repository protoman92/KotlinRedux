/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.saga.common

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import org.swiften.redux.core.DefaultReduxAction
import org.swiften.redux.core.DispatchMapper
import org.swiften.redux.core.DispatchWrapper
import org.swiften.redux.core.IMiddleware
import org.swiften.redux.core.IReduxAction
import org.swiften.redux.core.MiddlewareInput
import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.withLock
import kotlin.coroutines.CoroutineContext

/** Created by haipham on 2018/12/22 */
/**
 * [IMiddleware] implementation for [ISagaEffect]. Every time an [IReduxAction] is received, call
 * [ISagaOutput.onAction].
 * @param effects The [List] of [ISagaEffect] to run.
 * @param context The [CoroutineContext] with which to perform asynchronous work on.
 */
internal class SagaMiddleware(
  private val effects: Collection<ISagaEffect<*>>,
  private val context: CoroutineContext
) : IMiddleware<Any> {
  override fun invoke(p1: MiddlewareInput<Any>): DispatchMapper {
    return { wrapper ->
      val lock = ReentrantLock()

      val scope = object : CoroutineScope {
        override val coroutineContext = Dispatchers.Default + this@SagaMiddleware.context
      }

      val sagaInput = SagaInput(scope, p1.lastState, p1.dispatch)
      val outputs = this@SagaMiddleware.effects.map { it(sagaInput) }
      outputs.forEach { it.subscribe({}) }

      /**
       * We use a [ReentrantLock] here to ensure the store receives the latest state by the time
       * [ISagaOutput.onAction] happens, so that it is available for state value selection.
       */
      DispatchWrapper("${wrapper.id}-saga") { action ->
        lock.withLock {
          wrapper.dispatch(action)
          outputs.forEach { it.onAction(action) }

          /** If [action] is [DefaultReduxAction.Deinitialize], dispose of all [ISagaOutput]. */
          if (action == DefaultReduxAction.Deinitialize) {
            outputs.forEach { it.dispose() }
            scope.coroutineContext.cancel()
          }
        }
      }
    }
  }
}

/**
 * Create a [SagaMiddleware] with [effects].
 * @param effects See [SagaMiddleware.effects].
 * @param context See [SagaMiddleware.context].
 * @return A [SagaMiddleware] instance.
 */
internal fun createSagaMiddleware(
  effects: Collection<ISagaEffect<*>>,
  context: CoroutineContext = SupervisorJob()
): IMiddleware<Any> {
  return SagaMiddleware(effects, context)
}

/**
 * Create a [SagaMiddleware] with [effects] and a default [CoroutineContext] so this
 * [SagaMiddleware] does not have to share its [CoroutineContext] with any other user.
 * @param effects See [SagaMiddleware.effects].
 * @return A [SagaMiddleware] instance.
 */
fun createSagaMiddleware(effects: Collection<ISagaEffect<*>>): IMiddleware<Any> {
  return createSagaMiddleware(effects, SupervisorJob())
}
