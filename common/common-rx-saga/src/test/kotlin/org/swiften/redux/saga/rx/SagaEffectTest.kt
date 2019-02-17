/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.saga.rx

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.rx2.rxFlowable
import kotlinx.coroutines.withTimeoutOrNull
import org.junit.Assert.assertEquals
import org.junit.Test
import org.swiften.redux.core.EmptyJob
import org.swiften.redux.core.FinalStore
import org.swiften.redux.core.IReducer
import org.swiften.redux.core.IReduxAction
import org.swiften.redux.core.IReduxActionWithKey
import org.swiften.redux.core.IReduxStore
import org.swiften.redux.core.applyMiddlewares
import org.swiften.redux.core.createAsyncMiddleware
import org.swiften.redux.saga.common.CommonSagaEffectTest
import org.swiften.redux.saga.common.ISagaEffect
import org.swiften.redux.saga.common.SagaEffect
import org.swiften.redux.saga.common.TakeEffect
import org.swiften.redux.saga.common.castValue
import org.swiften.redux.saga.common.catchError
import org.swiften.redux.saga.common.createSagaMiddleware
import org.swiften.redux.saga.common.doOnValue
import org.swiften.redux.saga.common.filter
import org.swiften.redux.saga.common.map
import org.swiften.redux.saga.common.mapAsync
import org.swiften.redux.saga.common.thenMightAsWell
import org.swiften.redux.saga.common.thenNoMatterWhat
import org.swiften.redux.saga.rx.SagaEffects.await
import org.swiften.redux.saga.rx.SagaEffects.from
import org.swiften.redux.saga.rx.SagaEffects.just
import org.swiften.redux.saga.rx.SagaEffects.putInStore
import org.swiften.redux.saga.rx.SagaEffects.selectFromState
import org.swiften.redux.saga.rx.SagaEffects.takeEvery
import org.swiften.redux.saga.rx.SagaEffects.takeEveryForKeys
import org.swiften.redux.saga.rx.SagaEffects.takeLatest
import org.swiften.redux.saga.rx.SagaEffects.takeLatestForKeys
import java.net.URL
import java.util.Collections.synchronizedList
import java.util.Random

/** Created by haipham on 2018/12/23 */
class SagaEffectTest : CommonSagaEffectTest() {
  override fun <T> justEffect(value: T) where T : Any = just(value)

  @ExperimentalCoroutinesApi
  override fun <T : Any> fromEffect(vararg values: T): SagaEffect<T> {
    return from(GlobalScope.rxFlowable { values.forEach { this.send(it) } })
  }

  private fun test_takeEffectDebounce_shouldEmitCorrectValues(
    createTakeEffect: (
      extractor: (IReduxAction) -> Int?,
      creator: (Int) -> ISagaEffect<Int>
    ) -> TakeEffect<IReduxAction, Int, Int>
  ) {
    // Setup
    data class Action(val value: Int) : IReduxAction
    val finalValues = synchronizedList(arrayListOf<Int>())
    val debounceTime = 500L

    val sourceOutput = createTakeEffect({
      (it as Action).value
    }, { value ->
      this@SagaEffectTest.justEffect(value)
    }).debounceTake(debounceTime).invoke()

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
    test_takeEffectDebounce_shouldEmitCorrectValues { a, b -> takeEvery(IReduxAction::class, a, b) }
  }

  @Test
  fun `Take with selects should ensure thread-safety and that latest state is used`() {
    // Setup
    data class State(val a1: Int? = null, val a2: Int? = null)

    abstract class Container(val value: Int) : IReduxActionWithKey {
      override fun toString(): String = "${this.key}-${this.value}"
    }

    class Action1(value: Int) : Container(value) {
      override val key: String get() = "Action1"
    }

    class Action2(value: Int) : Container(value) {
      override val key: String get() = "Action2"
    }

    val reducer: IReducer<State> = { state, action ->
      when (action) {
        is Action1 -> state.copy(a1 = action.value)
        is Action2 -> state.copy(a2 = action.value)
        else -> state
      }
    }

    val enhancedReducer: IReducer<State> = { s, a -> val newState = reducer(s, a); newState }
    var store: IReduxStore<State>? = null
    val finalValues = synchronizedList(arrayListOf<Pair<State, State>>())

    val takeEffect = takeEveryForKeys(setOf("Action1", "Action2")) { action ->
      val lastState = store!!.lastState()
      val newState = reducer(lastState, action)
      selectFromState(State::class) { s -> s to newState }.doOnValue { finalValues.add(it) }
    }

    store = applyMiddlewares<State>(
      createAsyncMiddleware(),
      createAsyncMiddleware(),
      createSagaMiddleware(arrayListOf(takeEffect)),
      createAsyncMiddleware(),
      createAsyncMiddleware()
    )(FinalStore(State(), enhancedReducer))

    // When
    val rand = Random()
    val iteration = 1000

    val jobs = (0 until iteration).map { _ ->
      GlobalScope.launch(Dispatchers.IO, CoroutineStart.LAZY) {
        if (rand.nextBoolean()) {
          store.dispatch(Action1(rand.nextInt(100))).await()
        } else {
          store.dispatch(Action2(rand.nextInt(100))).await()
        }
      }
    }

    runBlocking {
      jobs.forEach { it.join() }

      // Then
      assertEquals(finalValues.size, iteration)
      finalValues.forEach { assertEquals(it.first, it.second) }
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

  @Test
  fun `Select effect should extract some value from a state`() {
    // Setup
    val sourceOutput1 = just(1).selectFromState(Any::class, { 2 }, { a, b -> a + b }).invoke()
    val sourceOutput2 = just(2).selectFromState(State::class) { 4 }.invoke(State())

    // When && Then
    assertEquals(sourceOutput1.awaitFor(this.timeout), 3)
    assertEquals(sourceOutput2.awaitFor(this.timeout), 4)
  }

  @Test
  fun `Await effect should work correctly for successful await sequence`() {
    // Setup
    class State(val value: Int = 0)
    data class ProgressAction(val progress: Boolean) : IReduxAction
    data class ValueAction(val value: Int) : IReduxAction

    val dispatched = arrayListOf<IReduxAction>()

    val source = await { input ->
      putInStore(ProgressAction(true)).invoke(input).await({})
      val value = selectFromState(State::class) { it.value }.invoke(input).await(0)
      val newValue = value * 2
      putInStore(ValueAction(newValue)).invoke(input).await({})
      putInStore(ProgressAction(false)).invoke(input).await({})
    }

    // When
    source.invoke(GlobalScope, State(10)) { dispatched.add(it); EmptyJob }.subscribe({})

    runBlocking {
      delay(1000)

      // Then
      assertEquals(dispatched, arrayListOf(
        ProgressAction(true),
        ValueAction(20),
        ProgressAction(false)
      ))
    }
  }

  @Test
  fun `Await effect should work correctly for erroneous await sequence`() {
    // Setup
    class State(val value: Int = 0)
    data class ProgressAction(val progress: Boolean) : IReduxAction
    data class ValueAction(val value: Int) : IReduxAction
    data class ErrorAction(val error: Throwable) : IReduxAction

    val dispatched = arrayListOf<IReduxAction>()
    val error = Exception("Oops!")
    val api: suspend (Int) -> Int = { throw error }

    val source = await { input ->
      putInStore(ProgressAction(true)).invoke(input).await({})
      val value = selectFromState(State::class) { it.value }.filter { false }.invoke(input).await(0)

      try {
        val newValue = api(value)
        putInStore(ValueAction(newValue)).invoke(input).await({})
      } catch (e: Throwable) {
        putInStore(ErrorAction(e)).invoke(input).await({})
      } finally {
        putInStore(ProgressAction(false)).invoke(input).await({})
      }
    }

    // When
    source.invoke(GlobalScope, State(10)) { dispatched.add(it); EmptyJob }.subscribe({})

    runBlocking {
      delay(1000)

      // Then
      assertEquals(dispatched, arrayListOf(
        ProgressAction(true),
        ErrorAction(error),
        ProgressAction(false)
      ))
    }
  }
}
