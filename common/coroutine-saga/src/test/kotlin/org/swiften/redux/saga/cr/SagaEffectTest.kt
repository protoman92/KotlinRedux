/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.saga.cr

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withTimeoutOrNull
import org.swiften.redux.saga.CommonSagaEffectTest
import org.swiften.redux.saga.cr.ReduxSagaHelper.from
import org.swiften.redux.saga.cr.ReduxSagaHelper.just
import org.swiften.redux.saga.cr.ReduxSagaHelper.takeEveryAction
import org.swiften.redux.saga.cr.ReduxSagaHelper.takeLatestAction
import org.swiften.redux.saga.invoke
import org.swiften.redux.saga.map
import org.swiften.redux.saga.mapAsync
import org.swiften.redux.saga.mapSuspend
import org.swiften.redux.saga.then
import org.testng.Assert
import org.testng.annotations.Test

/** Created by haipham on 2018/12/23 */
class SagaEffectTest : CommonSagaEffectTest() {
  override fun <T> justEffect(value: T) = just<State, T>(value)

  @ExperimentalCoroutinesApi
  override fun <T : Any> fromEffect(vararg values: T) = from<State, T>(this.produce {
    values.forEach { this.send(it) }
  })

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
  fun `Select effect should extract some value from a state`() {
    // Setup
    val sourceOutput1 = just<State, Int>(1)
      .select({ 2 }, { a, b -> a + b })
      .invoke(this, State()) { }

    val sourceOutput2 = just<State, Int>(2)
      .select { 4 }
      .invoke(this, State()) { }

    // When && Then
    Assert.assertEquals(sourceOutput1.nextValue(this.timeout), 3)
    Assert.assertEquals(sourceOutput2.nextValue(this.timeout), 4)
  }

  @Test
  @ExperimentalCoroutinesApi
  fun `Disposing outputs should terminate channels`() {
    // Setup
    val sourceOutput = just<State, Int>(1)
      .mapSuspend { delay(500); it }
      .then(just(2)) { a, b -> a + b }
      .mapSuspend { delay(500); it }
      .invoke(this, State()) { } as ReduxSaga.Output<*>

    runBlocking {
      // When
      sourceOutput.dispose()

      // Then
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
    // Setup
    val sourceOutput = just<State, Int>(0)
      .map { it }
      .mapAsync { this.async { delay(1000); it } }
      .then(just<State, Int>(2).map { it }) { a, b -> a + b }
      .invoke(this, State()) { } as ReduxSaga.Output<*>

    sourceOutput.subscribe({})

    runBlocking {
      // When
      withTimeoutOrNull(this@SagaEffectTest.timeout) {
        while (!sourceOutput.channel.isClosedForReceive) { }; Unit
      }

      // Then
      Assert.assertTrue(sourceOutput.channel.isClosedForReceive)
    }
  }
}
