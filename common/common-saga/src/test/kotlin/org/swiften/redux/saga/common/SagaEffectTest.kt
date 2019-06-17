/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.saga.common

import io.reactivex.Single
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.rx2.rxFlowable
import kotlinx.coroutines.withTimeoutOrNull
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.swiften.redux.core.AsyncMiddleware
import org.swiften.redux.core.DefaultReduxAction
import org.swiften.redux.core.EmptyJob
import org.swiften.redux.core.FinalStore
import org.swiften.redux.core.IActionDispatcher
import org.swiften.redux.core.IReducer
import org.swiften.redux.core.IReduxAction
import org.swiften.redux.core.IReduxStore
import org.swiften.redux.core.NoopActionDispatcher
import org.swiften.redux.core.applyMiddlewares
import org.swiften.redux.saga.common.SagaEffects.await
import org.swiften.redux.saga.common.SagaEffects.doNothing
import org.swiften.redux.saga.common.SagaEffects.from
import org.swiften.redux.saga.common.SagaEffects.just
import org.swiften.redux.saga.common.SagaEffects.mergeAll
import org.swiften.redux.saga.common.SagaEffects.putInStore
import org.swiften.redux.saga.common.SagaEffects.selectFromState
import org.swiften.redux.saga.common.SagaEffects.takeAction
import java.net.URL
import java.util.Collections.synchronizedList
import java.util.Random
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.atomic.AtomicInteger

/** Created by haipham on 2018/12/23 */
abstract class OverridableSagaEffectTest : CommonSagaEffectTest() {
  override fun <T> justEffect(value: T) where T : Any = just(value)

  @ExperimentalCoroutinesApi
  override fun <T : Any> fromEffect(vararg values: T): SagaEffect<T> {
    return from(GlobalScope.rxFlowable { values.forEach { this.send(it) } })
  }

  @Test
  fun `Take every effect should take all actions`() {
    val actualValues = (0 until this.iteration).toList()

    test_takeEffect_shouldTakeCorrectActions({ a, b ->
      takeAction(IReduxAction::class, a).flatMap(b)
    }) { it == actualValues.sorted() }
  }

  @Test
  fun `Take latest effect should take latest actions`() {
    test_takeEffect_shouldTakeCorrectActions({ a, b ->
      takeAction(IReduxAction::class, a).switchMap(b)
    }) { it.size == 1 }
  }

  @Test
  fun `Take every state should stream correct state`() {
    val actualValues = (0..this.iteration).map { "$it" }

    this.test_takeStateEffect_shouldSelectCorrectState({
      SagaEffects.takeState(String::class).flatMap(it)
    }) { it == actualValues.sorted() }
  }

  @Test
  fun `Take latest state should stream correct state`() {
    this.test_takeStateEffect_shouldSelectCorrectState({
      SagaEffects.takeState(String::class).switchMap(it)
    }) { it.size == 1 }
  }

  protected fun test_takeEffectDebounce_shouldEmitCorrectValues(
    createTakeEffect: (
      extractor: (IReduxAction) -> Int?,
      creator: (Int) -> SagaEffect<Int>
    ) -> SagaEffect<Int>
  ) {
    // Setup
    data class Action(val value: Int) : IReduxAction
    val monitor = SagaMonitor()
    val finalValues = synchronizedList(arrayListOf<Int>())
    val debounceTime = 500L

    createTakeEffect({ (it as Action).value }, { value -> justEffect(value) })
      .debounce(debounceTime)
      .invoke(SagaInput(monitor))
      .subscribe({ finalValues.add(it) })

    // When
    runBlocking {
      monitor.dispatch(Action(0))
      delay(debounceTime - 100)
      monitor.dispatch(Action(1))
      delay(debounceTime + 100)
      monitor.dispatch(Action(2))
      delay(debounceTime - 100)
      monitor.dispatch(Action(3))
      delay(debounceTime + 100)
      monitor.dispatch(Action(4))
      delay(debounceTime - 100)
      monitor.dispatch(Action(5))

      val correctValues = arrayListOf(1, 3, 5)

      withTimeoutOrNull(this@OverridableSagaEffectTest.timeout) {
        while (finalValues.sorted() != correctValues.sorted() && this.isActive) { delay(500) }; Unit
      }

      // Then
      assertEquals(finalValues.sorted(), correctValues.sorted())
    }
  }

  protected fun test_streamDisposition_shouldRemoveEntryFromMonitor(
    createTakeEffect: (
      creator: (IReduxAction) -> SagaEffect<Any>
    ) -> SagaEffect<Any>
  ) {
    // Setup
    class Action(val value: Int) : IReduxAction
    val dispatchers = ConcurrentHashMap<Long, IActionDispatcher>()
    val setCount = AtomicInteger(0)
    val removeCount = AtomicInteger(0)

    val monitor = object : ISagaMonitor {
      override val dispatch: IActionDispatcher = { action ->
        dispatchers.forEach { it.value(action) }; EmptyJob
      }

      override fun addOutputDispatcher(id: Long, dispatch: IActionDispatcher) {
        dispatchers[id] = dispatch
        setCount.incrementAndGet()
      }

      override fun removeOutputDispatcher(id: Long) {
        /** If no id found, do not increment remove count. */
        dispatchers.remove(id)?.also { removeCount.incrementAndGet() }
      }
    }

    val sourceOutput = createTakeEffect { a ->
      justEffect((a as Action).value)
        .map { if (it % 2 == 0) it else { throw Exception("Oops!") } }
        .catchError { 10 }
        .delayUpstreamValue(500)
        .mapSingle { Single.just(1000) }
        .mapAsync { this.async { it } }
        .putInStore { DefaultReduxAction.Dummy }
    }.invoke(SagaInput(monitor))

    sourceOutput.subscribe({}, {})

    // When
    (0 until this.iteration).forEach { monitor.dispatch(Action(it)) }

    runBlocking {
      delay(1000)

      // Clear the take publisher to register a remove event for it as well.
      sourceOutput.dispose()

      // Then
      assertTrue(setCount.get() > 0)
      assertEquals(setCount.get(), removeCount.get())
    }
  }
}

class SagaEffectTest : OverridableSagaEffectTest() {
  @Test
  fun `Take effect debounce should emit correct values`() {
    test_takeEffectDebounce_shouldEmitCorrectValues { a, b ->
      takeAction(IReduxAction::class, a).flatMap(b)
    }
  }

  @Test
  @Suppress("RedundantLambdaArrow")
  fun `Take with selects should ensure thread-safety and that latest state is used`() {
    // Setup
    data class State(val a1: Int? = null, val a2: Int? = null)

    abstract class Container(val value: Int) : IReduxAction {
      override fun toString(): String = "${this.value}"
    }

    class Action1(value: Int) : Container(value)
    class Action2(value: Int) : Container(value)

    val reducer: IReducer<State, IReduxAction> = { state, action ->
      when (action) {
        is Action1 -> state.copy(a1 = action.value)
        is Action2 -> state.copy(a2 = action.value)
        else -> state
      }
    }

    val enhancedReducer: IReducer<State, IReduxAction> = { s, a ->
      val newState = reducer(s, a); newState
    }

    var store: IReduxStore<State>? = null
    val finalValues = synchronizedList(arrayListOf<Pair<State, State>>())

    val takeEffect = takeAction(Container::class) { it }.switchMap { action ->
      val lastState = store!!.lastState()
      val newState = reducer(lastState, action)
      selectFromState(State::class) { s -> s to newState }.doOnValue { finalValues.add(it) }
    }

    store = applyMiddlewares<State>(
      AsyncMiddleware.create(),
      AsyncMiddleware.create(),
      SagaMiddleware.create(arrayListOf(takeEffect)),
      AsyncMiddleware.create(),
      AsyncMiddleware.create()
    )(FinalStore(State(), enhancedReducer))

    // When
    val rand = Random()

    val jobs = (0 until this.iteration).map { _ ->
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
      assertEquals(finalValues.size, this@SagaEffectTest.iteration)
      finalValues.forEach { assertEquals(it.first, it.second) }
    }
  }

  @Test
  fun `Take latest should work in real life scenarios`() {
    // Setup
    data class Action(val query: String) : IReduxAction
    val monitor = SagaMonitor()
    val finalValues = synchronizedList(arrayListOf<Any>())

    fun CoroutineScope.searchMusicStoreAsync(q: String) = this.async {
      val url = "https://itunes.apple.com/search?term=$q&limit=5&media=music"
      val result = URL(url).readText().replace("\n", "")
      "Input: $q, Result: $result"
    }

    takeAction(Action::class, { it.query }).switchMap { query ->
      just(query)
        .map { "unavailable$it" }
        .mapAsync { this.searchMusicStoreAsync(it) }
        .mapSingle { Single.just(it) }
        .catchError {""}
    }
      .invoke(SagaInput(monitor))
      .subscribe({ finalValues.add(it) })

    // When
    for (i in 0 until 20) { monitor.dispatch(Action("$i")) }

    runBlocking {
      withTimeoutOrNull(this@SagaEffectTest.timeout) {
        while (finalValues.size != 1 && this.isActive) { delay(500) }; Unit
      }

      // Then
      assertEquals(finalValues.size, 1)
    }
  }

  @Test
  fun `All effect should merge emissions from all sources`() {
    // Setup
    val monitor = SagaMonitor()
    val finalValues = synchronizedList(arrayListOf<Int>())

    // When
    mergeAll(
      justEffect(1),
      justEffect(2),
      justEffect(3),
      justEffect(4)
    )
      .invoke(SagaInput(monitor))
      .subscribe({ finalValues.add(it) })

    runBlocking {
      withTimeoutOrNull(this@SagaEffectTest.timeout) {
        if (finalValues.sorted() != arrayListOf(1, 2, 3, 4)) { }; Unit
      }

      // Then
      assertEquals(finalValues.sorted(), arrayListOf(1, 2, 3, 4))
    }
  }

  @Test
  fun `Dispatching actions should all effect should call dispatch for all sub-effects`() {
    // Setup
    data class Action(val value: Int) : IReduxAction, Comparable<Action> {
      override fun compareTo(other: Action): Int {
        return this.value - other.value
      }
    }

    val monitor = SagaMonitor()
    val finalValues = synchronizedList(arrayListOf<Int>())
    val correctValues = (0 until this.iteration).map { arrayListOf(it, it * 2, it * 3) }.flatten().sorted()

    mergeAll(
      takeAction(Action::class, { it.value }).switchMap { v -> justEffect(v).map { it } },
      takeAction(Action::class, { it.value }).switchMap { v -> justEffect(v).map { it * 2 } },
      takeAction(Action::class, { it.value }).switchMap { v -> justEffect(v).map { it * 3 } }
    )
      .invoke(SagaInput(monitor))
      .subscribe({ finalValues.add(it) })

    // When
    (0 until this.iteration).forEach { monitor.dispatch(Action(it)) }

    runBlocking {
      withTimeoutOrNull(this@SagaEffectTest.timeout) {
        while (finalValues.sorted() != correctValues && this.isActive) { delay(500) }; Unit
      }

      // Then
      assertEquals(finalValues.sorted(), correctValues)
    }
  }

  @Test
  fun `Nothing effect should not emit anything`() {
    // Setup
    val monitor = SagaMonitor()

    val value = justEffect(100)
      .thenSwitchTo(doNothing<Int>())
      .map { it * 2 }
      .invoke(SagaInput(monitor))
      .await(200)

    // When && Then
    assertEquals(value, 200)
  }

  @Test
  fun `Select effect should extract some value from a state`() {
    // Setup
    val monitor = SagaMonitor()

    val sourceOutput1 = just(1).selectFromState(Any::class, { 2 }, { a, b -> a + b })
      .invoke(SagaInput(monitor))

    val sourceOutput2 = just(2).selectFromState(State::class) { 4 }
      .invoke(SagaInput(monitor, { State() }, NoopActionDispatcher))

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

    val monitor = SagaMonitor()
    val dispatched = arrayListOf<IReduxAction>()

    // When
    await { input ->
      putInStore(ProgressAction(true)).await(input)
      val value = selectFromState(State::class) { it.value }.await(input)
      val newValue = value * 2
      putInStore(ValueAction(newValue)).await(input)
      putInStore(ProgressAction(false)).await(input)
    }
      .invoke(SagaInput(monitor, { State(10) }) { dispatched.add(it); EmptyJob })
      .subscribe({})

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
  fun `Stream disposition should remove entry from monitor`() {
    test_streamDisposition_shouldRemoveEntryFromMonitor { creator ->
      takeAction(IReduxAction::class) { it }.switchMap(creator)
    }
  }
}
