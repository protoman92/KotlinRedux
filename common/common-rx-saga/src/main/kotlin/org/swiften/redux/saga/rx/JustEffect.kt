/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.saga.rx

import io.reactivex.Flowable.just
import org.swiften.redux.saga.common.ISagaEffect
import org.swiften.redux.saga.common.SagaEffect
import org.swiften.redux.saga.common.SagaInput

/** Created by haipham on 2018/12/24 */
/**
 * [ISagaEffect] whose [SagaOutput] simply emits [value].
 * @param R The result emission type.
 * @param value The [R] value to be emitted.
 */
internal class JustEffect<R>(private val value: R) : SagaEffect<R>() {
  override fun invoke(p1: SagaInput) = SagaOutput(p1.scope, just(this.value)) {}
}
