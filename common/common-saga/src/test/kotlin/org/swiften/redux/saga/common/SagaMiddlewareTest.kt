/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.saga.common

import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import org.junit.Assert.assertEquals
import org.junit.Test
import org.swiften.redux.core.BaseMiddlewareTest
import org.swiften.redux.core.DefaultReduxAction
import org.swiften.redux.core.EmptyJob
import org.swiften.redux.core.IActionDispatcher
import org.swiften.redux.core.IAsyncJob
import org.swiften.redux.core.IReduxAction
import java.util.concurrent.atomic.AtomicInteger
import kotlin.coroutines.EmptyCoroutineContext

/** Created by haipham on 2019/01/15 */
class SagaMiddlewareTest : BaseMiddlewareTest() {
  @Test
  fun `Redux saga middleware should work correctly`() {
    // Setup
    val dispatched = AtomicInteger()

    val dispatchers: List<IActionDispatcher> = (0 until 100).map {
      fun(_: IReduxAction): IAsyncJob<Any> {
        dispatched.incrementAndGet()
        return EmptyJob
      }
    }

    val outputs = dispatchers.map { d -> mock<ISagaOutput<Any>> {
      on { this.onAction } doReturn d
    } }

    val monitor = SagaMonitor()
    val effects = outputs.map<ISagaOutput<Any>, ISagaEffect<Any>> { o -> { o } }
    val input = this.mockMiddlewareInput(0)
    outputs.forEachIndexed { i, o -> monitor.addOutputDispatcher(i.toLong(), o.onAction) }

    val wrappedDispatch = SagaMiddleware.create(EmptyCoroutineContext, monitor, effects)
      .invoke(input)(this.mockDispatchWrapper())
      .dispatch

    // When
    wrappedDispatch(DefaultReduxAction.Dummy)
    wrappedDispatch(DefaultReduxAction.Dummy)
    wrappedDispatch(DefaultReduxAction.Dummy)
    wrappedDispatch(DefaultReduxAction.Deinitialize)

    // Then
    assertEquals(dispatched.get(), dispatchers.size * 4)
    outputs.forEach { verify(it).dispose() }
  }
}
