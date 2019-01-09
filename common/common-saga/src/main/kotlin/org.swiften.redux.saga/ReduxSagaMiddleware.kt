/*
 * Copyright (c) haipham 2018. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.saga

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import org.swiften.redux.middleware.IReduxMiddleware
import org.swiften.redux.middleware.IReduxMiddlewareProvider
import org.swiften.redux.middleware.ReduxDispatchWrapper

/** Created by haipham on 2018/12/22 */
/** [IReduxMiddlewareProvider] implementation for Saga */
class ReduxSagaMiddlewareProvider<State>(
  private val effects: List<ReduxSagaEffect<State, Any>>
) : IReduxMiddlewareProvider<State> {
  override val middleware: IReduxMiddleware<State> = { input ->
    { wrapper ->
      val job = SupervisorJob()

      val scope = object : CoroutineScope {
        override val coroutineContext = Dispatchers.Default + job
      }

      val sgi = Input(scope, input.stateGetter, wrapper.dispatch)
      val outputs = this@ReduxSagaMiddlewareProvider.effects.map { it(sgi) }
      outputs.forEach { it.subscribe({}) }

      ReduxDispatchWrapper("${wrapper.id}-saga") { action ->
        wrapper.dispatch(action)
        outputs.forEach { it.onAction(action) }
      }
    }
  }
}
