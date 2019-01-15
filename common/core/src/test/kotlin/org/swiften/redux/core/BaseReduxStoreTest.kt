/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.core

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withTimeoutOrNull
import org.testng.Assert
import org.testng.annotations.Test
import java.util.Random
import java.util.concurrent.atomic.AtomicInteger

/** Created by haipham on 2018/12/16 */
/** Use this test class to test [IReduxStore] implementations */
@Suppress("FunctionName")
abstract class BaseReduxStoreTest : CoroutineScope {
  sealed class Action : IReduxAction {
    object AddOne : Action()
    object AddTwo : Action()
    object AddThree : Action()
    object Minus10 : Action()
    object Minus20 : Action()

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

  override val coroutineContext = Dispatchers.Default

  fun reducer(): IReduxReducer<Int> = { s, a ->
    when (a) { is Action -> a(s); else -> throw RuntimeException() }
  }

  abstract fun createStore(): IReduxStore<Int>

  @Test
  fun test_dispatchingAction_shouldResultInCorrectState() {
    val store = this.createStore()
    var currentState = 0
    val allDispatches = arrayListOf<Deferred<Unit>>()

    for (i in 0 until 100) {
      // Setup
      val actions = arrayListOf<Action>()

      for (j in 0 until 50) {
        val action = Action.random()
        actions.add(action)
        currentState = action(currentState)
      }

      // When
      // Dispatch actions on multiple coroutines to check thread safety.
      allDispatches.addAll(actions.map { a ->
        GlobalScope.async(start = CoroutineStart.LAZY) { store.dispatch(a) }
      })
    }

    for (dispatch in allDispatches) { dispatch.start() }

    runBlocking(this.coroutineContext) {
      withTimeoutOrNull(100000) {
        while (store.lastState() != currentState) { }; 1
      }

      // Then
      Assert.assertEquals(store.lastState(), currentState)
    }
  }

  @Test
  fun test_deinitializingStore_shouldRemoveAllSubscriptions() {
    // Setup
    val store = this.createStore()
    val receivedUpdates = AtomicInteger()
    store.subscribe("1") { receivedUpdates.incrementAndGet() }
    store.subscribe("2") { receivedUpdates.incrementAndGet() }
    store.subscribe("3") { receivedUpdates.incrementAndGet() }

    /// When
    runBlocking {
      store.dispatch(DefaultReduxAction.Dummy)
      delay(1000)
      store.deinitialize()
      (0 until 100).forEach { store.dispatch(DefaultReduxAction.Dummy) }
      delay(1000)

      /// Then
      Assert.assertEquals(receivedUpdates.get(), 3)
    }
  }
}
