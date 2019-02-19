/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.core

import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Job
import kotlinx.coroutines.runBlocking
import java.util.concurrent.CountDownLatch

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
}

/** Represents an empty [IAsyncJob] that does not do anything. */
object EmptyJob : IAsyncJob<Unit> {
  override fun await() = Unit
}

/**
 * Represents an [IAsyncJob] that returns some [value] on [await].
 * @param T The return type of [await].
 * @param value A [T] instance.
 */
class JustJob<T>(private val value: T) : IAsyncJob<T> where T : Any {
  override fun await() = this.value
}

/**
 * Represents an [IAsyncJob] that handles [Job]. It waits for [job] to resolve synchronously with
 * a [CountDownLatch].
 * @param T The return type of [await].
 * @param job The [Job] to be resolved.
 */
class CoroutineJob<T>(private val job: Deferred<T>) : IAsyncJob<T> where T : Any {
  override fun await(): T {
    return runBlocking { this@CoroutineJob.job.await() }
  }
}
