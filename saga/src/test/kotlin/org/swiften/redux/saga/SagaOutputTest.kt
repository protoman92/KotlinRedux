/*
 * Copyright (c) haipham 2018. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.saga

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.channels.produce
import org.swiften.redux.core.Redux
import org.testng.Assert
import org.testng.annotations.Test
import java.util.*

/**
 * Created by haipham on 2018/12/23.
 */
private typealias Output<T> = ReduxSaga.Output<T>

class SagaOutputTest : CoroutineScope {
  override val coroutineContext get() = Dispatchers.IO

  private val timeout: Long = 100000

  @ObsoleteCoroutinesApi
  @ExperimentalCoroutinesApi
  fun test_flatMapVariants_shouldEmitCorrectValues(
    fn: Output<Int>.(suspend (Int) -> Output<String>) -> Output<String>,
    actualValues: List<String>
  ) {
    /// Setup
    val sourceCh = Channel<Int>(0)

    val dispatcher = object : Redux.IDispatcher {
      override fun invoke(action: Redux.IAction) {}
    }

    val source = ReduxSaga.Output(this, sourceCh, dispatcher)
    val finalValues = Collections.synchronizedList(arrayListOf<String>())

    val finalOutput = fn(source) {
      val resultCh = this.produce {
        delay(500); this.send("${it}1")
        delay(500); this.send("${it}2")
        delay(500); this.send("${it}3")
      }

      ReduxSaga.Output(this, resultCh, dispatcher)
    }

    /// When
    this.launch { sourceCh.send(0); sourceCh.send(1); sourceCh.send(2) }
    this.launch { finalOutput.channel.consumeEach { finalValues.add(it) } }

    runBlocking {
      withTimeoutOrNull(this@SagaOutputTest.timeout) {
        while (finalValues.sorted() != actualValues.sorted()) { delay(1000) }; 1
      }

      finalOutput.terminate()

      /// Then
      Assert.assertEquals(finalValues.sorted(), actualValues.sorted())
    }
  }

  @Test
  @ObsoleteCoroutinesApi
  @ExperimentalCoroutinesApi
  fun `Output flatMap should emit all values`() {
    test_flatMapVariants_shouldEmitCorrectValues(
      { this.flatMap(it) },
      arrayListOf("01", "11", "21", "02", "12", "22", "03", "13", "23")
    )
  }

  @Test
  @ObsoleteCoroutinesApi
  @ExperimentalCoroutinesApi
  fun `Output switchMap should emit values from latest Output`() {
    test_flatMapVariants_shouldEmitCorrectValues(
      { this.switchMap(it) },
      arrayListOf("21", "22", "23")
    )
  }

  @Test
  @ObsoleteCoroutinesApi
  @ExperimentalCoroutinesApi
  fun `Output debounce should throttle emissions`() {
    /// Setup
    val rand = Random()
    val sourceCh = Channel<Int>()

    val source = ReduxSaga.Output(this, sourceCh, object : Redux.IDispatcher {
      override fun invoke(action: Redux.IAction) {}
    })

    val finalOutput = source.debounce(100)
    val finalValues = Collections.synchronizedList(arrayListOf<Int>())
    this.launch { finalOutput.channel.consumeEach { finalValues.add(it) } }
    val validEmissions = (0 until 100).map { rand.nextBoolean() }

    val actualValues = validEmissions
      .mapIndexed { index, b -> index to b }
      .filter { (_, b) -> b }
      .map { (index, _) -> index }

    /// When
    this.launch {
      validEmissions.forEachIndexed { index, b ->
        if (b) {
          sourceCh.send(index)
          delay((150 until 300).random().toLong())
        } else {
          delay((10 until 50).random().toLong())
        }
      }
    }

    runBlocking {
      withTimeoutOrNull(this@SagaOutputTest.timeout) {
        while (finalValues != actualValues) { delay(1000) }; 1
      }

      finalOutput.terminate()

      /// Then
      Assert.assertEquals(finalValues, actualValues)
    }
  }
}
