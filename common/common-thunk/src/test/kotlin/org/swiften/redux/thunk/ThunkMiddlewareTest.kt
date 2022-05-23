/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.thunk

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Assert.fail
import org.junit.Test
import org.swiften.redux.core.BaseMiddlewareTest
import org.swiften.redux.core.DefaultReduxAction
import org.swiften.redux.core.EmptyAwaitable
import org.swiften.redux.core.EnhancedReduxStore
import org.swiften.redux.core.IActionDispatcher
import org.swiften.redux.core.IReduxAction
import org.swiften.redux.core.NoopActionDispatcher
import org.swiften.redux.core.ThreadSafeStore
import org.swiften.redux.core.combineMiddlewares
import java.util.Collections.synchronizedList
import java.util.concurrent.Executors
import java.util.concurrent.atomic.AtomicInteger

/** Created by haipham on 2019/01/26 */
class ThunkMiddlewareTest : BaseMiddlewareTest() {
  private interface ISubDependency1
  private interface IDependency : ISubDependency1

  @Test
  @Suppress("UNCHECKED_CAST", "USELESS_IS_CHECK")
  @Throws(ClassCastException::class)
  fun `Thunk actions should be correctly cast`() {
    // Setup
    class ThunkAction :
      IReduxThunkAction<Int, ISubDependency1, Pair<Int, Int>> {
      override val params = 1 to 2
      override val payload: ThunkFunction<Int, ISubDependency1> = { _, _, _ -> }
    }

    // When
    val thunkAction = ThunkAction()
    val successCast = thunkAction as IReduxThunkAction<Any, IDependency, Any>
    val failureCast = thunkAction as IReduxThunkAction<Any, Int, Any>

    // Then
    assertTrue(thunkAction is IReduxThunkAction<*, *, *>)
    assertTrue(successCast.payload(NoopActionDispatcher, { 0 }, object : IDependency {}) is Unit)

    try {
      failureCast.payload(NoopActionDispatcher, {}, 0)
      fail("Should not have completed")
    } catch (e: Throwable) { }
  }

  @Test
  fun `Dispatching thunk actions should resolve them`() {
    // Setup
    data class Action(val value: Int) : IReduxAction

    class ThunkAction(override val params: Int) : IReduxThunkAction<Int, Unit, Int> {
      override val payload: ThunkFunction<Int, Unit> = { dispatch, _, _ ->
        runBlocking {
          val result = withContext(Dispatchers.IO) { delay(1000); 2 }
          dispatch(Action(result))
        }
      }

      override fun hashCode() = this.params.hashCode()

      override fun equals(other: Any?) = when (other) {
        is ThunkAction -> other.params == this.params
        else -> false
      }
    }

    val dispatched = synchronizedList(arrayListOf<IReduxAction>())
    val dispatch: IActionDispatcher = { dispatched.add(it); EmptyAwaitable }
    val baseStore = ThreadSafeStore(0) { s, _ -> s }
    val enhancedStore = EnhancedReduxStore(baseStore, dispatch)
    val wrappedDispatch = combineMiddlewares<Int>(ThunkMiddleware.create(Unit))(enhancedStore)

    // When
    wrappedDispatch(ThunkAction(1))

    // Then
    runBlocking {
      delay(1500)

      // Then
      assertEquals(dispatched, arrayListOf(ThunkAction(1), Action(2)))
    }
  }

  @Test
  fun `Sending deinitialize action should deinitialize executor service`() {
    // Setup
    val dispatched = AtomicInteger(0)
    val executorService = Executors.newFixedThreadPool(1)
    val middleware = ThunkMiddleware.create(executorService = executorService, external = Unit)
    val dispatch: IActionDispatcher = { dispatched.incrementAndGet(); EmptyAwaitable }
    val dispatchWrapper = this.mockDispatchWrapper(dispatch)
    val input = mockMiddlewareInput(0)
    val wrappedDispatch = middleware(input)(dispatchWrapper).dispatch

    runBlocking {
      // When
      wrappedDispatch(DefaultReduxAction.Dummy)
      wrappedDispatch(DefaultReduxAction.Dummy)
      delay(500)
      wrappedDispatch(DefaultReduxAction.Deinitialize)
      wrappedDispatch(DefaultReduxAction.Dummy)
      wrappedDispatch(DefaultReduxAction.Dummy)
      delay(500)

      // Then
      assertEquals(dispatched.get(), 5)
      assertTrue(executorService.isShutdown)
    }
  }
}
