/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.saga.common

/** Created by viethai.pham on 2019/02/14/2 */
/**
 * [SagaEffect] whose [ISagaOutput] applies a debouncing effect on the resulting emission.
 * @param R The result emission type.
 * @param source The source [SagaEffect] instance.
 * @param millis The time to debounce by, in milliseconds.
 */
internal class DebounceEffect<R>(
  private val source: SagaEffect<R>,
  private val millis: Long
) : SagaEffect<R>() where R : Any {
  override fun invoke(p1: SagaInput): ISagaOutput<R> {
    return this.source.invoke(p1).debounce(this.millis)
  }
}
