package org.swiften.redux

import org.testng.Assert

/**
 * Created by haipham on 2018-12-16.
 */
/**
 * Use this test class to test [Redux.IStore] implementations.
 */
@Suppress("FunctionName")
open class BaseStoreTest {
  sealed class Action: Redux.IAction {
    object AddOne : Action()
    object AddTwo : Action()
    object AddThree : Action()
    object Double : Action()
    object Halve : Action()

    companion object {
      fun random(): Action {
        val actions = arrayListOf(
          Action.AddOne,
          Action.AddTwo,
          Action.AddThree,
          Action.Double,
          Action.Halve
        )

        return actions.shuffled().take(1)[0]
      }
    }

    operator fun invoke(value: Int): Int {
      return when (this) {
        is Action.AddOne -> value + 1
        is Action.AddTwo -> value + 2
        is Action.AddThree -> value + 3
        is Action.Double -> value * 2
        is Action.Halve -> value / 2
      }
    }
  }

  fun reducer(): Redux.IReducer<Int> {
    return object : Redux.IReducer<Int> {
      override operator fun invoke(previous: Int, action: Redux.IAction): Int {
        return when {
          action is Action -> action(previous)
          else -> throw RuntimeException()
        }
      }
    }
  }

  fun dispatchingAction_shouldResultInCorrectState(
    store: Redux.IStore<Int>
  ) {
    val actionCount = 500
    var currentState = 0

    for (i in 1..1000) {
      /// Setup
      val actions = arrayListOf<Action>()

      for (j in 1..actionCount) {
        val action = Action.random()
        actions.add(action)
        currentState = action(currentState)
      }

      /// When
      store.dispatch(actions)
    }

    /// Then
    Assert.assertEquals(store.lastState(), currentState)
  }
}