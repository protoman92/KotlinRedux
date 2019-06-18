/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.saga.common

/** Created by viethai.pham on 2019/02/17 */
/** Produces a value using [SagaInput] and [ISagaOutput.awaitFor]. */
typealias IAwaitCreator<R> = suspend (SagaInput) -> R

/**
 * [SagaEffect] whose [ISagaOutput] is created from [creator], which is a function that creates
 * [R] using [ISagaOutput.awaitFor]. It is important that the resulting [SagaOutput.stream] emits only
 * one element.
 * @param R The result emission type.
 * @param creator An [IAwaitCreator] instance.
 */
class AwaitEffect<R>(private val creator: IAwaitCreator<R>) : SingleSagaEffect<R>() where R : Any {
  override fun invoke(p1: SagaInput): ISagaOutput<R> {
    return SagaOutput.from(p1, p1.monitor) { this@AwaitEffect.creator(p1) }
  }
}
