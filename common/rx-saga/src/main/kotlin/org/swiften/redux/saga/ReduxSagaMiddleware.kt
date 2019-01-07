/*
 * Copyright (c) haipham 2018. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.saga

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import org.swiften.redux.middleware.ReduxMiddleware
import org.swiften.redux.middleware.ReduxMiddlewareCreator

/** Created by haipham on 2018/12/22 */
/** Top-level namespace for Redux Saga middleware */
object ReduxSagaMiddleware {
  /** [ReduxMiddleware.IProvider] implementation for Saga */
  class Provider<State>(
    private val effects: List<ReduxSagaEffect<State, Any>>
  ): ReduxMiddleware.IProvider<State> {
    override val middleware: ReduxMiddlewareCreator<State> = { input ->
      { wrapper ->
        val job = SupervisorJob()

        val scope = object : CoroutineScope {
          override val coroutineContext = Dispatchers.Default + job
        }

        val sgi = CommonSaga.Input(scope, input.stateGetter, wrapper.dispatch)
        val outputs = this@Provider.effects.map { it(sgi) }
        outputs.forEach { it.subscribe({}) }

        ReduxMiddleware.DispatchWrapper("${wrapper.id}-saga") { action ->
          wrapper.dispatch(action)
          outputs.forEach { it.onAction(action) }
        }
      }
    }
  }
}
