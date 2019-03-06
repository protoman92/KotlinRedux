/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.core

import org.junit.Assert.assertEquals
import org.junit.Test

/** Created by haipham on 2019/01/27 */
class LoggingMiddlewareTest : BaseMiddlewareTest() {
  @Test
  fun `Dispatching actions should call logger`() {
    // Setup
    data class Action(val value: Int) : IReduxAction
    var logged = 0
    val store = ThreadSafeStore(0) { a, _ -> a }

    val middleware = LoggingMiddleware.create<Int> { _, a ->
      logged += 1; println("Logged: ${logged}, current action $a")
    }

    val wrappedDispatch = applyMiddlewares(middleware)(store).dispatch

    // When
    wrappedDispatch(Action(0))
    wrappedDispatch(Action(1))
    wrappedDispatch(DefaultReduxAction.Deinitialize)
    wrappedDispatch(Action(2))
    wrappedDispatch(Action(3))
    wrappedDispatch(Action(4))

    // Then
    // Value is 3 due to last value being relayed on subscription.
    assertEquals(logged, 3)
  }
}
