/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.thunk

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import org.swiften.redux.core.DefaultReduxAction
import org.swiften.redux.core.DispatchMapper
import org.swiften.redux.core.DispatchWrapper
import org.swiften.redux.core.EmptyJob
import org.swiften.redux.core.IActionDispatcher
import org.swiften.redux.core.IMiddleware
import org.swiften.redux.core.IReduxAction
import org.swiften.redux.core.IStateGetter
import org.swiften.redux.core.JustJob
import org.swiften.redux.core.MiddlewareInput
import org.swiften.redux.core.ThreadSafeDispatcher
import java.util.concurrent.locks.ReentrantLock
import kotlin.coroutines.CoroutineContext

/** Created by haipham on 2019/02/05 */
/**
 * A thunk function is one that can be resolved asynchronously. It is used mainly as
 * [IReduxThunkAction.payload].
 * @param GState The global state type.
 * @param GExt The external argument type.
 */
typealias ThunkFunction<GState, GExt> =
  suspend CoroutineScope.(IActionDispatcher, IStateGetter<GState>, GExt) -> Unit

/**
 * A thunk action represents an [IReduxAction] whose [payload] is a function that can be resolved
 * asynchronously. We can use these functions to perform simple async logic, such as fetching data
 * from a remote API and then dispatch the result to populate the [GState] implementation with said
 * data.
 *
 * Beware that there is no way to properly type check these [IReduxAction], so it is up to the
 * developer to ensure [GState], [GExt] and [Param] do not fail casting checks with
 * [ClassCastException].
 * @param GState The global state type.
 * @param GExt The external argument type.
 * @param Param The parameters with which to construct this [IReduxThunkAction]. This is mainly
 * used for equality check.
 */
interface IReduxThunkAction<GState, GExt, Param> : IReduxAction {
  val params: Param
  val payload: ThunkFunction<GState, GExt>
}

/**
 * [IMiddleware] implementation that handles [IReduxThunkAction].
 * @param GExt The external argument type.
 * @param external The external [GExt] argument.
 * @param context The [CoroutineContext] with which to perform async work.
 */
class ThunkMiddleware<GExt>(
  private val external: GExt,
  private val context: CoroutineContext
) : IMiddleware<Any> where GExt : Any {
  companion object {
    /**
     * Create a [ThunkMiddleware] with [context].
     * @param GExt The external argument type.
     * @param external The external [GExt] argument.
     * @param context See [ThunkMiddleware.context].
     * @return A [ThunkMiddleware] instance.
     */
    internal fun <GExt> create(external: GExt, context: CoroutineContext): IMiddleware<Any> where GExt : Any {
      return ThunkMiddleware(external, context)
    }

    /**
     * Create a [ThunkMiddleware] with a default [CoroutineContext]. This is made public so that users
     * of this [ThunkMiddleware] cannot share its [CoroutineContext] with other users.
     * @param GExt The external argument type.
     * @param external The external [GExt] argument.
     * @return A [ThunkMiddleware] instance.
     */
    fun <GExt> create(external: GExt): IMiddleware<Any> where GExt : Any {
      return this.create(external, SupervisorJob())
    }
  }

  @Suppress("UNCHECKED_CAST")
  override fun invoke(p1: MiddlewareInput<Any>): DispatchMapper {
    val lock = ReentrantLock()
    val context = this@ThunkMiddleware.context
    val scope = object : CoroutineScope { override val coroutineContext get() = context }

    return { wrapper ->
      DispatchWrapper.wrap(wrapper, "thunk", ThreadSafeDispatcher(lock) { action ->
        val dispatchResult = wrapper.dispatch(action).await()

        when (action) {
          is IReduxThunkAction<*, *, *> -> scope.launch {
            val castAction = action as IReduxThunkAction<Any, Any, Any>
            castAction.payload(scope, p1.dispatch, p1.lastState, Unit)
          }

          is DefaultReduxAction.Deinitialize -> { context.cancel(); EmptyJob }
        }

        JustJob(dispatchResult)
      })
    }
  }
}
