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
/**
 * Represents a job for [IActionDispatcher] that can be resolved to ensure [IReduxStore.dispatch]
 * finishes mutating internal state.
 */
interface IDispatchJob {
  /** Wait until some asynchronous action finishes. */
  fun blockingWait()
}

/** Represents an empty [IDispatchJob] that does not do anything. */
object EmptyDispatchJob : IDispatchJob {
  override fun blockingWait() {}
}

/**
 * Represents an [IDispatchJob] that handles [Job]. It waits for [job] to resolve synchronously
 * with a [CountDownLatch].
 * @param context A [CoroutineContext] instance.
 * @param job The [Job] to be resolved.
 */
class CoroutineDispatchJob(
  private val context: CoroutineContext,
  private val job: Job
) : IDispatchJob {
  constructor(
    context: CoroutineContext,
    performer: suspend CoroutineContext.() -> Unit
  ) : this(context, GlobalScope.launch(context) { performer(context) })

  override fun blockingWait() {
    val latch = CountDownLatch(1)

    GlobalScope.launch(this.context) {
      this@CoroutineDispatchJob.job.join()
      latch.countDown()
    }

    latch.await()
  }
}
