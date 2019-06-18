/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.saga.common

import io.reactivex.Flowable
import java.util.concurrent.TimeUnit

/** Created by haipham on 2019/01/07 */
/**
 * [ISagaEffect] whose [ISagaOutput] delays emissions by [millis].
 * @param R The result emission type.
 * @param millis Delay time in milliseconds.
 */
class DelayEffect(private val millis: Long) : SagaEffect<Any>() {
  override fun invoke(p1: SagaInput): ISagaOutput<Any> {
    return SagaOutput(p1.monitor, Flowable.timer(this.millis, TimeUnit.MILLISECONDS).cast(Any::class.java))
  }
}
