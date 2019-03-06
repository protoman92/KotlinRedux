/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.core

import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Assert.assertThrows
import org.junit.Test
import java.util.Random

/** Created by haipham on 2019/02/17 */
class AsyncJobTest {
  @Test
  fun `Dispatching with async middleware should work correctly`() {
    // Setup
    class Action(val value: Int) : IReduxAction

    val store = applyMiddlewares<Int>(
      AsyncMiddleware.create(),
      AsyncMiddleware.create(),
      AsyncMiddleware.create()
    )(FinalStore(-1) { s, a -> when (a) {
      is Action -> a.value
      else -> s
    } })

    val rand = Random()

    // When
    val iterations = 1000

    val jobs = (0 until iterations).map {
      GlobalScope.launch(Dispatchers.IO, start = CoroutineStart.LAZY) {
        store.dispatch(Action(rand.nextInt(1000))).await()
      }
    }

    runBlocking {
      jobs.forEach { it.join() }

      // Then
      assertNotEquals(store.lastState(), -1)
    }
  }

  @Test
  fun `Batch job await with timeout should throw error on time out`() {
    // Setup
    val iteration = 10000
    val context = Dispatchers.IO

    val childJobs = (0 until iteration)
      .map { i -> GlobalScope.async(context, start = CoroutineStart.LAZY) { delay(800); i } }
      .map { CoroutineJob(context, it) }

    val batchJob = BatchJob(*childJobs.toTypedArray())

    // When && Then
    assertThrows(TimeoutCancellationException::class.java) { batchJob.awaitFor(2000L) }
  }

  @Test
  fun `Batch job await should wait for all jobs to finish and results should be sequential`() {
    // Setup
    val iteration = 100
    val context = Dispatchers.IO

    val childJobs = (0 until iteration)
      .map { i -> GlobalScope.async(context, start = CoroutineStart.LAZY) { i } }
      .map { CoroutineJob(context, it) }

    val batchJob = BatchJob(*childJobs.toTypedArray())

    // When
    val results = batchJob.await()

    // Then
    assertEquals(results.sorted(), (0 until iteration).toList())
  }
}
