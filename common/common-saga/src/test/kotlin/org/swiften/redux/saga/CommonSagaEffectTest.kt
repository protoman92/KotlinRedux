/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.saga

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withTimeoutOrNull
import org.swiften.redux.core.DefaultAction
import org.swiften.redux.core.IReduxAction
import org.testng.Assert
import org.testng.annotations.AfterMethod
import org.testng.annotations.BeforeMethod
import org.testng.annotations.Test
import java.util.Collections

/** Created by haipham on 2019/01/07 */
/** Use this test class for common [ReduxSagaEffect] tests */
abstract class CommonSagaEffectTest : CoroutineScope {
  class State

  sealed class TakeAction : IReduxAction {
    data class Action1(val value: Int) : TakeAction()
  }

  override val coroutineContext get() = this.job

  private lateinit var job: Job
  protected val timeout: Long = 10000

  @BeforeMethod
  fun beforeMethod() { this.job = SupervisorJob() }

  @AfterMethod
  fun afterMethod() {
    this.coroutineContext.cancelChildren()
    runBlocking { delay(1000) }
  }

  abstract fun <T> justEffect(value: T): ReduxSagaEffect<State, T>
  abstract fun <T : Any> fromEffect(vararg values: T): ReduxSagaEffect<State, T>

  @ObsoleteCoroutinesApi
  fun test_takeEffect_shouldTakeCorrectActions(
    createTakeEffect: (
      extract: Function1<TakeAction, Int?>,
      block: Function1<Int, ReduxSagaEffect<State, Any>>
    ) -> ReduxSagaEffect<State, Any>,
    actualValues: List<Int>
  ) {
    // Setup
    val takeOutput = createTakeEffect(
      { when (it) { is TakeAction.Action1 -> it.value } },
      { justEffect(it).mapSuspend { delay(1000); it } }
    ).invoke(this, State()) { }

    val finalValues = Collections.synchronizedList(arrayListOf<Int>())
    takeOutput.subscribe({ finalValues.add(it as Int) })

    // When
    takeOutput.onAction(TakeAction.Action1(0))
    takeOutput.onAction(TakeAction.Action1(1))
    takeOutput.onAction(DefaultAction.Dummy)
    takeOutput.onAction(TakeAction.Action1(2))
    takeOutput.onAction(DefaultAction.Dummy)
    takeOutput.onAction(TakeAction.Action1(3))

    runBlocking {
      withTimeoutOrNull(this@CommonSagaEffectTest.timeout) {
        while (finalValues.sorted() != actualValues.sorted()) { }; Unit
      }

      // Then
      Assert.assertEquals(finalValues.sorted(), actualValues.sorted())
    }
  }

  @Test
  @Suppress("UNREACHABLE_CODE")
  fun `Catch error effect should handle errors gracefully`() {
    // Setup
    val error = Exception("Oh no!")

    val finalOutput = justEffect(1)
      .map { throw error; 1 }
      .delay(1000)
      .catchError { 100 }
      .invoke(this, State()) { }

    val finalValues = Collections.synchronizedList(arrayListOf<Int>())

    // When
    finalOutput.subscribe({ finalValues.add(it) })

    runBlocking {
      withTimeoutOrNull(this@CommonSagaEffectTest.timeout) {
        while (finalValues != arrayListOf(100)) { }; Unit
      }

      // Then
      Assert.assertEquals(finalValues, arrayListOf(100))
    }
  }

  @Test
  @ExperimentalCoroutinesApi
  fun `Filter effect should filter out unwanted values`() {
    // Setup
    val finalOutput = fromEffect(0, 1, 2, 3)
      .filter { it % 2 == 0 }
      .delay(100)
      .cast<State, Int, Int>()
      .invoke(this, State()) { }

    val finalValues = Collections.synchronizedList(arrayListOf<Int>())

    // When
    finalOutput.subscribe({ finalValues.add(it) })

    runBlocking {
      withTimeoutOrNull(this@CommonSagaEffectTest.timeout) {
        while (finalValues != arrayListOf(0, 2)) { }; Unit
      }

      // Then
      Assert.assertEquals(finalValues, arrayListOf(0, 2))
    }
  }

  @Test
  fun `Put effect should dispatch action`() {
    // Setup
    data class Action(private val value: Int) : IReduxAction
    val dispatched = arrayListOf<IReduxAction>()

    val finalOutput = justEffect(0)
      .map { it }
      .put { Action(it) }
      .invoke(this, State()) { dispatched.add(it) }

    // When
    finalOutput.subscribe({})

    runBlocking {
      withTimeoutOrNull(this@CommonSagaEffectTest.timeout) {
        while (dispatched != arrayListOf(Action(0))) { }; Unit
      }

      // Then
      Assert.assertEquals(dispatched, arrayListOf(Action(0)))
    }
  }

  @Test
  fun `Then effect should enforce ordering`() {
    // Setup
    val finalOutput = justEffect(1)
      .delay(500)
      .then(justEffect(2).map { it }) { a, b -> "$a$b" }
      .delay(1000)
      .invoke(this, State()) { }

    // When && Then
    Assert.assertEquals(finalOutput.nextValue(this.timeout), "12")
  }
}
