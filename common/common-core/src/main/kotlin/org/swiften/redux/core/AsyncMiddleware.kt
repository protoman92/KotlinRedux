/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.core

import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlin.coroutines.CoroutineContext

/** Created by haipham on 2019/01/26 */
/**
 * [IMiddleware] implementation that calls [DispatchWrapper.dispatch] on another thread.
 * @param context A [CoroutineContext] instance.
 */
internal class AsyncMiddleware(private val context: CoroutineContext) : IMiddleware<Any> {
  override fun invoke(p1: MiddlewareInput<Any>): DispatchMapper {
    return { wrapper ->
      val context = this@AsyncMiddleware.context

      DispatchWrapper.wrap(wrapper, "async") { action ->
        when (action) {
          is DefaultReduxAction.Deinitialize -> { context.cancel(); EmptyJob }
          else -> CoroutineJob(context) { wrapper.dispatch(action) } }
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
