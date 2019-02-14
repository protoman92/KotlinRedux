/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.saga.rx

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.rx2.rxFlowable
import kotlinx.coroutines.withTimeoutOrNull
import org.junit.Assert.assertEquals
import org.junit.Test
import org.swiften.redux.core.IReduxAction
import org.swiften.redux.saga.common.CommonSagaEffectTest
import org.swiften.redux.saga.common.castValue
import org.swiften.redux.saga.common.catchError
import org.swiften.redux.saga.common.filter
import org.swiften.redux.saga.common.map
import org.swiften.redux.saga.common.mapAsync
import org.swiften.redux.saga.common.thenMightAsWell
import org.swiften.redux.saga.common.thenNoMatterWhat
import org.swiften.redux.saga.rx.SagaEffects.from
import org.swiften.redux.saga.rx.SagaEffects.just
import org.swiften.redux.saga.rx.SagaEffects.takeEvery
import org.swiften.redux.saga.rx.SagaEffects.takeEveryForKeys
import org.swiften.redux.saga.rx.SagaEffects.takeLatest
import org.swiften.redux.saga.rx.SagaEffects.takeLatestForKeys
import java.net.URL
import java.util.Collections.synchronizedList

/** Created by haipham on 2018/12/23 */
class SagaEffectTest : CommonSagaEffectTest() {
  override fun <T> justEffect(value: T) where T : Any = just(value)

  @ExperimentalCoroutinesApi
  override fun <T : Any> fromEffect(vararg values: T) =
    from(GlobalScope.rxFlowable { values.forEach { this.send(it) } })

  @Test
  fun `Take every effect should take all actions`() {
    test_takeEffect_shouldTakeCorrectActions(
      { a, b -> takeEvery(IReduxAction::class, a, creator = b) }, arrayListOf(0, 1, 2, 3))
  }

  @Test
  fun `Take latest effect should take latest actions`() {
    test_takeEffect_shouldTakeCorrectActions(
      { a, b -> takeLatest(IReduxAction::class, a, creator = b) }, arrayListOf(3))
  }

  @Test
  fun `Take every effect with keys should take correct actions`() {
    test_takeEffectWithActionKeys_shouldTakeCorrectActions { a, b -> takeEveryForKeys(a, creator = b) }
  }

  @Test
  fun `Take latest effect with keys should take correct actions`() {
    test_takeEffectWithActionKeys_shouldTakeCorrectActions { a, b -> takeLatestForKeys(a, creator = b) }
  }

  @Test
  fun `Select effect should extract some value from a state`() {
    // Setup
    val sourceOutput1 = just(1).selectFromState(Any::class, { 2 }, { a, b -> a + b }).invoke()
    val sourceOutput2 = just(2).selectFromState(State::class) { 4 }.invoke(State())

    // When && Then
    assertEquals(sourceOutput1.nextValue(this.timeout), 3)
    assertEquals(sourceOutput2.nextValue(this.timeout), 4)
  }

  @Test
  fun `Take latest with forceful then should work correctly`() {
    // Setup
    data class Action(val value: Int) : IReduxAction
    val finalValues = synchronizedList(arrayListOf<Int>())
    val defaultValue = -1
    val ignoredValue = -2
    val error = "Error!"
    val allActionValues = (0 until 100)
    val correctValues = allActionValues.map { defaultValue }

    val sourceOutput = takeEvery(Action::class, { it.value }) { value ->
      this@SagaEffectTest.justEffect(value)
        .filter { it % 3 == 0 }
        .map { if (it % 2 == 0) throw Exception(error) else it }
        .thenNoMatterWhat(just(defaultValue))
        .thenMightAsWell(justEffect(ignoredValue))
    }.invoke()

    sourceOutput.subscribe({ finalValues.add(it) })

    // When
    allActionValues.forEach { sourceOutput.onAction(Action(it)) }

    runBlocking {
      withTimeoutOrNull(this@SagaEffectTest.timeout) {
        while (finalValues.sorted() != correctValues.sorted()) { }; Unit
      }

      // Then
      assertEquals(finalValues.sorted(), correctValues.sorted())
    }
  }

  @Test
  fun `Take effect debounce should emit correct values`() {
    // Setup
    data class Action(val value: Int) : IReduxAction
    val finalValues = synchronizedList(arrayListOf<Int>())
    val debounceTime = 500L

    val sourceOutput = takeEvery(Action::class, { it.value }) { value ->
      this@SagaEffectTest.justEffect(value)
    }.debounceTake(debounceTime).invoke()

    sourceOutput.subscribe({ finalValues.add(it) })

    // When
    runBlocking {
      sourceOutput.onAction(Action(0))
      delay(debounceTime - 100)
      sourceOutput.onAction(Action(1))
      delay(debounceTime + 100)
      sourceOutput.onAction(Action(2))
      delay(debounceTime - 100)
      sourceOutput.onAction(Action(3))
      delay(debounceTime + 100)
      sourceOutput.onAction(Action(4))
      delay(debounceTime - 100)
      sourceOutput.onAction(Action(5))

      val correctValues = arrayListOf(1, 3, 5)

      withTimeoutOrNull(this@SagaEffectTest.timeout) {
        while (finalValues.sorted() != correctValues.sorted()) { }; Unit
      }

      // Then
      assertEquals(finalValues.sorted(), correctValues.sorted())
    }
  }

  @Test
  fun `Take latest should work in real life scenarios`() {
    // Setup
    data class Action(val query: String) : IReduxAction
    val finalValues = synchronizedList(arrayListOf<Any>())

    fun CoroutineScope.searchMusicStore(q: String) = this.async {
      val url = "https://itunes.apple.com/search?term=$q&limit=5&media=music"
      val result = URL(url).readText().replace("\n", "")
      "Input: $q, Result: $result"
    }

    val sourceOutput = takeLatest(Action::class, { it.query }) { query ->
      just(query)
        .map { "unavailable$it" }
        .mapAsync { this.searchMusicStore(it) }
        .castValue<Any>()
        .catchError {}
    }.invoke()

    sourceOutput.subscribe({ finalValues.add(it) })

    // When
    for (i in 0 until 20) { sourceOutput.onAction(Action("$i")) }

    runBlocking {
      withTimeoutOrNull(this@SagaEffectTest.timeout) {
        while (finalValues.size != 1) { delay(100) }; Unit
      }

      // Then
      assertEquals(finalValues.size, 1)
    }
  }
}
