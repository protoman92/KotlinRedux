/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.saga

/** Created by viethai.pham on 2019/02/21 */
/**
 * [SagaEffect] whose [ISagaOutput] merges the emissions from [ISagaOutput] instances produced
 * by [sources].
 * @param R The result emission type.
 * @param sources A [Collection] of [SagaEffect].
 */
internal class AllEffect<R>(private val sources: Collection<SagaEffect<R>>) : SagaEffect<R>() where R : Any {
  override fun invoke(p1: SagaInput): ISagaOutput<R> {
    val sourceOutputs = this.sources.map { (it.invoke(p1) as SagaOutput<R>) }
    return SagaOutput.merge(p1.monitor, sourceOutputs)
  }
}
