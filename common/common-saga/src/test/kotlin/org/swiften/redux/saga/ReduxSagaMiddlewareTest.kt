/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.saga

import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import org.junit.Test
import org.swiften.redux.core.DefaultReduxAction
import org.swiften.redux.middleware.ReduxDispatchWrapper
import org.swiften.redux.middleware.ReduxMiddlewareInput

/** Created by haipham on 2019/01/15 */
class ReduxSagaMiddlewareTest {
  @Test
  fun `Redux saga middleware should work correctly`() {
    // Setup
    val outputs = (0 until 100).map { mock<IReduxSagaOutput<Any>> {
      on { this.onAction } doReturn {}
    } }

    val effects = outputs.map<IReduxSagaOutput<Any>, IReduxSagaEffect<Any>> { o -> { o } }

    val wrappedDispatch = createSagaMiddlewareProvider<Unit>(effects)
      .middleware(ReduxMiddlewareInput { })(ReduxDispatchWrapper("root") { })
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
