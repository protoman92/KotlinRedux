/*
 * Copyright (c) haipham 2018. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.saga

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.channels.produce
import org.testng.Assert
import org.testng.annotations.AfterMethod
import org.testng.annotations.Test
import java.util.*
import kotlin.coroutines.CoroutineContext

/**
 * Created by haipham on 2018/12/23.
 */
private typealias Output<T> = ReduxSaga.Output<T>

class SagaOutputTest : CoroutineScope {
  override val coroutineContext = Dispatchers.IO

  private val timeout: Long = 100000

  @AfterMethod
  fun afterMethod() { this.coroutineContext.cancel() }

  @ObsoleteCoroutinesApi
  @ExperimentalCoroutinesApi
  fun test_flatMapVariants_shouldEmitCorrectValues(
    fn: Output<Int>.(suspend CoroutineScope.(Int) -> Output<String>) -> Output<String>,
    actualValues: List<String>
  ) {
    /// Setup
    val sourceCh = Channel<Int>()
    val sourceOutput = Output(scope = this, channel = sourceCh) { }
    val finalValues = Collections.synchronizedList(arrayListOf<String>())

    val finalOutput = fn(sourceOutput) { value ->
      Output(scope = this, channel = this.produce {
        delay(500); this.send("${value}1")
        delay(500); this.send("${value}2")
        delay(500); this.send("${value}3")
      }) { }
    }

    this.launch { finalOutput.channel.consumeEach { finalValues.add(it) } }

    /// When
    this.launch { sourceCh.send(0); sourceCh.send(1); sourceCh.send(2) }

    runBlocking {
      withTimeoutOrNull(this@SagaOutputTest.timeout) {
        while (finalValues.sorted() != actualValues.sorted()) { delay(1000) }; 1
      }

      finalOutput.dispose()

      /// Then
      Assert.assertEquals(finalValues.sorted(), actualValues.sorted())
    }
  }

  @Test
  @ObsoleteCoroutinesApi
  @ExperimentalCoroutinesApi
  fun `Output flatMap should emit all values`() {
    test_flatMapVariants_shouldEmitCorrectValues(
      { this.flatMap(transform = it) },
      arrayListOf("01", "11", "21", "02", "12", "22", "03", "13", "23")
    )
  }

  @Test
  @ObsoleteCoroutinesApi
  @ExperimentalCoroutinesApi
  fun `Output switchMap should emit values from latest Output`() {
    test_flatMapVariants_shouldEmitCorrectValues(
      { this.switchMap(transform = it) },
      arrayListOf("21", "22", "23")
    )
  }

  @ObsoleteCoroutinesApi
  @ExperimentalCoroutinesApi
  fun test_flatMapVariants_shouldTerminateOnCancel(
    fn: Output<Int>.(suspend CoroutineScope.(Int) -> Output<Int>) -> Output<Int>
  ) {
    /// Setup
    val job = SupervisorJob()

    val scope = object : CoroutineScope {
      override val coroutineContext: CoroutineContext =
        this@SagaOutputTest.coroutineContext + job
    }

    val sourceCh = Channel<Int>()
    val sourceOutput = Output(scope = scope, channel = sourceCh) { }
    val finalValues = Collections.synchronizedList(arrayListOf<Int>())

    val finalOutput = fn(sourceOutput) {
      Output(scope = this, channel = this.produce {
        delay(500); this.send(1)
        delay(500); this.send(2)
        delay(500); this.send(3)
      }) { }
    }

    this.launch(job) { finalOutput.channel.consumeEach { finalValues.add(it) } }

    /// When
    job.cancel()

    this.launch {
      sourceCh.send(0)
      sourceCh.send(1)
      sourceCh.send(2)
    }

    runBlocking {
      delay(1000)

      /// Then
      Assert.assertEquals(finalValues.size, 0)
    }
  }

  @Test
  @ObsoleteCoroutinesApi
  @ExperimentalCoroutinesApi
  fun `Output flatMap should terminate when scope context cancels`() {
    test_flatMapVariants_shouldTerminateOnCancel { this.flatMap(transform = it) }
  }

  @Test
  @ObsoleteCoroutinesApi
  @ExperimentalCoroutinesApi
  fun `Output switchMap should terminate when scope context cancels`() {
    test_flatMapVariants_shouldTerminateOnCancel { this.switchMap(transform = it) }
  }

  @Test
  @ObsoleteCoroutinesApi
  @ExperimentalCoroutinesApi
  fun `Output debounce should throttle emissions`() {
    /// Setup
    val rand = Random()
    val sourceCh = Channel<Int>()
    val sourceOutput = Output(scope = this, channel = sourceCh) { }
    val finalOutput = sourceOutput.debounce(100)
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

      /// Then
      Assert.assertEquals(finalValues, actualValues)
    }
  }

  @Test
  @ObsoleteCoroutinesApi
  @ExperimentalCoroutinesApi
  fun `Output catch error should handle errors gracefully`() {
    /// Setup
    val sourceCh = Channel<Int>()
    val sourceOutput = Output(scope = this, channel = sourceCh) { }
    val error = Exception("Oh no!")
    val finalOutput = sourceOutput.map<Int> { throw error }.catchError { 100 }
    val finalValues = Collections.synchronizedList(arrayListOf<Int>())
    this.launch { finalOutput.channel.consumeEach { finalValues.add(it) } }

    /// When
    this.launch { sourceCh.send(0); sourceCh.send(1); sourceCh.send(2) }

    runBlocking {
      delay(1000)

      /// Then
      Assert.assertEquals(finalValues, arrayListOf(100))
    }
  }

  @ObsoleteCoroutinesApi
  @ExperimentalCoroutinesApi
  fun test_terminatingOutput_shouldWorkCorrectly(
    dispose: (CoroutineScope, Output<Int>) -> Unit,
    assert: (Output<Int>) -> Unit
  ) {
    /// Setup
    val scope = object : CoroutineScope {
      override val coroutineContext = SupervisorJob()
    }

    val flatMapChannel1 = Channel<Int>()
    val flatMapChannel2 = Channel<Int>()
    val flatMapOutput1 = Output("FlatMap1", scope, flatMapChannel1) { }
    val flatMapOutput2 = Output("FlatMap2", scope, flatMapChannel2) { }

    val sourceCh = Channel<Int>()
    val output1 = Output(this, scope, sourceCh) { }
    val output2 = output1.map { it }
    val output3 = output2.switchMap { flatMapOutput1 }
    val output4 = output3.flatMap { flatMapOutput2 }
    val output5 = output4.debounce(500)
    val output6 = output5.catchError { 100 }

    this.launch { flatMapChannel1.send(1) }
    this.launch { flatMapChannel2.send(2) }
    this.launch { sourceCh.send(50) }
    this.launch { output6.channel.consumeEach { println("Received $it") } }

    runBlocking {
      /// When
      delay(1000)
      dispose(scope, output6)
      delay(1000)

      /// Then
      arrayListOf(
        output1,
        output2,
        output3,
        output4,
        output5,
        output6
      ).forEach { assert(it) }

      println("${flatMapChannel1.isClosedForSend} ${flatMapChannel2.isClosedForSend}")
      Assert.assertTrue(flatMapChannel1.isClosedForReceive)
      Assert.assertTrue(flatMapChannel2.isClosedForReceive)
    }
  }

  @Test
  @ObsoleteCoroutinesApi
  @ExperimentalCoroutinesApi
  fun `Output should be terminated correctly on scope context cancel`() {
    test_terminatingOutput_shouldWorkCorrectly(
      { a, _ -> a.coroutineContext.cancel() },
      { Assert.assertTrue(it.channel.isClosedForReceive) }
    )
  }

  @Test
  @ObsoleteCoroutinesApi
  @ExperimentalCoroutinesApi
  fun `Output should be terminated correctly on dispose`() {
    test_terminatingOutput_shouldWorkCorrectly(
      { _, b -> b.dispose() },
      { Assert.assertTrue(it.channel.isClosedForReceive) }
    )
  }
}
