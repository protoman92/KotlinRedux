/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.async

import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test
import org.swiften.redux.core.DefaultReduxAction
import org.swiften.redux.core.IActionDispatcher
import org.swiften.redux.core.DispatchWrapper
import org.swiften.redux.core.MiddlewareInput
import java.util.concurrent.atomic.AtomicInteger

/** Created by haipham on 2019/01/26 */
class AsyncMiddlewareTest {
  @Test
  fun `Sending deinitialize action should deinitialize context`() {
    // Setup
    val dispatched = AtomicInteger(0)
    val job = Job()
    val middleware = createAsyncMiddleware(job)
    val dispatch: IActionDispatcher = { dispatched.incrementAndGet() }
    val dispatchWrapper = DispatchWrapper("", dispatch)
    val wrappedDispatch = middleware(MiddlewareInput { 0 })(dispatchWrapper).dispatch

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
      Assert.assertEquals(dispatched.get(), 2)
      Assert.assertTrue(job.isCancelled)
    }
  }
}
