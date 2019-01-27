/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.async

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import org.swiften.redux.core.DefaultReduxAction
import org.swiften.redux.core.DispatchMapper
import org.swiften.redux.core.DispatchWrapper
import org.swiften.redux.core.IMiddleware
import org.swiften.redux.core.MiddlewareInput
import kotlin.coroutines.CoroutineContext

/** Created by haipham on 2019/01/26 */
/** [IMiddleware] implementation that calls [DispatchWrapper.dispatch] on another thread */
internal class AsyncMiddleware(private val context: CoroutineContext) : IMiddleware<Any> {
  @ExperimentalCoroutinesApi
  override fun invoke(p1: MiddlewareInput<Any>): DispatchMapper {
    return { wrapper ->
      require(wrapper.id == DispatchWrapper.ROOT_WRAPPER) {
        "This middleware must be applied at the end of the middleware chain (right before the " +
          "actual store dispatch) so that there are no unintended side effects on the previous " +
          "middlewares."
      }

      DispatchWrapper("${wrapper.id}-async") { action ->
        when (action) {
          is DefaultReduxAction.Deinitialize -> this@AsyncMiddleware.context.cancel()
          else -> GlobalScope.launch(this@AsyncMiddleware.context) { wrapper.dispatch(action) }
        }
      }
    }
  }
}

/** Create a [AsyncMiddleware] with [context] */
fun createAsyncMiddleware(context: CoroutineContext = SupervisorJob()): IMiddleware<Any> =
  AsyncMiddleware(context)
