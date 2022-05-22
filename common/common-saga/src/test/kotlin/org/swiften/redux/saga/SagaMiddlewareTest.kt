/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.saga

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import io.reactivex.disposables.Disposables
import io.reactivex.schedulers.Schedulers
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.swiften.redux.core.BaseMiddlewareTest
import org.swiften.redux.core.DefaultReduxAction
import org.swiften.redux.core.EmptyAwaitable
import org.swiften.redux.core.IActionDispatcher
import org.swiften.redux.core.IAwaitable
import org.swiften.redux.core.IReduxAction
import java.util.concurrent.atomic.AtomicInteger

/** Created by haipham on 2019/01/15 */
class SagaMiddlewareTest : BaseMiddlewareTest() {
  @Test
  fun `Redux saga middleware should work correctly`() {
    // Setup
    val dispatched = AtomicInteger()

    val dispatchers: List<IActionDispatcher> = (0 until 100).map {
      fun(_: IReduxAction): IAwaitable<Any> {
        dispatched.incrementAndGet()
        return EmptyAwaitable
      }
    }

    val outputs = dispatchers.map { d -> mock<ISagaOutput<Any>> {
      on { this.onAction } doReturn d
      on { this.subscribe(any(), any()) } doReturn Disposables.fromAction {}
    } }

    val monitor = SagaMonitor()
    val effects = outputs.map<ISagaOutput<Any>, ISagaEffect<Any>> { o -> { o } }
    val input = this.mockMiddlewareInput(0)
    outputs.forEachIndexed { i, o -> monitor.addOutputDispatcher(i.toLong(), o.onAction) }

    val middleware = SagaMiddleware.create(monitor, Schedulers.computation(), effects)
    val wrappedDispatch = middleware.invoke(input)(this.mockDispatchWrapper()).dispatch

    // When
    wrappedDispatch(DefaultReduxAction.Dummy)
    wrappedDispatch(DefaultReduxAction.Dummy)
    wrappedDispatch(DefaultReduxAction.Dummy)
    wrappedDispatch(DefaultReduxAction.Deinitialize)

    // Then
    assertEquals(dispatched.get(), dispatchers.size * 4)
    assertTrue(middleware.composite.isDisposed)
  }
}
