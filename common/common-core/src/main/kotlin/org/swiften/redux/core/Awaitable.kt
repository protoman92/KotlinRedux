/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.core

import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Job
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.isActive
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withTimeout
import kotlinx.coroutines.yield
import kotlin.coroutines.CoroutineContext

/** Created by haipham on 2019/02/16 */
/**
 * Represents a job that does some asynchronous work and can be resolved synchronously.
 * @param T The return type of [await].
 */
interface IAwaitable<T> where T : Any {
  /**
   * Wait until some asynchronous action finishes.
   * @return A [T] instance.
   */
  fun await(): T

  /**
   * Same as [await], but return a default [T] instance if [await] errors out or is empty.
   * @param defaultValue A [T] instance.
   * @return A [T] instance.
   */
  fun await(defaultValue: T): T

  /**
   * Same as [await], but only up till [timeoutMillis]. Throw a [Throwable] otherwise.
   * @param timeoutMillis The timeout time in milliseconds.
   * @return A [T] instance.
   */
  @Throws(Exception::class)
  fun awaitFor(timeoutMillis: Long): T
}

/** Represents an empty [IAwaitable] that does not do anything. */
object EmptyAwaitable : IAwaitable<Any> {
  override fun await() = Unit
  override fun await(defaultValue: Any) = this.await()
  override fun awaitFor(timeoutMillis: Long) = this.await()
}

/**
 * Represents an [IAwaitable] that returns some [value] on [await].
 * @param T The return type of [await].
 * @param value A [T] instance.
 */
data class JustAwaitable<T>(private val value: T) : IAwaitable<T> where T : Any {
  override fun await() = this.value
  override fun await(defaultValue: T) = this.value
  override fun awaitFor(timeoutMillis: Long) = this.value
}

/**
 * Represents an [IAwaitable] that handles [Job]. It waits for [job] to resolve synchronously with
 * [runBlocking]. If [awaitFor] is used, make sure [job] is cooperative with cancellation.
 * @param T The return type of [await].
 * @param context The [CoroutineContext] to perform waiting on.
 * @param job The [Job] to be resolved.
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
    return runBlocking(this.context) { withTimeout(timeoutMillis) { this@CoroutineAwaitable.await() } }
  }
}

/**
 * Represents an [IAwaitable] that waits for all [IAwaitable] in [jobs] to finish, then return a
 * [Collection] of [jobs] return values.
 * @param jobs A [Collection] of [IAwaitable].
 */
data class BatchAwaitable<T>(private val jobs: Collection<IAwaitable<T>>) : IAwaitable<Collection<T>> where T : Any {
  constructor(vararg jobs: IAwaitable<T>) : this(jobs.toList())

  override fun await(): Collection<T> = this.jobs.map { it.await() }

  override fun await(defaultValue: Collection<T>): Collection<T> {
    return try { this.await() } catch (e: Throwable) { defaultValue }
  }

  @Throws(TimeoutCancellationException::class)
  override fun awaitFor(timeoutMillis: Long): Collection<T> {
    return runBlocking {
      withTimeout(timeoutMillis) {
        val results = arrayListOf<T>()

        for (job in this@BatchAwaitable.jobs) {
          if (this.isActive) {
            val jobResult = job.await()

            if (this.isActive) {
              results.add(jobResult)
              continue
            }
          }

          yield()
        }

        results
      }
    }
  }
}
