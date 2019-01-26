/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.async

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import org.swiften.redux.core.DefaultReduxAction
import org.swiften.redux.middleware.DispatchMapper
import org.swiften.redux.middleware.DispatchWrapper
import org.swiften.redux.middleware.IMiddleware
import org.swiften.redux.middleware.MiddlewareInput
import kotlin.coroutines.CoroutineContext

/** Created by haipham on 2019/01/26 */
/** [IMiddleware] implementation that calls [DispatchWrapper.dispatch] on another thread */
internal class AsyncMiddleware(private val context: CoroutineContext) : IMiddleware<Any> {
  override fun invoke(p1: MiddlewareInput<Any>): DispatchMapper {
    return { wrapper ->
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
