/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.saga

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancelChildren
import org.swiften.redux.core.DefaultReduxAction
import org.swiften.redux.core.IReduxAction
import org.swiften.redux.middleware.IReduxMiddleware
import org.swiften.redux.middleware.ReduxDispatchMapper
import org.swiften.redux.middleware.ReduxDispatchWrapper
import org.swiften.redux.middleware.ReduxMiddlewareInput

/** Created by haipham on 2018/12/22 */
/**
 * [IReduxMiddleware] implementation for [IReduxSagaEffect]. Every time an [IReduxAction] is
 * received, call [IReduxSagaOutput.onAction].
 */
internal class ReduxSagaMiddleware<GlobalState>(
  private val effects: Collection<IReduxSagaEffect<*>>
) : IReduxMiddleware<GlobalState> {
  override fun invoke(p1: ReduxMiddlewareInput<GlobalState>): ReduxDispatchMapper {
    return { wrapper ->
      val job = SupervisorJob()

      val scope = object : CoroutineScope {
        override val coroutineContext = Dispatchers.Default + job
      }

      val sagaInput = Input(scope, p1.stateGetter, wrapper.dispatch)
      val outputs = this@ReduxSagaMiddleware.effects.map { it(sagaInput) }
      outputs.forEach { it.subscribe({}) }

      ReduxDispatchWrapper("${wrapper.id}-saga") { action ->
        wrapper.dispatch(action)
        outputs.forEach { it.onAction(action) }

        /** If [action] is [DefaultReduxAction.Deinitialize], dispose of all [IReduxSagaOutput] */
        if (action == DefaultReduxAction.Deinitialize) {
          outputs.forEach { it.dispose() }
          job.cancelChildren()
        }
      }
    }
  }
}

/** Create a [ReduxSagaMiddleware] with [effects] */
fun <GlobalState> createSagaMiddleware(effects: Collection<IReduxSagaEffect<*>>):
  IReduxMiddleware<GlobalState> = ReduxSagaMiddleware(effects)
