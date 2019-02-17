/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.saga.rx

import io.reactivex.Flowable.just
import org.swiften.redux.core.EmptyJob
import org.swiften.redux.saga.common.SagaEffect
import org.swiften.redux.saga.common.SagaInput
import org.swiften.redux.saga.common.SingleSagaEffect

/** Created by haipham on 2018/12/24 */
/**
 * [SagaEffect] whose [SagaOutput] simply emits [value].
 * @param R The result emission type.
 * @param value The [R] value to be emitted.
 */
internal class JustEffect<R>(private val value: R) : SingleSagaEffect<R>() where R : Any {
  override fun invoke(p1: SagaInput) = SagaOutput(p1.scope, just(this.value)) { EmptyJob }
}
