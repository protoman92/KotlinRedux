/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.saga.common

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withTimeoutOrNull
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.swiften.redux.core.AsyncMiddleware
import org.swiften.redux.core.DefaultReduxAction
import org.swiften.redux.core.EmptyJob
import org.swiften.redux.core.FinalStore
import org.swiften.redux.core.IReduxAction
import org.swiften.redux.core.IReduxActionWithKey
import org.swiften.redux.core.applyMiddlewares
import java.util.Collections.synchronizedList

/** Created by haipham on 2019/01/07 */
abstract class OverridableCommonSagaEffectTest {
  protected class State

  protected val timeout: Long = 10000
  protected val iteration = 1000
  abstract fun <T> justEffect(value: T): SagaEffect<T> where T : Any
  abstract fun <T> fromEffect(vararg values: T): SagaEffect<T> where T : Any

  fun test_takeEffect_shouldTakeCorrectActions(
    createTakeEffect: (
      extractor: (IReduxAction) -> Int?,
      creator: (Int) -> ISagaEffect<Any>
    ) -> SagaEffect<Any>,
    verifyValues: (Collection<Int>) -> Boolean
  ) {
    // Setup
    class Action(val value: Int) : IReduxAction
    val monitor = SagaMonitor()
    val finalValues = synchronizedList(arrayListOf<Int>())

    createTakeEffect(
      { when (it) { is Action -> it.value; else -> null } },
      { v -> justEffect(v).mapSuspend { delay(1000); it } }
    )
      .invoke(SagaInput(monitor))
      .subscribe({ finalValues.add(it as Int) })

    // When
    (0 until this.iteration).forEach { i ->
      GlobalScope.launch(Dispatchers.IO) { monitor.dispatch(Action(i)) }
      GlobalScope.launch(Dispatchers.IO) { monitor.dispatch(DefaultReduxAction.Dummy) }
    }

    runBlocking {
      withTimeoutOrNull(this@OverridableCommonSagaEffectTest.timeout) {
        while (!verifyValues(finalValues.sorted()) && this.isActive) { delay(500) }; Unit
      }

      // Then
      assertTrue(verifyValues(finalValues.sorted()))
    }
  }

  fun test_takeEffectWithKeys_shouldTakeCorrectActions(
    createTakeEffect: (
      actionKeys: Set<String>,
      creator: (IReduxActionWithKey) -> ISagaEffect<Any>
    ) -> SagaEffect<Any>
  ) {
    // Setup
    abstract class BaseAction(val value: Int)

    class Action1(value: Int) : BaseAction(value), IReduxActionWithKey {
      override val key: String get() = "1"
    }

    class Action2(value: Int) : BaseAction(value), IReduxActionWithKey {
      override val key: String get() = "2"
    }

    class Action3(value: Int) : BaseAction(value), IReduxActionWithKey {
      override val key: String get() = "3"
    }

    val monitor = SagaMonitor()
    val keys = setOf("1", "2")
    val finalValues = synchronizedList(arrayListOf<Int>())
    val allValues = 0 until this.iteration
    val actualValues = allValues.filter { it % 3 != 0 }

    createTakeEffect(keys) { v -> justEffect((v as BaseAction).value) }
      .invoke(SagaInput(monitor))
      .subscribe({ finalValues.add(it as Int) })

    // When
    actualValues.forEach {
      when {
        it % 3 == 0 -> monitor.dispatch(Action3(it))
        it % 2 == 0 -> monitor.dispatch(Action2(it))
        else -> monitor.dispatch(Action1(it))
      }
    }

    runBlocking {
      withTimeoutOrNull(this@OverridableCommonSagaEffectTest.timeout) {
        while (finalValues.sorted() != actualValues.sorted() && this.isActive) { delay(500) }; Unit
      }

      // Then
      assertEquals(finalValues.sorted(), actualValues.sorted())
    }
  }

  fun test_takeStateEffect_shouldSelectCorrectState(
    createTakeEffect: (creator: (String) -> ISagaEffect<String>) -> TakeStateEffect<String, String>,
    verifyValues: (Collection<String>) -> Boolean
  ) {
    // Setup
    data class Action(val value: Int) : IReduxAction
    val defaultState = "0"
    val finalValues = synchronizedList(arrayListOf<String>())

    val takeEffect = createTakeEffect { v ->
      justEffect(v).delayUpstreamValue(500).doOnValue { finalValues.add(it) }
    }

    val store = applyMiddlewares<String>(
      AsyncMiddleware.create(),
      SagaMiddleware.create(arrayListOf(takeEffect))
    )(FinalStore(defaultState) { s, _ -> "${s.toInt() + 1}" })

    // When
    (0 until this.iteration).forEach { i ->
      GlobalScope.launch(Dispatchers.IO) { store.dispatch(Action(i)) }
    }

    runBlocking {
      withTimeoutOrNull(this@OverridableCommonSagaEffectTest.timeout) {
        while (!verifyValues(finalValues.sorted()) && this.isActive) { delay(500) }; Unit
      }

      store.dispatch(DefaultReduxAction.Deinitialize)

      // Then
      verifyValues(finalValues.sorted())
    }
  }
}

/** Use this test class for common [ISagaEffect] tests */
abstract class CommonSagaEffectTest : OverridableCommonSagaEffectTest() {
  @Test
  @Suppress("UNREACHABLE_CODE")
  fun `Catch error effect should handle errors gracefully`() {
    // Setup
    val monitor = SagaMonitor()
    val error = Exception("Oh no!")
    val finalValues = synchronizedList(arrayListOf<Int>())

    // When
    justEffect(1)
      .map { throw error; 1 }
      .delayUpstreamValue(1000)
      .catchError { 100 }
      .invoke(SagaInput(monitor))
      .subscribe({ finalValues.add(it) })

    runBlocking {
      withTimeoutOrNull(this@CommonSagaEffectTest.timeout) {
        while (finalValues != arrayListOf(100) && this.isActive) { delay(500) }; Unit
      }

      // Then
      assertEquals(finalValues, arrayListOf(100))
    }
  }

  @Test
  @Suppress("UNREACHABLE_CODE")
  fun `Async catch error effect should handle errors gracefully`() {
    // Setup
    val monitor = SagaMonitor()
    val error = Exception("Oh no!")
    val finalValues = synchronizedList(arrayListOf<Int>())

    // When
    justEffect(1)
      .map { throw error; 1 }
      .delayUpstreamValue(1000)
      .catchAsync { this.async { 100 } }
      .invoke(SagaInput(monitor))
      .subscribe({ finalValues.add(it) })

    runBlocking {
      withTimeoutOrNull(this@CommonSagaEffectTest.timeout) {
        while (finalValues != arrayListOf(100) && this.isActive) { delay(500) }; Unit
      }

      // Then
      assertEquals(finalValues, arrayListOf(100))
    }
  }

  @Test
  @Suppress("UNREACHABLE_CODE")
  fun `Compact map effect should unwrap nullable values`() {
    // Setup
    val monitor = SagaMonitor()
    val finalValues = synchronizedList(arrayListOf<Int>())

    // When
    justEffect(1)
      .mapIgnoringNull { null }
      .invoke(SagaInput(monitor))
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
    val monitor = SagaMonitor()
    val finalValues = synchronizedList(arrayListOf<Int>())

    // When
    fromEffect(0, 1, 2, 3)
      .filter { it % 2 == 0 }
      .delayUpstreamValue(100)
      .castValue<Int>()
      .invoke(SagaInput(monitor))
      .subscribe({ finalValues.add(it) })

    runBlocking {
      withTimeoutOrNull(this@CommonSagaEffectTest.timeout) {
        while (finalValues != arrayListOf(0, 2) && this.isActive) { delay(500) }; Unit
      }

      // Then
      assertEquals(finalValues, arrayListOf(0, 2))
    }
  }

  @Test
  fun `If empty effect should emit specified values when upstream is empty`() {
    // Setup
    val monitor = SagaMonitor()
    val finalValues = synchronizedList(arrayListOf<Int>())
    val defaultValue = 1000

    // When
    fromEffect(0, 1, 2, 3)
      .filter { false }
      .ifEmptyThenReturn(defaultValue)
      .invoke(SagaInput(monitor))
      .subscribe({ finalValues.add(it) })

    runBlocking {
      withTimeoutOrNull(this@CommonSagaEffectTest.timeout) {
        while (finalValues != arrayListOf(defaultValue) && this.isActive) { delay(500) }; Unit
      }

      // Then
      assertEquals(finalValues, arrayListOf(defaultValue))
    }
  }

  @Test
  fun `Put effect should dispatch action`() {
    // Setup
    data class Action(private val value: Int) : IReduxAction
    val monitor = SagaMonitor()
    val dispatched = synchronizedList(arrayListOf<IReduxAction>())

    justEffect(0)
      .map { it }
      .putInStore { Action(it) }
      .invoke(SagaInput(monitor) { dispatched.add(it); EmptyJob })
      .subscribe({})

    // When
    runBlocking {
      withTimeoutOrNull(this@CommonSagaEffectTest.timeout) {
        while (dispatched != arrayListOf(Action(0)) && this.isActive) { delay(500) }; Unit
      }

      // Then
      assertEquals(dispatched, arrayListOf(Action(0)))
    }
  }

  @Test
  fun `Then effect should enforce ordering`() {
    // Setup
    val monitor = SagaMonitor()
    val finalValues = synchronizedList(arrayListOf<String>())

    // When
    justEffect(1)
      .delayUpstreamValue(500)
      .thenSwitchTo(justEffect(2).map { it }) { a, b -> "$a$b" }
      .delayUpstreamValue(1000)
      .thenMightAsWell(justEffect("This should be ignored"))
      .invoke(SagaInput(monitor))
      .subscribe({ finalValues.add(it) })

    runBlocking {
      withTimeoutOrNull(this@CommonSagaEffectTest.timeout) {
        while (finalValues != arrayListOf("12") && this.isActive) { delay(500) }; Unit
      }

      // Then
      assertEquals(finalValues, arrayListOf("12"))
    }
  }

  @Test
  fun `Force-then effect should enforce ordering when source is empty or erroneous`() {
    // Setup
    val monitor = SagaMonitor()
    val finalValues = synchronizedList(arrayListOf<Int>())
    val error = "Error!"

    // When
    justEffect(1)
      .map { throw Exception(error) }
      .thenNoMatterWhat(justEffect(2))
      .invoke(SagaInput(monitor))
      .subscribe({ finalValues.add(it) })

    justEffect(1)
      .filter { it % 2 == 0 }
      .map { throw Exception(error) }
      .thenNoMatterWhat(justEffect(3))
      .invoke(SagaInput(monitor))
      .subscribe({ finalValues.add(it) })

    runBlocking {
      withTimeoutOrNull(this@CommonSagaEffectTest.timeout) {
        while (finalValues != arrayListOf(2, 3) && this.isActive) { delay(500) }; Unit
      }

      // Then
      assertEquals(finalValues, arrayListOf(2, 3))
    }
  }

  @Test
  fun `Timeout effect should error with timeout`() {
    // Setup
    val monitor = SagaMonitor()

    val finalOutput = justEffect(1)
      .mapSuspend { delay(this@CommonSagaEffectTest.timeout); it }
      .timeoutUntilFailure(1000)
      .catchSuspend { 100 }
      .invoke(SagaInput(monitor))

    // When && Then
    assertEquals(finalOutput.awaitFor(this.timeout), 100)
  }
}
