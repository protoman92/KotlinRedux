/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.saga.cr

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.async
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withTimeoutOrNull
import org.testng.annotations.AfterMethod
import org.testng.annotations.BeforeMethod
import org.testng.annotations.Test
import java.util.Collections
import java.util.Random

/** Created by haipham on 2018/12/23 */
class SagaOutputTest : CoroutineScope {
  override val coroutineContext get() = this.job

  private lateinit var job: Job
  private val timeout: Long = 100000

  @BeforeMethod
  fun beforeMethod() { this.job = SupervisorJob() }

  @AfterMethod
  fun afterMethod() { this.coroutineContext.cancel() }

  @ObsoleteCoroutinesApi
  @ExperimentalCoroutinesApi
  fun test_flatMapVariants_shouldEmitCorrectValues(
    fn: ReduxSagaOutput<Int>.((Int) -> ReduxSagaOutput<String>) -> ReduxSagaOutput<String>,
    actualValues: List<String>
  ) {
    // Setup
    val sourceCh = Channel<Int>()
    val sourceOutput = ReduxSagaOutput(scope = this, channel = sourceCh) { }
    val finalValues = Collections.synchronizedList(arrayListOf<String>())

    val finalOutput = fn(sourceOutput) { value ->
      ReduxSagaOutput(scope = this, channel = this.produce {
        delay(500); this.send("${value}1")
        delay(500); this.send("${value}2")
        delay(500); this.send("${value}3")
      }) { }
    }

    finalOutput.subscribe({ finalValues.add(it) })

    // When
    this.launch { sourceCh.send(0); sourceCh.send(1); sourceCh.send(2) }

    runBlocking {
      withTimeoutOrNull(this@SagaOutputTest.timeout) {
        while (finalValues.sorted() != actualValues.sorted()) { delay(1000) }; 1
      }

      finalOutput.dispose()

      // Then
      assertEquals(finalValues.sorted(), actualValues.sorted())
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
      { this.switchMap(transform = it) },
      arrayListOf("21", "22", "23")
    )
  }

  @ObsoleteCoroutinesApi
  @ExperimentalCoroutinesApi
  fun test_flatMapVariants_shouldTerminateOnCancel(
    fn: ReduxSagaOutput<Int>.((Int) -> ReduxSagaOutput<Int>) -> ReduxSagaOutput<Int>
  ) {
    // Setup
    val sourceCh = Channel<Int>()
    val sourceOutput = ReduxSagaOutput(scope = this, channel = sourceCh) { }
    val finalValues = Collections.synchronizedList(arrayListOf<Int>())

    val finalOutput = fn(sourceOutput) {
      ReduxSagaOutput(scope = this, channel = this.produce {
        delay(500); this.send(1)
        delay(500); this.send(2)
        delay(500); this.send(3)
      }) { }
    }

    finalOutput.subscribe({ finalValues.add(it) })

    // When
    this.coroutineContext.cancel()

    this.launch {
      sourceCh.send(0)
      sourceCh.send(1)
      sourceCh.send(2)
    }

    runBlocking {
      delay(1000)

      // Then
      assertEquals(finalValues.size, 0)
    }
  }

  @Test
  @ObsoleteCoroutinesApi
  @ExperimentalCoroutinesApi
  fun `Output flatMap should terminate when scope context cancels`() {
    test_flatMapVariants_shouldTerminateOnCancel { this.flatMap(it) }
  }

  @Test
  @ObsoleteCoroutinesApi
  @ExperimentalCoroutinesApi
  fun `Output switchMap should terminate when scope context cancels`() {
    test_flatMapVariants_shouldTerminateOnCancel { this.switchMap(transform = it) }
  }

  @Test
  @ExperimentalCoroutinesApi
  fun `Output mapAsync should await deferred values`() {
    // Setup
    val sourceCh = this.produce {
      this.send(0)
      this.send(1)
      this.send(2)
      this.send(3)
    }

    val finalValues = Collections.synchronizedList(arrayListOf<Int>())

    ReduxSagaOutput("", this, sourceCh) { }
      .mapAsync { this.async { delay(1000); it } }
      .subscribe({ finalValues.add(it) })

    // When && Then
    runBlocking {
      withTimeoutOrNull(this@SagaOutputTest.timeout) {
        while (finalValues.sorted() != arrayListOf(0, 1, 2, 3)) { }; Unit
      }

      assertEquals(finalValues.sorted(), arrayListOf(0, 1, 2, 3))
    }
  }

  @Test
  @ObsoleteCoroutinesApi
  @ExperimentalCoroutinesApi
  fun `Output debounce should throttle emissions`() {
    // Setup
    val rand = Random()
    val sourceCh = Channel<Int>()
    val sourceOutput = ReduxSagaOutput(scope = this, channel = sourceCh) { }
    val finalOutput = sourceOutput.debounce(100)
    val finalValues = Collections.synchronizedList(arrayListOf<Int>())
    val validEmissions = (0 until 100).map { rand.nextBoolean() }

    val actualValues = validEmissions
      .mapIndexed { index, b -> index to b }
      .filter { (_, b) -> b }
      .map { (index, _) -> index }

    finalOutput.subscribe({ finalValues.add(it) })

    // When
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

      // Then
      assertEquals(finalValues, actualValues)
    }
  }

  @Test
  @ObsoleteCoroutinesApi
  @ExperimentalCoroutinesApi
  fun `Output catch error should handle errors gracefully`() {
    // Setup
    val sourceCh = Channel<Int>()
    val sourceOutput = ReduxSagaOutput(scope = this, channel = sourceCh) { }
    val error = Exception("Oh no!")
    val finalOutput = sourceOutput.map<Int> { throw error }.catchError { 100 }
    val finalValues = Collections.synchronizedList(arrayListOf<Int>())
    finalOutput.subscribe({ finalValues.add(it) })

    // When
    this.launch { sourceCh.send(0); sourceCh.send(1); sourceCh.send(2) }

    runBlocking {
      delay(1000)

      // Then
      assertEquals(finalValues, arrayListOf(100))
    }
  }

  @ObsoleteCoroutinesApi
  @ExperimentalCoroutinesApi
  fun test_terminatingOutput_shouldWorkCorrectly(
    dispose: (CoroutineScope, ReduxSagaOutput<Int>) -> Unit,
    assert: (ReduxSagaOutput<Int>) -> Unit
  ) {
    // Setup
    val scope = object : CoroutineScope {
      override val coroutineContext = SupervisorJob()
    }

    val flatMapChannel1 = Channel<Int>()
    val flatMapChannel2 = Channel<Int>()
    val flatMapOutput1 = ReduxSagaOutput("FlatMap1", scope, flatMapChannel1) { }
    val flatMapOutput2 = ReduxSagaOutput("FlatMap2", scope, flatMapChannel2) { }

    val sourceCh = Channel<Int>()
    val output1 = ReduxSagaOutput(this, scope, sourceCh) { }
    val output2 = output1.map { it }
    val output3 = output2.switchMap { flatMapOutput1 }
    val output4 = output3.flatMap { flatMapOutput2 }
    val output5 = output4.debounce(500)
    val output6 = output5.catchError { 100 }

    this.launch { flatMapChannel1.send(1) }
    this.launch { flatMapChannel2.send(2) }
    this.launch { sourceCh.send(50) }

    runBlocking {
      // When
      delay(1000)
      dispose(scope, output6)
      delay(1000)

      // Then
      arrayListOf(
        output1,
        output2,
        output3,
        output4,
        output5,
        output6
      ).forEach { assert(it) }

      assertTrue(flatMapChannel1.isClosedForReceive)
      assertTrue(flatMapChannel2.isClosedForReceive)
    }
  }

  @Test
  @ObsoleteCoroutinesApi
  @ExperimentalCoroutinesApi
  fun `Output should be terminated correctly on scope context cancel`() {
    test_terminatingOutput_shouldWorkCorrectly(
      { a, _ -> a.coroutineContext.cancel() },
      { assertTrue(it.channel.isClosedForReceive) }
    )
  }

  @Test
  @ObsoleteCoroutinesApi
  @ExperimentalCoroutinesApi
  fun `Output should be terminated correctly on dispose`() {
    test_terminatingOutput_shouldWorkCorrectly(
      { _, b -> b.dispose() },
      { assertTrue(it.channel.isClosedForReceive) }
    )
  }
}
