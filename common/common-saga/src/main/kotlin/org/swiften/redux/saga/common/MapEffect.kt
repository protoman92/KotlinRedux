/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.saga.common

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import org.swiften.redux.core.Boxed

/** Created by haipham on 2018/12/23 */
/**
 * [ISagaEffect] whose output performs some asynchronous work with [transformer], based on the
 * emissions from another [source], and then emit the result.
 * @param P The source emission type.
 * @param R The result emission type.
 * @param source The source [ISagaEffect].
 * @param transformer Function that transforms [P] to [R].
 */
internal class MapEffect<P, R>(
  private val source: ISagaEffect<P>,
  private val transformer: (P) -> R
) : SagaEffect<R>() where P : Any, R : Any {
  override fun invoke(input: SagaInput) = this.source.invoke(input).map(this.transformer)
}

/**
 * Similar to [MapEffect], but handles async functions.
 * @param P The source emission type.
 * @param R The result emission type.
 * @param source The source [ISagaEffect].
 * @param transformer Transformation function.
 */
internal class AsyncMapEffect<P, R>(
  private val source: ISagaEffect<P>,
  private val transformer: suspend CoroutineScope.(P) -> Deferred<R>
) : SagaEffect<R>() where P : Any, R : Any {
  override fun invoke(p1: SagaInput) = this.source.invoke(p1).mapAsync(this.transformer)
}

/**
 * Similar to [MapEffect], but handles suspend functions.
 * @param P The source emission type.
 * @param R The result emission type.
 * @param source The source [ISagaEffect].
 * @param transformer Function that transforms [P] to [R].
 */
internal class SuspendMapEffect<P, R>(
  private val source: ISagaEffect<P>,
  private val transformer: suspend CoroutineScope.(P) -> R
) : SagaEffect<R>() where P : Any, R : Any {
  override fun invoke(p1: SagaInput) = this.source.invoke(p1).mapSuspend(this.transformer)
}

/**
 * Unwraps a nullable [R] to [R]. If [R] is null, emit nothing.
 * @param R The result emission type.
 * @param source The source [ISagaEffect] instance.
 */
internal class CompactMapEffect<P, R>(
  private val source: ISagaEffect<P>,
  private val transformer: (P) -> R?
): SagaEffect<R>() where P : Any, R : Any {
  override fun invoke(p1: SagaInput): ISagaOutput<R> {
    return this.source.invoke(p1)
      .map { Boxed(this@CompactMapEffect.transformer(it)) }
      .filter { it.value != null }
      .map { it.value!! }
  }
}
