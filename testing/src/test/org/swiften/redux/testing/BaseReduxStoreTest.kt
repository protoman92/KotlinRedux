/*
 * Copyright (c) haipham 2018. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.testing

import kotlinx.coroutines.*
import org.swiften.redux.core.Redux
import org.testng.Assert
import java.util.*
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

/**
 * Created by haipham on 2018/12/16.
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
        AddOne,
        AddTwo,
        AddThree,
        Minus10,
        Minus20
      )

      fun random(): Action {
        return allActions[random.nextInt(
          allActions.size)]
      }
    }

    override fun toString(): String {
      return this.javaClass.simpleName
    }

    operator fun invoke(value: Int): Int {
      return when (this) {
        is AddOne -> value + 1
        is AddTwo -> value + 2
        is AddThree -> value + 3
        is Minus10 -> value - 10
        is Minus20 -> value - 20
      }
    }
  }

  fun reducer(): Redux.IReducer<Int> {
    return object : Redux.IReducer<Int> {
      override operator fun invoke(previous: Int, action: Redux.IAction): Int {
        return when (action) {
          is Action -> action(previous)
          else -> throw RuntimeException()
        }
      }
    }
  }

  fun dispatchingAction_shouldResultInCorrectState(
    store: Redux.IStore<Int>
  ) {
    var currentState = 0
    val allDispatches = arrayListOf<Deferred<Unit>>()
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
      allDispatches.addAll(actions.map { a ->
        GlobalScope.async(start = CoroutineStart.LAZY) { store.dispatch(a) }
      })
    }

    for (dispatch in allDispatches) { dispatch.start() }

    GlobalScope.launch {
      while (store.lastState() != currentState) {}
      latch.countDown()
    }

    latch.await(20, TimeUnit.SECONDS)

    /// Then
    Assert.assertEquals(store.lastState(), currentState)
  }
}
