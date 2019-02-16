/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.saga.common

import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import org.junit.Test
import org.swiften.redux.core.BaseMiddlewareTest
import org.swiften.redux.core.DefaultReduxAction
import org.swiften.redux.core.EmptyJob

/** Created by haipham on 2019/01/15 */
class SagaMiddlewareTest : BaseMiddlewareTest() {
  @Test
  fun `Redux saga middleware should work correctly`() {
    // Setup
    val outputs = (0 until 100).map { mock<ISagaOutput<Any>> {
      on { this.onAction } doReturn { EmptyJob }
    } }

    val effects = outputs.map<ISagaOutput<Any>, ISagaEffect<Any>> { o -> { o } }
    val input = this.mockMiddlewareInput(0)

    val wrappedDispatch = createSagaMiddleware(effects)
      .invoke(input)(this.mockDispatchWrapper())
      .dispatch

    // When
    wrappedDispatch(DefaultReduxAction.Dummy)
    wrappedDispatch(DefaultReduxAction.Dummy)
    wrappedDispatch(DefaultReduxAction.Dummy)
    wrappedDispatch(DefaultReduxAction.Deinitialize)

    // Then
    outputs.forEach { verify(it, times(4)).onAction }
    outputs.forEach { verify(it).dispose() }
  }
}
