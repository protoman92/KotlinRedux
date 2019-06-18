/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.saga.common

import io.reactivex.Single

/** Created by haipham on 2019/01/05 */
/**
 * [SagaEffect] whose [ISagaOutput] awaits for a [Single] to complete.
 * @param P The source emission type.
 * @param R The result emission type.
 * @param source The source [ISagaEffect] instance.
 * @param transformer Function that transforms [P] to a [Single].
 */
internal class CallEffect<P, R>(
  private val source: ISagaEffect<P>,
  private val transformer: (P) -> Single<R>
) : SagaEffect<R>() where P : Any, R : Any {
  override fun invoke(p1: SagaInput) = this.source.invoke(p1).flatMap {
    SagaOutput(p1.context, p1.monitor, this.transformer(it).toFlowable())
  }
}
