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

/**
 * Created by haipham on 2018/12/23.
 */
private typealias Output<T> = ReduxSaga.Output<T>

class SagaOutputTest: CoroutineScope {
  override val coroutineContext = Dispatchers.Default

  private val timeout: Long = 10000

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
    val finalValues = arrayListOf<String>()

    val finalOutput = fn(source) {
      val resultCh = this@SagaOutputTest.produce {
        delay(500); this.send("${it}1")
        delay(500); this.send("${it}2")
        delay(500); this.send("${it}3")
      }

      ReduxSaga.Output(this, resultCh, dispatcher)
    }

    /// When
    this.launch {
      sourceCh.send(0)
      sourceCh.send(1)
      sourceCh.send(2)
    }

    this.launch { finalOutput.channel.consumeEach { finalValues.add(it) } }

    runBlocking(this.coroutineContext) {
      withTimeoutOrNull(this@SagaOutputTest.timeout) {
        while (finalValues.sorted() != actualValues.sorted()) { delay(100) }; 1
      }

      finalOutput.terminate()
      this@SagaOutputTest.coroutineContext.cancel()

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
}
