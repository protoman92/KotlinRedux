/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.saga

import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import org.swiften.redux.core.DefaultReduxAction
import org.swiften.redux.middleware.ReduxDispatchWrapper
import org.swiften.redux.middleware.ReduxMiddlewareInput
import org.testng.annotations.Test

/** Created by haipham on 2019/01/15 */
class ReduxSagaMiddlewareTest {
  @Test
  fun `Redux saga middleware should work correctly`() {
    // Setup
    val outputs = (0 until 100).map { mock<IReduxSagaOutput<Any>> {
      on { this.onAction } doReturn {}
    } }

    val effects = outputs.map<IReduxSagaOutput<Any>, IReduxSagaEffect<Unit, Any>> { o -> { o } }
    val input = ReduxMiddlewareInput { }
    val dispatchWrapper = ReduxDispatchWrapper("root") { }
    val wrappedDispatch = createSagaMiddlewareProvider(effects).middleware(input)(dispatchWrapper)

    // When
    wrappedDispatch.dispatch(DefaultReduxAction.Dummy)
    wrappedDispatch.dispatch(DefaultReduxAction.Dummy)
    wrappedDispatch.dispatch(DefaultReduxAction.Dummy)
    wrappedDispatch.dispatch(DefaultReduxAction.Deinitialize)

    // Then
    outputs.forEach { verify(it, times(4)).onAction }
    outputs.forEach { verify(it).dispose() }
  }
}
