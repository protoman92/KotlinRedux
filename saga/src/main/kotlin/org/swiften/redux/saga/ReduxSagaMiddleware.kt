/*
 * Copyright (c) haipham 2018. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.saga

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import org.swiften.redux.core.Redux
import org.swiften.redux.core.ReduxDispatcher
import org.swiften.redux.middleware.ReduxMiddleware
import org.swiften.redux.middleware.ReduxMiddlewareCreator

/**
 * Created by haipham on 2018/12/22.
 */
private typealias DispatchWrapper = ReduxMiddleware.DispatchWrapper

/** Top-level namespace for Redux Saga middleware */
object ReduxSagaMiddleware {
  /** [ReduxMiddleware.IProvider] implementation for Saga */
  class Provider<State>(
    private val effects: List<ReduxSaga.IEffect<State, Any>>
  ): ReduxMiddleware.IProvider<State> {
    override val middleware: ReduxMiddlewareCreator<State> = { input ->
      { wrapper ->
        val job = SupervisorJob()

        val scope = object : CoroutineScope {
          override val coroutineContext = Dispatchers.Default + job
        }

        val sgi = ReduxSaga.Input(scope, input.stateGetter, wrapper.dispatch)
        val outputs = this@Provider.effects.map { it(sgi) }

        DispatchWrapper("${wrapper.id}-saga", object : ReduxDispatcher {
          override fun invoke(action: Redux.IAction) {
            wrapper.dispatch(action)
            outputs.forEach { it.onAction(action) }
          }
        })
      }
    }
  }
}
