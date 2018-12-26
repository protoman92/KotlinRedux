/*
 * Copyright (c) haipham 2018. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.saga

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.consumeEach
import org.swiften.redux.core.Redux
import org.swiften.redux.core.ReduxPreset
import org.swiften.redux.saga.ReduxSagaHelper.just
import org.swiften.redux.saga.ReduxSagaHelper.takeEveryAction
import org.swiften.redux.saga.ReduxSagaHelper.takeLatestAction
import org.testng.Assert
import org.testng.annotations.AfterMethod
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

  override val coroutineContext get() = Dispatchers.IO

  private val timeout: Long = 100000

  @AfterMethod
  fun afterMethod() { this.coroutineContext.cancelChildren() }

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
      { just<State, Int>(it).call { delay(1000); it } })

    val takeOutput = takeEffect.invoke(this, State()) { }
    val finalValues = Collections.synchronizedList(arrayListOf<Int>())
    this.launch { takeOutput.channel.consumeEach { finalValues.add(it as Int) } }

    /// When
    takeOutput.onAction(TakeAction.Action1(0))
    takeOutput.onAction(TakeAction.Action1(1))
    takeOutput.onAction(ReduxPreset.DefaultAction.Dummy)
    takeOutput.onAction(TakeAction.Action1(2))
    takeOutput.onAction(ReduxPreset.DefaultAction.Dummy)
    takeOutput.onAction(TakeAction.Action1(3))

    runBlocking {
      withTimeoutOrNull(this@SagaEffectTest.timeout) {
        while (finalValues.sorted() != actualValues.sorted()) { delay(1000) }; 1
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
      .call { delay(1000); throw error; 1 }
      .catchError { 100 }
      .invoke(this, State()) { }

    val finalValues = Collections.synchronizedList(arrayListOf<Int>())

    /// When
    this.launch { finalOutput.channel.consumeEach { finalValues.add(it) } }

    runBlocking {
      delay(1500)

      /// Then
      Assert.assertEquals(finalValues, arrayListOf(100))
    }
  }

  @Test
  fun `Then effect should enforce ordering`() {
    /// Setup
    val finalOutput = just<State, Int>(1)
      .call { delay(500); it }
      .then(just<State, Int>(2).call { delay(1000); it }) { a, b -> "$a$b" }
      .invoke(this, State()) { }

    /// When && Then
    Assert.assertEquals(finalOutput.nextValue(2000), "12")
  }
}
