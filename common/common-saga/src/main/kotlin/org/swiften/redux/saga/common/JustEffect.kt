/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.saga.common

import io.reactivex.Flowable.just

/** Created by haipham on 2018/12/24 */
/**
 * [SagaEffect] whose [SagaOutput] simply emits [value].
 * @param R The result emission type.
 * @param value The [R] value to be emitted.
 */
internal class JustEffect<R>(private val value: R) : SingleSagaEffect<R>() where R : Any {
  override fun invoke(p1: SagaInput): ISagaOutput<R> {
    return SagaOutput(p1.scope, p1.monitor, just(this.value))
  }
}
