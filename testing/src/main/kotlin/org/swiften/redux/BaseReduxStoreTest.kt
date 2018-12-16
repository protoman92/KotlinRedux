package org.swiften.redux

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.testng.Assert
import java.util.*
import java.util.concurrent.CountDownLatch
import java.util.concurrent.Semaphore
import java.util.concurrent.TimeUnit

/**
 * Created by haipham on 2018-12-16.
 */
/**
 * Use this test class to test [Redux.IStore] implementations.
 */
@Suppress("FunctionName")
open class BaseReduxStoreTest {
  sealed class Action: Redux.IAction {
    object AddOne : Action()
    object AddTwo : Action()
    object AddThree : Action()
    object Minus10 : Action()
    object Minus20: Action()

    companion object {
      private val random = Random()

      private val allActions = arrayListOf(
        Action.AddOne,
        Action.AddTwo,
        Action.AddThree,
        Action.Minus10,
        Action.Minus20
      )

      fun random(): Action {
        return this.allActions[this.random.nextInt(this.allActions.size)]
      }
    }

    override fun toString(): String {
      return this.javaClass.simpleName
    }

    operator fun invoke(value: Int): Int {
      return when (this) {
        is Action.AddOne -> value + 1
        is Action.AddTwo -> value + 2
        is Action.AddThree -> value + 3
        is Action.Minus10 -> value - 10
        is Action.Minus20 -> value - 20
      }
    }
  }

  val timeout: Long = 100

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
    var currentState = 0
    val latch = CountDownLatch(1)

    for (i in 0 until 1000) {
      /// Setup
      val actions = arrayListOf<Action>()

      for (j in 0 until 500) {
        val action = Action.random()
        actions.add(action)
        currentState = action(currentState)
      }

      /// When
      // Dispatch actions on multiple coroutines to check thread safety.
      actions.forEach { a -> GlobalScope.launch { store.dispatch(a) } }
    }

    GlobalScope.launch {
      while (store.lastState() != currentState) { }
      latch.countDown()
    }

    latch.await(this.timeout, TimeUnit.SECONDS)

    /// Then
    Assert.assertEquals(store.lastState(), currentState)
  }
}