/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.saga

/** Created by haipham on 2019/01/14 */
/**
 * [ISagaEffect] whose [ISagaOutput] can time out if no value is emitted within
 * [millis]
 */
internal class TimeoutEffect<R>(
  private val source: ISagaEffect<R>,
  private val millis: Long
) : SagaEffect<R>() {
  override fun invoke(p1: SagaInput) = this.source.invoke(p1).timeout(this.millis)
}

/** Invoke a [TimeoutEffect] on [this] */
fun <R> SagaEffect<R>.timeout(millis: Long) = this.transform(CommonEffects.timeout(millis))
