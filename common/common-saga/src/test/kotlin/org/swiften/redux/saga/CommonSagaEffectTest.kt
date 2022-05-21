/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.saga

import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.CoroutineScope
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
import org.swiften.redux.core.DefaultReduxAction
import org.swiften.redux.core.EmptyAwaitable
import org.swiften.redux.core.FinalStore
import org.swiften.redux.core.IActionDispatcher
import org.swiften.redux.core.IReducer
import org.swiften.redux.core.IReduxAction
import org.swiften.redux.core.IReduxStore
import org.swiften.redux.core.NoopActionDispatcher
import org.swiften.redux.core.applyMiddlewares
import org.swiften.redux.saga.CommonEffects.await
import org.swiften.redux.saga.CommonEffects.mergeAll
import org.swiften.redux.saga.CommonEffects.put
import org.swiften.redux.saga.CommonEffects.select
import org.swiften.redux.saga.CommonEffects.takeAction
import java.net.URL
import java.util.Collections.synchronizedList
import java.util.Random
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.atomic.AtomicInteger

/** Created by haipham on 2019/01/07 */
/** Use this test class for common [ISagaEffect] tests */
@ExperimentalCoroutinesApi
class CommonSagaEffectTest {
  private class State

  protected val timeout: Long = 10000
  protected val iteration = 1000

  private fun <T> fromEffect(vararg values: T): SagaEffect<T> where T : Any {
    return CommonEffects.from(rxFlowable { values.forEach { this.send(it) } })
  }

  private fun test_takeEffect_shouldTakeCorrectActions(
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
      { v -> await { delay(1000); v } }
    )
      .invoke(SagaInput(monitor = monitor))
      .subscribe({ finalValues.add(it as Int) })

    // When
    (0 until this.iteration).forEach { i ->
      GlobalScope.launch(Dispatchers.IO) { monitor.dispatch(Action(i)) }
      GlobalScope.launch(Dispatchers.IO) { monitor.dispatch(DefaultReduxAction.Dummy) }
    }

    runBlocking {
      withTimeoutOrNull(this@CommonSagaEffectTest.timeout) {
        while (!verifyValues(finalValues.sorted()) && this.isActive) { delay(500) }; Unit
      }

      // Then
      assertTrue(verifyValues(finalValues.sorted()))
    }
  }

  private fun test_takeStateEffect_shouldSelectCorrectState(
    createTakeEffect: (creator: (String) -> SagaEffect<String>) -> SagaEffect<String>,
    verifyValues: (Collection<String>) -> Boolean
  ) {
    // Setup
    data class Action(val value: Int) : IReduxAction
    val defaultState = "0"
    val finalValues = synchronizedList(arrayListOf<String>())
    val takeEffect = createTakeEffect { v -> await { delay(500); finalValues.add(v); v } }

    val store = applyMiddlewares<String>(
      SagaMiddleware.create(Schedulers.computation(), arrayListOf(takeEffect))
    )(FinalStore(defaultState) { s, _ -> "${s.toInt() + 1}" })

    // When
    (0 until this.iteration).forEach { i ->
      GlobalScope.launch(Dispatchers.IO) { store.dispatch(Action(i)) }
    }

    runBlocking {
      withTimeoutOrNull(this@CommonSagaEffectTest.timeout) {
        while (!verifyValues(finalValues.sorted()) && this.isActive) { delay(500) }; Unit
      }

      store.dispatch(DefaultReduxAction.Deinitialize)

      // Then
      verifyValues(finalValues.sorted())
    }
  }

  private fun test_takeEffectDebounce_shouldEmitCorrectValues(
    createTakeEffect: (extractor: (IReduxAction) -> Int) -> SagaEffect<Int>
  ) {
    // Setup
    data class Action(val value: Int) : IReduxAction
    val monitor = SagaMonitor()
    val finalValues = synchronizedList(arrayListOf<Int>())
    val debounceTime = 500L

    createTakeEffect { (it as Action).value }
      .debounce(debounceTime)
      .invoke(SagaInput(monitor = monitor))
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

      withTimeoutOrNull(this@CommonSagaEffectTest.timeout) {
        while (finalValues.sorted() != correctValues.sorted() && this.isActive) { delay(500) }; Unit
      }

      // Then
      assertEquals(finalValues.sorted(), correctValues.sorted())
    }
  }

  private fun test_streamDisposition_shouldRemoveEntryFromMonitor(
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
        dispatchers.forEach { it.value(action) }; EmptyAwaitable
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
      await {
        val value = (a as Action).value
        it.async { value * 2 }.await()
        put(DefaultReduxAction.Dummy).await(it)
      }
    }.invoke(SagaInput(monitor = monitor))

    val disposable = sourceOutput.subscribe({}, {})

    // When
    (0 until this.iteration).forEach { monitor.dispatch(Action(it)) }

    runBlocking {
      delay(1000)
      disposable.dispose()

      // Then
      assertTrue(setCount.get() > 0)
      assertEquals(setCount.get(), removeCount.get())
    }
  }

  @Test
  fun `Put effect should dispatch action`() {
    // Setup
    data class Action(private val value: Int) : IReduxAction
    val dispatched = synchronizedList(arrayListOf<IReduxAction>())

    // When
    put(Action(0)).invoke(SagaInput(dispatch = { dispatched.add(it); EmptyAwaitable })).subscribe({})

    runBlocking {
      withTimeoutOrNull(this@CommonSagaEffectTest.timeout) {
        while (dispatched != arrayListOf(Action(0)) && this.isActive) { delay(500) }; Unit
      }

      // Then
      assertEquals(dispatched, arrayListOf(Action(0)))
    }
  }

  @Test
  fun `Take effect debounce should emit correct values`() {
    test_takeEffectDebounce_shouldEmitCorrectValues {
      CommonEffects.takeAction(IReduxAction::class, it)
    }
  }

  @Test
  fun `Take with selects should ensure thread-safety and that latest state is used`() {
    // Setup
    data class State(val a1: Int? = null, val a2: Int? = null, val lastAction: IReduxAction? = null)

    abstract class Container(val value: Int) : IReduxAction {
      override fun toString(): String = "${this.value}"
    }

    class Action1(value: Int) : Container(value) {
      override fun toString(): String = "A1 ${super.toString()}"
    }

    class Action2(value: Int) : Container(value) {
      override fun toString(): String = "A2 ${super.toString()}"
    }

    val reducer: IReducer<State, IReduxAction> = { state, action ->
      when (action) {
        is Action1 -> state.copy(a1 = action.value)
        is Action2 -> state.copy(a2 = action.value)
        else -> state
      }.copy(lastAction = action)
    }

    var store: IReduxStore<State>? = null
    val finalValues = synchronizedList(arrayListOf<Pair<State, State>>())

    val takeEffect = takeAction(Container::class) { it }.flatMap { _ ->
      await {
        val value = select(State::class) { s -> s to store!!.lastState() }.await(it)
        finalValues.add(value)
      }
    }

    store = applyMiddlewares<State>(
      SagaMiddleware.create(Schedulers.io(), arrayListOf(takeEffect))
    )(FinalStore(State(), reducer))

    // When
    val rand = Random()
    val iteration = 5

    (0 until iteration).map { i ->
      GlobalScope.launch {
        if (rand.nextBoolean()) {
          store.dispatch(Action1(i)).await()
        } else {
          store.dispatch(Action2(i)).await()
        }
      }
    }

    runBlocking {
      withTimeoutOrNull(this@CommonSagaEffectTest.timeout) {
        while (finalValues.size != iteration && this.isActive) { delay(500) }; Unit
      }

      // Then
      assertEquals(finalValues.size, iteration)
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

    CommonEffects.takeAction(Action::class) { it.query }.switchMap { query ->
      await {
        try {
          it.searchMusicStoreAsync("unavailable$query").await()
        } catch (e: Exception) {
          "Failed for some reasons"
        }
      }
    }
      .invoke(SagaInput(monitor = monitor))
      .subscribe({ finalValues.add(it) })

    // When
    for (i in 0 until 20) { monitor.dispatch(Action("$i")) }

    runBlocking {
      withTimeoutOrNull(this@CommonSagaEffectTest.timeout) {
        while (finalValues.size != 1 && this.isActive) { delay(500) }; Unit
      }

      // Then
      assertEquals(finalValues.size, 1)
    }
  }

  @Test
  @ExperimentalCoroutinesApi
  fun `All effect should merge emissions from all sources`() {
    // Setup
    val finalValues = synchronizedList(arrayListOf<Int>())

    // When
    mergeAll(fromEffect(1), fromEffect(2), fromEffect(3), fromEffect(4))
      .invoke(SagaInput())
      .subscribe({ finalValues.add(it) })

    runBlocking {
      withTimeoutOrNull(this@CommonSagaEffectTest.timeout) {
        if (finalValues.sorted() != arrayListOf(1, 2, 3, 4)) { }; Unit
      }

      // Then
      assertEquals(finalValues.sorted(), arrayListOf(1, 2, 3, 4))
    }
  }

  @Test
  fun `Dispatching actions from all effect should call dispatch for all sub-effects`() {
    // Setup
    data class Action(val value: Int) : IReduxAction, Comparable<Action> {
      override fun compareTo(other: Action): Int {
        return this.value - other.value
      }
    }

    val monitor = SagaMonitor()
    val finalValues = synchronizedList(arrayListOf<Int>())
    val correctValues = (0 until 3).map { arrayListOf(it, it * 2, it * 3) }.flatten().sorted()

    CommonEffects.mergeAll(
      CommonEffects.takeAction(Action::class) { it.value }.flatMap { v -> await { v } },
      CommonEffects.takeAction(Action::class) { it.value }.flatMap { v -> await { v * 2 } },
      CommonEffects.takeAction(Action::class) { it.value }.flatMap { v -> await { v * 3 } }
    )
      .invoke(SagaInput(monitor = monitor))
      .subscribe({ finalValues.add(it) })

    // When
    (0 until 3).forEach { monitor.dispatch(Action(it)) }

    runBlocking {
      withTimeoutOrNull(this@CommonSagaEffectTest.timeout) {
        while (finalValues.sorted() != correctValues && this.isActive) { delay(500) }; Unit
      }

      // Then
      assertEquals(finalValues.sorted(), correctValues)
    }
  }

  @Test
  fun `Nothing effect should not emit anything`() {
    // Setup && When
    val value = CommonEffects.doNothing<Int>().invoke(SagaInput()).await(200)

    // Then
    assertEquals(value, 200)
  }

  @Test
  fun `Select effect should extract some value from a state`() {
    // Setup
    val sourceOutput1 = CommonEffects.select(Any::class) { 2 }.invoke(SagaInput())

    val sourceOutput2 = CommonEffects.select(State::class) { 4 }
      .invoke(SagaInput(dispatch = NoopActionDispatcher, lastState = { State() }))

    // When && Then
    assertEquals(sourceOutput1.awaitFor(this.timeout), 2)
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
      put(ProgressAction(true)).await(input)
      val value = CommonEffects.select(State::class) { it.value }.await(input)
      val newValue = value * 2
      put(ValueAction(newValue)).await(input)
      put(ProgressAction(false)).await(input)
    }
      .invoke(SagaInput(dispatch = { dispatched.add(it); EmptyAwaitable }, lastState = { State(10) }))
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
      CommonEffects.takeAction(IReduxAction::class) { it }.switchMap(creator)
    }
  }

  @Test
  fun `Take every effect should take all actions`() {
    val actualValues = (0 until this.iteration).toList()

    test_takeEffect_shouldTakeCorrectActions({ a, b ->
      CommonEffects.takeAction(IReduxAction::class, a).flatMap(b)
    }) { it == actualValues.sorted() }
  }

  @Test
  fun `Take latest effect should take latest actions`() {
    test_takeEffect_shouldTakeCorrectActions({ a, b ->
      CommonEffects.takeAction(IReduxAction::class, a).switchMap(b)
    }) { it.size == 1 }
  }

  @Test
  fun `Take every state should stream correct state`() {
    val actualValues = (0..this.iteration).map { "$it" }

    this.test_takeStateEffect_shouldSelectCorrectState({
      CommonEffects.takeState(String::class).flatMap(it)
    }) { it == actualValues.sorted() }
  }

  @Test
  fun `Take latest state should stream correct state`() {
    this.test_takeStateEffect_shouldSelectCorrectState({
      CommonEffects.takeState(String::class).switchMap(it)
    }) { it.size == 1 }
  }
}
