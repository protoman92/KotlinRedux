/*
 * Copyright (c) haipham 2018. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.saga

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.consumeEach
import org.swiften.redux.core.Redux
import org.swiften.redux.core.ReduxPreset
import org.swiften.redux.saga.ReduxSagaEffect.call
import org.swiften.redux.saga.ReduxSagaEffect.takeEveryAction
import org.swiften.redux.saga.ReduxSagaEffect.takeLatestAction
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
  fun afterMethod() { this.coroutineContext.cancel() }

  @ObsoleteCoroutinesApi
  private fun test_takeEffect_shouldTakeCorrectActions(
    createTakeEffect: (
      suspend CoroutineScope.(TakeAction) -> Int?,
      suspend CoroutineScope.(Int) -> ReduxSaga.IEffect<State, Int>
    ) -> ReduxSaga.IEffect<State, Int>,
    actualValues: List<Int>
  ) {
    /// Setup
    val extract: suspend CoroutineScope.(TakeAction) -> Int? = {
      when (it) {is TakeAction.Action1 -> it.value }
    }

    val api = object : ReduxSaga.Output.IMapper<Int, Int> {
      override suspend operator fun invoke(scope: CoroutineScope, value: Int): Int {
        delay(1000); return value
      }
    }

    val block: suspend CoroutineScope.(Int) -> ReduxSaga.IEffect<State, Int> = {
      call(it, api)
    }

    val takeEffect = createTakeEffect(extract, block)
    val takeOutput = takeEffect.invoke(this, State()) { }
    val finalValues = Collections.synchronizedList(arrayListOf<Int>())
    this.launch { takeOutput.channel.consumeEach { finalValues.add(it) } }

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
}
