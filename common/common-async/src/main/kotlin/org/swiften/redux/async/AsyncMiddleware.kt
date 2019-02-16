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
import org.swiften.redux.core.DispatchMapper
import org.swiften.redux.core.DispatchWrapper
import org.swiften.redux.core.IMiddleware
import org.swiften.redux.core.MiddlewareInput
import kotlin.coroutines.CoroutineContext

/** Created by haipham on 2019/01/26 */
/**
 * [IMiddleware] implementation that calls [DispatchWrapper.dispatch] on another thread.
 * @param context A [CoroutineContext] instance.
 */
internal class AsyncMiddleware(private val context: CoroutineContext) : IMiddleware<Any> {
  override fun invoke(p1: MiddlewareInput<Any>): DispatchMapper {
    return { wrapper ->
      println(wrapper)
      DispatchWrapper.wrap(wrapper, "async") { action ->
        when (action) {
          is DefaultReduxAction.Deinitialize -> this@AsyncMiddleware.context.cancel()
          else -> GlobalScope.launch(this@AsyncMiddleware.context) { wrapper.dispatch(action) }
        }
      }
    }
  }
}

/**
 * Create a [AsyncMiddleware] with [context].
 * @param context See [AsyncMiddleware.context].
 * @return An [AsyncMiddleware] instance.
 */
internal fun createAsyncMiddleware(context: CoroutineContext = SupervisorJob()): IMiddleware<Any> {
  return AsyncMiddleware(context)
}

/**
 * Create a [AsyncMiddleware] with a default [CoroutineContext]. This is made public so that users
 * of this [AsyncMiddleware] cannot share its [CoroutineContext] with other users.
 * @return An [AsyncMiddleware] instance.
 */
fun createAsyncMiddleware(): IMiddleware<Any> {
  return createAsyncMiddleware(SupervisorJob())
}
