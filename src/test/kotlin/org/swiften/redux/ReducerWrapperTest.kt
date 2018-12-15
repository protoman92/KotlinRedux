package org.swiften.redux

import org.testng.Assert
import org.testng.annotations.Test

/**
 * Created by haipham on 8/4/18.
 */

class ReducerWrapperTest {
  @Test
  fun test_reducingDefaultActions_shouldWork() {
    /// Setup
    val reducer = ReduxPreset.ReducerWrapper(object : Redux.IReducer<Int> {
      override fun invoke(previous: Int, action: Redux.IAction): Int {
        return previous + 1
      }
    })

    var original = 0

    /// When & Then
    original = reducer(original, ReduxPreset.DefaultAction.Dummy)
    Assert.assertEquals(original, 0)
    original = reducer(original, ReduxPreset.DefaultAction.ReplaceState(1000))
    Assert.assertEquals(original, 1000)
    original = reducer(original, object : Redux.IAction {})
    Assert.assertEquals(original, 1001)
    println(ReduxPreset.DefaultAction.ReplaceState(1000))
  }
}