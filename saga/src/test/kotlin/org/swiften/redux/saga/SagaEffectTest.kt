/*
 * Copyright (c) haipham 2018. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.saga

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import org.swiften.redux.core.Redux
import org.swiften.redux.core.ReduxPreset
import org.swiften.redux.saga.ReduxSagaHelper.just
import org.swiften.redux.saga.ReduxSagaHelper.takeEveryAction
import org.swiften.redux.saga.ReduxSagaHelper.takeLatestAction
import org.testng.Assert
import org.testng.annotations.AfterMethod
import org.testng.annotations.BeforeMethod
import org.testng.annotations.Test
import java.util.*

/**
 * Created by haipham on 2018/12/23.
 */
class SagaEffectTest : CoroutineScope {
  class State

  sealed class TakeAction : Redux.IAction {
    data class Action1(val value: Int): TakeAction()
  }

  override val coroutineContext get() = this.job

  private lateinit var job: Job
  private val timeout: Long = 10000

  @BeforeMethod
  fun beforeMethod() { this.job = SupervisorJob() }

  @AfterMethod
  fun afterMethod() {
    this.coroutineContext.cancelChildren()
    runBlocking { delay(1000) }
  }

  @ObsoleteCoroutinesApi
  private fun test_takeEffect_shouldTakeCorrectActions(
    createTakeEffect: (
      extract: Function1<TakeAction, Int?>,
      block: Function1<Int, ReduxSagaEffect<State, Any>>
    ) -> ReduxSagaEffect<State, Any>,
    actualValues: List<Int>
  ) {
    /// Setup
    val takeEffect = createTakeEffect(
      { when (it) {is TakeAction.Action1 -> it.value } },
      { just<State, Int>(it).map { delay(1000); it } })

    val takeOutput = takeEffect.invoke(this, State()) { }
    val finalValues = Collections.synchronizedList(arrayListOf<Int>())
    takeOutput.subscribe({ finalValues.add(it as Int) })

    /// When
    takeOutput.onAction(TakeAction.Action1(0))
    takeOutput.onAction(TakeAction.Action1(1))
    takeOutput.onAction(ReduxPreset.DefaultAction.Dummy)
    takeOutput.onAction(TakeAction.Action1(2))
    takeOutput.onAction(ReduxPreset.DefaultAction.Dummy)
    takeOutput.onAction(TakeAction.Action1(3))

    runBlocking {
      withTimeoutOrNull(this@SagaEffectTest.timeout) {
        while (finalValues.sorted() != actualValues.sorted()) { }; Unit
      }

      /// Then
      Assert.assertEquals(finalValues.sorted(), actualValues.sorted())
    }
  }

  @Test
  @ObsoleteCoroutinesApi
  fun `Take every effect should take all actions`() {
    test_takeEffect_shouldTakeCorrectActions(
      { a, b -> takeEveryAction(a, b) }, arrayListOf(0, 1, 2, 3))
  }

  @Test
  @ObsoleteCoroutinesApi
  fun `Take latest effect should take latest actions`() {
    test_takeEffect_shouldTakeCorrectActions(
      { a, b -> takeLatestAction(a, b) }, arrayListOf(3))
  }

  @Test
  @ObsoleteCoroutinesApi
  @Suppress("UNREACHABLE_CODE")
  fun `Catch error effect should handle errors gracefully`() {
    /// Setup
    val error = Exception("Oh no!")

    val finalOutput = just<State, Int>(1)
      .map { delay(1000); throw error; 1 }
      .catchError { 100 }
      .invoke(this, State()) { }

    val finalValues = Collections.synchronizedList(arrayListOf<Int>())

    /// When
    finalOutput.subscribe({ finalValues.add(it) })

    runBlocking {
      withTimeoutOrNull(this@SagaEffectTest.timeout) {
        while (finalValues != arrayListOf(100)) { }; Unit
      }

      /// Then
      Assert.assertEquals(finalValues, arrayListOf(100))
    }
  }

  @Test
  fun `Then effect should enforce ordering`() {
    /// Setup
    val finalOutput = just<State, Int>(1)
      .map { delay(500); it }
      .then(just<State, Int>(2).map { delay(1000); it }) { a, b -> "$a$b" }
      .invoke(this, State()) { }

    /// When && Then
    Assert.assertEquals(finalOutput.nextValue(this.timeout), "12")
  }

  @Test
  @ExperimentalCoroutinesApi
  fun `Disposing outputs should terminate channels`() {
    /// Setup
    val source = just<State, Int>(1)
      .map { delay(500); it }
      .then(just(2)) { a, b -> a + b }
      .map { delay(500); it }

    val sourceOutput = source.invoke(this, State()) { }

    runBlocking {
      /// When
      sourceOutput.dispose()

      /// Then
      var parent: ReduxSaga.Output<*>? = sourceOutput

      while (parent != null) {
        val channel = parent.channel as Channel<*>
        Assert.assertTrue(channel.isClosedForSend)
        Assert.assertTrue(channel.isClosedForReceive)
        parent = parent.source
      }
    }
  }

  @Test
  @ExperimentalCoroutinesApi
  fun `One-off channels should terminate on single item emission`() {
    /// Setup
    val sourceOutput = just<State, Int>(0)
      .map { it }
      .then(just<State, Int>(2).map { it }) { a, b -> a + b }
      .invoke(this, State()) { }

    sourceOutput.subscribe({})

    runBlocking {
      /// When
      withTimeoutOrNull(this@SagaEffectTest.timeout) {
        while (!sourceOutput.channel.isClosedForReceive) { }; Unit
      }

      /// Then
      Assert.assertTrue(sourceOutput.channel.isClosedForReceive)
    }
  }
}
