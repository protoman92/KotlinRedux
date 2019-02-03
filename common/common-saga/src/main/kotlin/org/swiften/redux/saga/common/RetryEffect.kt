/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.saga.common

/** Created by haipham on 2019/01/13 */
/**
 * [ISagaEffect] whose [ISagaOutput] retries [times] if a [Throwable] is encountered.
 * @param R The result emission type.
 * @param source The source [ISagaEffect].
 * @param times The number of times to retry.
 */
internal class RetryEffect<R>(
  private val source: ISagaEffect<R>,
  private val times: Long
) : SagaEffect<R>() {
  override fun invoke(p1: SagaInput) = this.source.invoke(p1).retry(this.times)
}
