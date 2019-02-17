/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.saga.rx

import kotlinx.coroutines.CoroutineScope
import org.swiften.redux.saga.common.ISagaOutput
import org.swiften.redux.saga.common.SagaEffect
import org.swiften.redux.saga.common.SagaInput
import org.swiften.redux.saga.common.SingleSagaEffect

/** Created by viethai.pham on 2019/02/17 */
/** Produces a value using [SagaInput] and [ISagaOutput.awaitFor]. */
typealias IAwaitCreator<R> = suspend CoroutineScope.(SagaInput) -> R

/**
 * [SagaEffect] whose [ISagaOutput] is created from [creator], which is a function that creates
 * [R] using [ISagaOutput.awaitFor]. It is important that the resulting [SagaOutput.stream] emits only
 * one element.
 * @param R The result emission type.
 * @param creator An [IAwaitCreator] instance.
 */
internal class AwaitEffect<R>(private val creator: IAwaitCreator<R>) : SingleSagaEffect<R>() where R : Any {
  override fun invoke(p1: SagaInput): ISagaOutput<R> {
    return SagaOutput.from(p1.scope) { this@AwaitEffect.creator(this, p1) }
  }
}
