/*
 * Copyright (c) haipham 2018. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.saga

import kotlinx.coroutines.*
import kotlinx.coroutines.rx2.rxFlowable
import org.swiften.redux.core.Redux
import org.swiften.redux.core.ReduxPreset
import org.swiften.redux.saga.ReduxSagaHelper.from
import org.swiften.redux.saga.ReduxSagaHelper.just
import org.swiften.redux.saga.ReduxSagaHelper.takeEveryAction
import org.swiften.redux.saga.ReduxSagaHelper.takeLatestAction
import org.testng.Assert
import org.testng.annotations.AfterMethod
import org.testng.annotations.BeforeMethod
import org.testng.annotations.Test
import java.net.URL
import java.util.*

/** Created by haipham on 2018/12/23 */
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
    val takeOutput = createTakeEffect(
      { when (it) {is TakeAction.Action1 -> it.value } },
      { just<State, Int>(it).callSuspend { delay(1000); it } }
    ).invoke(this, State()) { }

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
  fun `Catch error effect should handle errors gracefully`() {
    /// Setup
    val error = Exception("Oh no!")

    val finalOutput = just<State, Int>(1)
      .map { throw error; 1 }
      .delay(1000)
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
  @ExperimentalCoroutinesApi
  fun `Filter effect should filter out unwanted values`() {
    /// Setup
    val sourceStream = this.rxFlowable {
      this.send(0)
      this.send(1)
      this.send(2)
      this.send(3)
    }

    val finalOutput = from<State, Int>(sourceStream)
      .filter { it % 2 == 0 }
      .delay(100)
      .cast<State, Int, Int>()
      .invoke(this, State()) { }

    val finalValues = Collections.synchronizedList(arrayListOf<Int>())

    /// When
    finalOutput.subscribe({ finalValues.add(it) })

    runBlocking {
      withTimeoutOrNull(this@SagaEffectTest.timeout) {
        while (finalValues != arrayListOf(0, 2)) { }; Unit
      }

      /// Then
      Assert.assertEquals(finalValues, arrayListOf(0, 2))
    }
  }

  @Test
  fun `Put effect should dispatch action`() {
    /// Setup
    data class Action(private val value: Int) : Redux.IAction
    val dispatched = arrayListOf<Redux.IAction>()

    val finalOutput = just<State, Int>(0)
      .map { it }
      .put { Action(it) }
      .invoke(this, State()) { dispatched.add(it) }

    /// When
    finalOutput.subscribe({})

    runBlocking {
      withTimeoutOrNull(this@SagaEffectTest.timeout) {
        while (dispatched != arrayListOf(Action(0))) { }; Unit
      }

      /// Then
      Assert.assertEquals(dispatched, arrayListOf(Action(0)))
    }
  }

  @Test
  fun `Select effect should extract some value from a state`() {
    /// Setup
    val sourceOutput1 = just<State, Int>(1)
      .select({ 2 }, { a, b -> a + b })
      .invoke(this, State()) { }

    val sourceOutput2 = just<State, Int>(2)
      .select { 4 }
      .invoke(this, State()) { }

    /// When && Then
    Assert.assertEquals(sourceOutput1.nextValue(this.timeout), 3)
    Assert.assertEquals(sourceOutput2.nextValue(this.timeout), 4)
  }

  @Test
  fun `Then effect should enforce ordering`() {
    /// Setup
    val finalOutput = just<State, Int>(1)
      .delay(500)
      .then(just<State, Int>(2).map { it }) { a, b -> "$a$b" }
      .delay(1000)
      .invoke(this, State()) { }

    /// When && Then
    Assert.assertEquals(finalOutput.nextValue(this.timeout), "12")
  }

  @Test
  fun `Take latest should work in real life scenarios`() {
    /// Setup
    data class Action(val query: String) : Redux.IAction
    val finalValues = Collections.synchronizedList(arrayListOf<Any>())

    fun CoroutineScope.searchMusicStore(q: String) = this.async {
      val url = "https://itunes.apple.com/search?term=$q&limit=5&media=music"
      val result = URL(url).readText().replace("\n", "")
      val result1 = "Input: $q, Result: $result"
      println("EH")
      result1
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

    /// When
    for (i in 0 until 20) { sourceOutput.onAction(Action("$i")) }

    runBlocking {
      withTimeoutOrNull(this@SagaEffectTest.timeout) {
        while (finalValues.size != 1) { delay(100) }; Unit
      }

      /// Then
      Assert.assertEquals(finalValues.size, 1)
    }
  }
}
