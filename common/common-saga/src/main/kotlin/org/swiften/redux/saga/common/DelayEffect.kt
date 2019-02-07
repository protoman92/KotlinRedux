/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.saga.common

/** Created by haipham on 2019/01/07 */
/**
 * [ISagaEffect] whose [ISagaOutput] delays emissions by [millis].
 * @param R The result emission type.
 * @param source The source [ISagaEffect].
 * @param millis Delay time in milliseconds.
 */
internal class DelayEffect<R>(
  private val source: ISagaEffect<R>,
  private val millis: Long
) : SagaEffect<R>() where R : Any {
  override fun invoke(p1: SagaInput) = this.source.invoke(p1).delay(this.millis)
}
