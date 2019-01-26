/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.saga.common

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancelChildren
import org.swiften.redux.core.DefaultReduxAction
import org.swiften.redux.core.IReduxAction
import org.swiften.redux.core.DispatchMapper
import org.swiften.redux.core.DispatchWrapper
import org.swiften.redux.core.IMiddleware
import org.swiften.redux.core.MiddlewareInput

/** Created by haipham on 2018/12/22 */
/**
 * [IMiddleware] implementation for [ISagaEffect]. Every time an [IReduxAction] is received, call
 * [ISagaOutput.onAction].
 */
internal class SagaMiddleware(private val effects: Collection<ISagaEffect<*>>) :
  IMiddleware<Any> {
  override fun invoke(p1: MiddlewareInput<Any>): DispatchMapper {
    return { wrapper ->
      val job = SupervisorJob()

      val scope = object : CoroutineScope {
        override val coroutineContext = Dispatchers.Default + job
      }

      val sagaInput = SagaInput(scope, p1.stateGetter, wrapper.dispatch)
      val outputs = this@SagaMiddleware.effects.map { it(sagaInput) }
      outputs.forEach { it.subscribe({}) }

      DispatchWrapper("${wrapper.id}-saga") { action ->
        wrapper.dispatch(action)
        outputs.forEach { it.onAction(action) }

        /** If [action] is [DefaultReduxAction.Deinitialize], dispose of all [ISagaOutput] */
        if (action == DefaultReduxAction.Deinitialize) {
          outputs.forEach { it.dispose() }
          job.cancelChildren()
        }
      }
    }
  }
}

/** Create a [SagaMiddleware] with [effects] */
fun createSagaMiddleware(effects: Collection<ISagaEffect<*>>): IMiddleware<Any> =
  SagaMiddleware(effects)
