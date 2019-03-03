/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.core

import org.junit.Assert.assertEquals
import org.junit.Test

/** Created by viethai.pham on 2019/03/03 */
class BatchDispatchMiddlewareTest : BaseMiddlewareTest() {
  @Test
  fun  `Dispatching batch action should dispatch all child actions`() {
    // Setup
    data class Action(val value: Int) : IReduxAction
    val iteration = 10000
    val dispatched = arrayListOf<IReduxAction>()
    val input = this.mockMiddlewareInput({}) { dispatched.add(it); EmptyJob }
    val wrappedDispatch = BatchDispatchMiddleware.create()(input)(this.mockDispatchWrapper())

    // When
    val batchedActions = (0 until iteration).map { Action(it) }
    wrappedDispatch.dispatch(BatchAction(*batchedActions.toTypedArray()))

    // Then
    assertEquals(dispatched, batchedActions)
  }
}
