/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.saga.common

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
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
import org.swiften.redux.core.applyMiddlewares
import org.swiften.redux.saga.common.CommonEffects.putInStore
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
      creator: (Int) -> SagaEffect<Any>
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

  fun test_takeStateEffect_shouldSelectCorrectState(
    createTakeEffect: (creator: (String) -> SagaEffect<String>) -> SagaEffect<String>,
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
  fun `Put effect should dispatch action`() {
    // Setup
    data class Action(private val value: Int) : IReduxAction
    val monitor = SagaMonitor()
    val dispatched = synchronizedList(arrayListOf<IReduxAction>())

    // When
    putInStore(Action(0)).invoke(SagaInput(monitor) { dispatched.add(it); EmptyJob }).subscribe({})

    runBlocking {
      withTimeoutOrNull(this@CommonSagaEffectTest.timeout) {
        while (dispatched != arrayListOf(Action(0)) && this.isActive) { delay(500) }; Unit
      }

      // Then
      assertEquals(dispatched, arrayListOf(Action(0)))
    }
  }
}
