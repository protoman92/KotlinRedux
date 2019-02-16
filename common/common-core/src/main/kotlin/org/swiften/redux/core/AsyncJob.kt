/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.core

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.util.concurrent.CountDownLatch
import kotlin.coroutines.CoroutineContext

/** Created by haipham on 2019/02/16 */
/** Represents a job that does some asynchronous work and can be resolved synchronously. */
interface IAsyncJob {
  /** Wait until some asynchronous action finishes. */
  fun await()
}

/** Represents an empty [IAsyncJob] that does not do anything. */
object EmptyJob : IAsyncJob {
  override fun await() {}
}

/**
 * Represents an [IAsyncJob] that handles [Job]. It waits for [job] to resolve synchronously with
 * a [CountDownLatch].
 * @param context A [CoroutineContext] instance.
 * @param job The [Job] to be resolved.
 */
class CoroutineJob(
  private val context: CoroutineContext,
  private val job: Job
) : IAsyncJob {
  constructor(
    context: CoroutineContext,
    performer: suspend CoroutineContext.() -> Unit
  ) : this(context, GlobalScope.launch(context) { performer(context) })

  override fun await() {
    val latch = CountDownLatch(1)

    GlobalScope.launch(this.context) {
      this@CoroutineJob.job.join()
      latch.countDown()
    }

    latch.await()
  }
}
