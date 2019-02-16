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
/** Represents a job that does some asynchronous work and can be resolved synchronously. */
interface IAsyncJob {
  /** Wait until some asynchronous action finishes. */
  fun await(): Any
}

/** Represents an empty [IAsyncJob] that does not do anything. */
object EmptyJob : IAsyncJob {
  override fun await() = Unit
}

/** Represents an [IAsyncJob] that returns some [value] on [await]. */
class JustJob(private val value: Any) : IAsyncJob {
  override fun await() = this.value
}

/**
 * Represents an [IAsyncJob] that handles [Job]. It waits for [job] to resolve synchronously with
 * a [CountDownLatch].
 * @param job The [Job] to be resolved.
 */
class CoroutineJob(private val job: Deferred<Any>) : IAsyncJob {
  override fun await(): Any {
    return runBlocking { this@CoroutineJob.job.await() }
  }
}
