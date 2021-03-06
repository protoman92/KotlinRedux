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
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Test

/** Created by haipham on 2019/02/17 */
class AsyncJobTest {
  @Test
  fun `Batch job await with timeout should throw error on time out`() {
    // Setup
    val iteration = 10000
    val context = Dispatchers.IO

    val childJobs = (0 until iteration)
      .map { i -> GlobalScope.async(context, start = CoroutineStart.LAZY) { delay(800); i } }
      .map { CoroutineAwaitable(context, it) }

    val batchJob = BatchAwaitable(*childJobs.toTypedArray())

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
      .map { CoroutineAwaitable(context, it) }

    val batchJob = BatchAwaitable(*childJobs.toTypedArray())

    // When
    val results = batchJob.await()

    // Then
    assertEquals(results.sorted(), (0 until iteration).toList())
  }
}
