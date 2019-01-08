/*
 * Copyright (c) haipham 2018. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.core

import org.testng.Assert
import org.testng.annotations.Test

/** Created by haipham on 2018/04/08 */
class ReducerWrapperTest {
  @Test
  fun test_reducingDefaultActions_shouldWork() {
    // Setup
    val reducer = ReduxPreset.ReducerWrapper<Int> { a, _ -> a + 1 }

    var original = 0

    // When & Then
    original = reducer(original, ReduxPreset.DefaultAction.Dummy)
    Assert.assertEquals(original, 0)
    original = reducer(original, ReduxPreset.DefaultAction.ReplaceState(1000))
    Assert.assertEquals(original, 1000)
    original = reducer(original, object : Redux.IAction {})
    Assert.assertEquals(original, 1001)
    original = reducer(original, ReduxPreset.DefaultAction.MapState<Int> { it * 2 })
    Assert.assertEquals(original, 2002)
  }
}
