/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.saga.common

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withTimeoutOrNull
import org.junit.Assert.assertEquals
import org.junit.Test
import org.swiften.redux.core.DefaultReduxAction
import org.swiften.redux.core.IReduxAction
import java.util.Collections.synchronizedList
import java.util.concurrent.atomic.AtomicInteger

/** Created by haipham on 2019/01/07 */
/** Use this test class for common [ISagaEffect] tests */
abstract class CommonSagaEffectTest {
  protected class State

  protected val timeout: Long = 10000
  abstract fun <T> justEffect(value: T): SagaEffect<T>
  abstract fun <T : Any> fromEffect(vararg values: T): SagaEffect<T>

  fun test_takeEffect_shouldTakeCorrectActions(
    createTakeEffect: (
      extractor: (IReduxAction) -> Int?,
      creator: Function1<Int, ISagaEffect<Any>>
    ) -> SagaEffect<Any>,
    actualValues: List<Int>
  ) {
    // Setup
    class TakeAction(val value: Int) : IReduxAction

    val takeOutput = createTakeEffect(
      { when (it) { is TakeAction -> it.value; else -> null } },
      { v -> justEffect(v).mapSuspend { delay(1000); it } }
    ).invoke()

    val finalValues = synchronizedList(arrayListOf<Int>())
    takeOutput.subscribe({ finalValues.add(it as Int) })

    // When
    takeOutput.onAction(TakeAction(0))
    takeOutput.onAction(TakeAction(1))
    takeOutput.onAction(DefaultReduxAction.Dummy)
    takeOutput.onAction(TakeAction(2))
    takeOutput.onAction(DefaultReduxAction.Dummy)
    takeOutput.onAction(TakeAction(3))

    runBlocking {
      withTimeoutOrNull(this@CommonSagaEffectTest.timeout) {
        while (finalValues.sorted() != actualValues.sorted()) { }; Unit
      }

      // Then
      assertEquals(finalValues.sorted(), actualValues.sorted())
    }
  }

  @Test
  @Suppress("UNREACHABLE_CODE")
  fun `Catch error effect should handle errors gracefully`() {
    // Setup
    val error = Exception("Oh no!")
    val finalValues = synchronizedList(arrayListOf<Int>())

    // When
    justEffect(1)
      .map { throw error; 1 }
      .delay(1000)
      .catchError { 100 }
      .invoke()
      .subscribe({ finalValues.add(it) })

    runBlocking {
      withTimeoutOrNull(this@CommonSagaEffectTest.timeout) {
        while (finalValues != arrayListOf(100)) { }; Unit
      }

      // Then
      assertEquals(finalValues, arrayListOf(100))
    }
  }

  @Test
  @Suppress("UNREACHABLE_CODE")
  fun `Async catch error effect should handle errors gracefully`() {
    // Setup
    val error = Exception("Oh no!")
    val finalValues = synchronizedList(arrayListOf<Int>())

    // When
    justEffect(1)
      .map { throw error; 1 }
      .delay(1000)
      .catchAsync { this.async { 100 } }
      .invoke()
      .subscribe({ finalValues.add(it) })

    runBlocking {
      withTimeoutOrNull(this@CommonSagaEffectTest.timeout) {
        while (finalValues != arrayListOf(100)) { }; Unit
      }

      // Then
      assertEquals(finalValues, arrayListOf(100))
    }
  }

  @Test
  @Suppress("UNREACHABLE_CODE")
  fun `Compact map effect should unwrap nullable values`() {
    // Setup
    val finalValues = synchronizedList(arrayListOf<Int>())

    // When
    justEffect(1)
      .map { null }
      .compactMap()
      .invoke()
      .subscribe({ finalValues.add(it) })

    runBlocking {
      delay(1000)

      // Then
      assertEquals(finalValues, arrayListOf<Int>())
    }
  }

  @Test
  fun `Filter effect should filter out unwanted values`() {
    // Setup
    val finalValues = synchronizedList(arrayListOf<Int>())

    // When
    fromEffect(0, 1, 2, 3)
      .filter { it % 2 == 0 }
      .delay(100)
      .cast<Int>()
      .invoke()
      .subscribe({ finalValues.add(it) })

    runBlocking {
      withTimeoutOrNull(this@CommonSagaEffectTest.timeout) {
        while (finalValues != arrayListOf(0, 2)) { }; Unit
      }

      // Then
      assertEquals(finalValues, arrayListOf(0, 2))
    }
  }

  @Test
  fun `Put effect should dispatch action`() {
    // Setup
    data class Action(private val value: Int) : IReduxAction
    val dispatched = synchronizedList(arrayListOf<IReduxAction>())

    justEffect(0)
      .map { it }
      .put { Action(it) }
      .invoke(GlobalScope, {}) { dispatched.add(it) }
      .subscribe({})

    // When
    runBlocking {
      withTimeoutOrNull(this@CommonSagaEffectTest.timeout) {
        while (dispatched != arrayListOf(Action(0))) { }; Unit
      }

      // Then
      assertEquals(dispatched, arrayListOf(Action(0)))
    }
  }

  @Test
  fun `Retry effects should retry asynchronous calls`() {
    // Setup
    val retryCount = 3
    val retries = AtomicInteger(0)

    // When
    justEffect(0)
      .mapSuspend { retries.incrementAndGet(); throw RuntimeException("Oh no!") }
      .retry(retryCount)
      .invoke()
      .subscribe({})

    runBlocking {
      withTimeoutOrNull(this@CommonSagaEffectTest.timeout) {
        while (retries.get() != retryCount + 1) { }; Unit
      }

      // Then
      assertEquals(retries.get(), retryCount + 1)
    }
  }

  @Test
  fun `Then effect should enforce ordering`() {
    // Setup
    val finalOutput = justEffect(1)
      .delay(500)
      .then(justEffect(2).map { it }) { a, b -> "$a$b" }
      .delay(1000)
      .invoke()

    // When && Then
    assertEquals(finalOutput.nextValue(this.timeout), "12")
  }

  @Test
  fun `Timeout effect should error with timeout`() {
    // Setup
    val finalOutput = justEffect(1)
      .mapSuspend { delay(this@CommonSagaEffectTest.timeout); it }
      .timeout(1000)
      .catchSuspend { 100 }
      .invoke()

    // When && Then
    assertEquals(finalOutput.nextValue(this.timeout), 100)
  }
}
