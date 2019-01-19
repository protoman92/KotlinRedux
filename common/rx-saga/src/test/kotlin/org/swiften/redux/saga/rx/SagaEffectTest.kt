/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.saga.rx

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.rx2.rxFlowable
import kotlinx.coroutines.withTimeoutOrNull
import org.junit.Assert
import org.junit.Test
import org.swiften.redux.core.IReduxAction
import org.swiften.redux.saga.CommonSagaEffectTest
import org.swiften.redux.saga.cast
import org.swiften.redux.saga.catchError
import org.swiften.redux.saga.map
import org.swiften.redux.saga.mapAsync
import org.swiften.redux.saga.rx.ReduxSagaEffects.from
import org.swiften.redux.saga.rx.ReduxSagaEffects.just
import org.swiften.redux.saga.rx.ReduxSagaEffects.takeEveryAction
import org.swiften.redux.saga.rx.ReduxSagaEffects.takeLatestAction
import java.net.URL
import java.util.Collections

/** Created by haipham on 2018/12/23 */
class SagaEffectTest : CommonSagaEffectTest() {
  override fun <T> justEffect(value: T) = just(value)

  @ExperimentalCoroutinesApi
  override fun <T : Any> fromEffect(vararg values: T) =
    from(this.rxFlowable { values.forEach { this.send(it) } })

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
    val sourceOutput1 = just(1)
      .select<State, Int, Int, Int>({ 2 }, { a, b -> a + b })
      .invoke(this, State()) { }

    val sourceOutput2 = just(2)
      .select<State, Int> { 4 }
      .invoke(this, State()) { }

    // When && Then
    Assert.assertEquals(sourceOutput1.nextValue(this.timeout), 3)
    Assert.assertEquals(sourceOutput2.nextValue(this.timeout), 4)
  }

  @Test
  fun `Take latest should work in real life scenarios`() {
    // Setup
    data class Action(val query: String) : IReduxAction
    val finalValues = Collections.synchronizedList(arrayListOf<Any>())

    fun CoroutineScope.searchMusicStore(q: String) = this.async {
      val url = "https://itunes.apple.com/search?term=$q&limit=5&media=music"
      val result = URL(url).readText().replace("\n", "")
      "Input: $q, Result: $result"
    }

    val sourceOutput = takeLatestAction<Action, String, Any>(
      { it.query },
      { query ->
        just(query)
          .map { "unavailable$it" }
          .mapAsync { this.searchMusicStore(it) }
          .
            cast<Any>()
          .catchError {}
      },
      TakeEffectOptions(0)
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
