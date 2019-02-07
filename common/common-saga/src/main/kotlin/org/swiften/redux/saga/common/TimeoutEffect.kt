/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.saga.common

/** Created by haipham on 2019/01/14 */
/**
 * [ISagaEffect] whose [ISagaOutput] can time out if no value is emitted within [millis].
 * @param R The result emission type.
 * @param source The source [ISagaEffect].
 * @param millis The timeout time in milliseconds.
 */
internal class TimeoutEffect<R>(
  private val source: ISagaEffect<R>,
  private val millis: Long
) : SagaEffect<R>() where R : Any {
  override fun invoke(p1: SagaInput) = this.source.invoke(p1).timeout(this.millis)
}
