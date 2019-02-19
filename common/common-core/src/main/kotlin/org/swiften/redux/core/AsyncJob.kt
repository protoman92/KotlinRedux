/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.core

import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Job
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withTimeout

/** Created by haipham on 2019/02/16 */
/**
 * Represents a job that does some asynchronous work and can be resolved synchronously.
 * @param T The return type of [await].
 */
interface IAsyncJob<T> where T : Any {
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

/** Represents an empty [IAsyncJob] that does not do anything. */
object EmptyJob : IAsyncJob<Unit> {
  override fun await() = Unit
  override fun await(defaultValue: Unit) = this.await()
  override fun awaitFor(timeoutMillis: Long) = this.await()
}

/**
 * Represents an [IAsyncJob] that returns some [value] on [await].
 * @param T The return type of [await].
 * @param value A [T] instance.
 */
class JustJob<T>(private val value: T) : IAsyncJob<T> where T : Any {
  override fun await() = this.value
  override fun await(defaultValue: T) = this.value
  override fun awaitFor(timeoutMillis: Long) = this.value
}

/**
 * Represents an [IAsyncJob] that handles [Job]. It waits for [job] to resolve synchronously with
 * [runBlocking]. If [awaitFor] is used, make sure [job] is cooperative with cancellation.
 * @param T The return type of [await].
 * @param job The [Job] to be resolved.
 */
class CoroutineJob<T>(private val job: Deferred<T>) : IAsyncJob<T> where T : Any {
  override fun await() = runBlocking { this@CoroutineJob.job.await() }
  override fun await(defaultValue: T) = try { this.await() } catch (e: Throwable) { defaultValue }

  @Throws(TimeoutCancellationException::class)
  override fun awaitFor(timeoutMillis: Long): T {
    return runBlocking {
      withTimeout(timeoutMillis) { this@CoroutineJob.await() }
    }
  }
}
