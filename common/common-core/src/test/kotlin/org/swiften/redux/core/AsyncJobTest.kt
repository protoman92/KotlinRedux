/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.core

import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withTimeout
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Test
import java.util.concurrent.Executors
import java.util.concurrent.TimeoutException
import kotlin.coroutines.CoroutineContext

/** Created by haipham on 2019/02/17 */
@OptIn(DelicateCoroutinesApi::class)
class AsyncJobTest {
  /**
   * Represents an [IAwaitable] that handles [Deferred]. It waits for [job] to resolve synchronously
   * with [runBlocking]. If [awaitFor] is used, make sure [job] is cooperative with cancellation.
   * @param T The return type of [await].
   * @param context The [CoroutineContext] to perform waiting on.
   * @param job The [Deferred] to be resolved.
   */
  data class CoroutineAwaitable<T>(
    private val context: CoroutineContext,
    private val job: Deferred<T>
  ) : IAwaitable<T> where T : Any {
    override fun await(): T {
      return runBlocking(this.context) { this@CoroutineAwaitable.job.await() }
    }

    override fun await(defaultValue: T) = try { this.await() } catch (e: Throwable) { defaultValue }

    @Throws(TimeoutCancellationException::class)
    override fun awaitFor(timeoutMillis: Long): T {
      return runBlocking(this.context) {
        withTimeout(timeoutMillis) { this@CoroutineAwaitable.await() }
      }
    }
  }

  @Test
  fun `Batch awaitable should fail if awaiting with time out on the same thread`() {
    // Setup
    val awaitable = JustAwaitable(value = 0)

    // When
    val batchAwaitable = BatchAwaitable(executor = { runnable ->
      runnable.run()
    }, awaitable)

    // Then
    assertThrows(BatchAwaitable.DeadlockException::class.java) {
      batchAwaitable.awaitFor(0L)
    }
  }

  @Test
  fun `Batch awaitable should handle child await errors if awaiting with time out`() {
    // Setup
    val exception = ClassNotFoundException()
    val executors = Executors.newFixedThreadPool(1)

    val awaitable = object : IAwaitable<String> {
      override fun await(): String {
        throw exception
      }

      override fun await(defaultValue: String): String {
        throw exception
      }

      override fun awaitFor(timeoutMillis: Long): String {
        throw exception
      }
    }

    // When
    val batchAwaitable = BatchAwaitable(executor = { runnable ->
      executors.submit(runnable)
    }, awaitable)

    // Then
    assertThrows(ClassNotFoundException::class.java) {
      batchAwaitable.awaitFor(1000L)
    }
  }

  @Test
  fun `Batch awaitable with timeout should throw error on time out`() {
    // Setup
    val iteration = 10000
    val context = Dispatchers.IO

    val awaitables = (0 until iteration)
      .map { i ->
        GlobalScope.async(context, start = CoroutineStart.LAZY) { delay(800); i }
      }
      .map { CoroutineAwaitable(context = context, job = it) }

    val executors = Executors.newFixedThreadPool(1)

    val batchAwaitable = BatchAwaitable(executor = { runnable ->
      executors.submit(runnable)
    }, *awaitables.toTypedArray())

    // When && Then
    assertThrows(TimeoutException::class.java) { batchAwaitable.awaitFor(2000L) }
  }

  @Test
  fun `Batch awaitable should wait for all awaitables to finish and results should be sequential`() {
    // Setup
    val iteration = 100
    val context = Dispatchers.IO

    val awaitables = (0 until iteration)
      .map { i -> GlobalScope.async(context, start = CoroutineStart.LAZY) { i } }
      .map { CoroutineAwaitable(context = context, job = it) }

    val executors = Executors.newFixedThreadPool(1)

    val batchAwaitable = BatchAwaitable(executor = { runnable ->
      executors.submit(runnable)
    }, *awaitables.toTypedArray())

    // When
    val results = batchAwaitable.await()

    // Then
    assertEquals(results.sorted(), (0 until iteration).toList())
  }
}
