/*
 * Copyright (c) haipham 2018. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.core

import org.junit.Assert.assertEquals
import org.junit.Test

/** Created by haipham on 2018/04/08 */
class ReducerWrapperTest {
  @Test
  fun test_reducingDefaultActions_shouldWork() {
    // Setup
    val reducer = ReduxReducerWrapper<Int> { a, _ -> a + 1 }

    var original = 0

    // When & Then
    original = reducer(original, DefaultReduxAction.Dummy)
    assertEquals(original, 0)
    original = reducer(original, DefaultReduxAction.ReplaceState(1000))
    assertEquals(original, 1000)
    original = reducer(original, object : IReduxAction {})
    assertEquals(original, 1001)
    original = reducer(original, DefaultReduxAction.MapState<Int> { it * 2 })
    assertEquals(original, 2002)
  }
}
