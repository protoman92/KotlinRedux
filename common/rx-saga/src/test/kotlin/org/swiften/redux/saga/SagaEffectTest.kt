/*
 * Copyright (c) haipham 2018. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.saga

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.rx2.rxFlowable
import kotlinx.coroutines.withTimeoutOrNull
import org.swiften.redux.core.Redux
import org.swiften.redux.saga.ReduxSagaHelper.from
import org.swiften.redux.saga.ReduxSagaHelper.just
import org.swiften.redux.saga.ReduxSagaHelper.takeEveryAction
import org.swiften.redux.saga.ReduxSagaHelper.takeLatestAction
import org.testng.Assert
import org.testng.annotations.Test
import java.net.URL
import java.util.Collections

/** Created by haipham on 2018/12/23 */
class SagaEffectTest : CommonSagaEffectTest() {
  override fun <T> justEffect(value: T) = just<State, T>(value)

  @ExperimentalCoroutinesApi
  override fun <T : Any> fromEffect(vararg values: T) =
    from<State, T>(this.rxFlowable { values.forEach { this.send(it) } })

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
  fun `Take latest should work in real life scenarios`() {
    // Setup
    data class Action(val query: String) : Redux.IAction
    val finalValues = Collections.synchronizedList(arrayListOf<Any>())

    fun CoroutineScope.searchMusicStore(q: String) = this.async {
      val url = "https://itunes.apple.com/search?term=$q&limit=5&media=music"
      val result = URL(url).readText().replace("\n", "")
      "Input: $q, Result: $result"
    }

    val sourceOutput = takeLatestAction<Unit, Action, String, Any>(
      { it.query },
      { query ->
        just<Unit, String>(query)
          .map { "unavailable$it" }
          .callAsync { this.searchMusicStore(it) }
          .cast<Unit, String, Any>()
          .catchError {}
      },
      ReduxSaga.TakeOptions(0)
    ).invoke(this, Unit) {}

    sourceOutput.subscribe({ finalValues.add(it) })

    // When
    for (i in 0 until 20) { sourceOutput.onAction(Action("$i")) }

    runBlocking {
      withTimeoutOrNull(this@SagaEffectTest.timeout) {
        while (finalValues.size != 1) { delay(100) }; Unit
      }

      // Then
      Assert.assertEquals(finalValues.size, 1)
    }
  }
}
