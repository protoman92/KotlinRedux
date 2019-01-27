/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.core

import org.junit.Assert
import org.junit.Test

/** Created by haipham on 2019/01/27 */
class LoggingMiddlewareTest {
  @Test
  fun `Dispatching actions should call logger`() {
    // Setup
    var logged = 0
    val store = ThreadSafeStore(0) { a, _ -> a }
    val middleware = createLoggingMiddleware<Int> { _, _ -> logged += 1 }
    val wrappedDispatch = applyMiddlewares(middleware)(store).dispatch

    // When
    wrappedDispatch(DefaultReduxAction.Dummy)
    wrappedDispatch(DefaultReduxAction.Dummy)
    wrappedDispatch(DefaultReduxAction.Deinitialize)
    wrappedDispatch(DefaultReduxAction.Dummy)
    wrappedDispatch(DefaultReduxAction.Dummy)
    wrappedDispatch(DefaultReduxAction.Dummy)

    // Then
    // Value is 3 due to last value being relayed on subscription.
    Assert.assertEquals(logged, 3)
  }
}
