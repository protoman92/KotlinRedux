/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.core

import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import java.util.concurrent.atomic.AtomicInteger

/** Created by haipham on 2019/01/26 */
class AsyncMiddlewareTest : BaseMiddlewareTest() {
  @Test
  fun `Sending deinitialize action should deinitialize context`() {
    // Setup
    val dispatched = AtomicInteger(0)
    val job = Job()
    val middleware = AsyncMiddleware.create(job)
    val dispatch: IActionDispatcher = { dispatched.incrementAndGet(); EmptyJob }
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
      assertEquals(dispatched.get(), 2)
      assertTrue(job.isCancelled)
    }
  }
}
