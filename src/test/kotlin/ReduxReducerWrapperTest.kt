import common.DefaultReduxAction
import common.ReduxActionType
import common.ReduxReducer
import common.ReduxReducerWrapper
import org.testng.Assert
import org.testng.annotations.Test

/**
 * Created by haipham on 8/4/18.
 */

class ReduxReducerWrapperTest {
  @Test
  fun test_reducingDefaultActions_shouldWork() {
    /// Setup
    val reducer = ReduxReducerWrapper(object : ReduxReducer<Int> {
      override fun invoke(previous: Int, action: ReduxActionType): Int {
        return previous + 1
      }
    })

    var original = 0

    /// When & Then
    original = reducer(original, DefaultReduxAction.Dummy)
    Assert.assertEquals(original, 0)
    original = reducer(original, DefaultReduxAction.ReplaceState(1000))
    Assert.assertEquals(original, 1000)
    original = reducer(original, object : ReduxActionType {})
    Assert.assertEquals(original, 1001)
    println(DefaultReduxAction.ReplaceState(1000))
  }
}