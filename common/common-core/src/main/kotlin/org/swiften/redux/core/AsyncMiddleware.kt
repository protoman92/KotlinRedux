/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.core

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.async
import kotlinx.coroutines.cancel
import kotlin.coroutines.CoroutineContext

/** Created by haipham on 2019/01/26 */
/**
 * [IMiddleware] implementation that calls [DispatchWrapper.dispatch] on another thread. You should
 * only use one [AsyncMiddleware] in a [IMiddleware] chain to avoid undesirable side effects.
 * @param context A [CoroutineContext] instance.
 */
class AsyncMiddleware private constructor (private val context: CoroutineContext) : IMiddleware<Any> {
  companion object {
    /**
     * Create a [AsyncMiddleware] with [context].
     * @param context See [AsyncMiddleware.context].
     * @return An [AsyncMiddleware] instance.
     */
    internal fun create(context: CoroutineContext): IMiddleware<Any> {
      return AsyncMiddleware(context)
    }

    /**
     * Create a [AsyncMiddleware] with a default [CoroutineContext]. This is made public so that
     * users of this [AsyncMiddleware] cannot share its [CoroutineContext] with other users.
     * @return An [AsyncMiddleware] instance.
     */
    fun create(): IMiddleware<Any> {
      return this.create(Dispatchers.Default + SupervisorJob())
    }
  }

  override fun invoke(p1: MiddlewareInput<Any>): DispatchMapper {
    return { wrapper ->
      val context = this@AsyncMiddleware.context
      val scope = object : CoroutineScope { override val coroutineContext get() = context }

      DispatchWrapper.wrap(wrapper, "async") { action ->
        when (action) {
          is DefaultReduxAction.Deinitialize -> { context.cancel(); EmptyJob }
          else -> CoroutineJob(context, scope.async { wrapper.dispatch(action).await() })
        }
      }
    }
  }
}
