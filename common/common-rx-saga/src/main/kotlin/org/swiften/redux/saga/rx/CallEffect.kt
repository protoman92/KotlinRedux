/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.saga.rx

import io.reactivex.Single
import org.swiften.redux.saga.common.ISagaEffect
import org.swiften.redux.saga.common.ISagaOutput
import org.swiften.redux.saga.common.SagaEffect
import org.swiften.redux.saga.common.SagaInput

/** Created by haipham on 2019/01/05 */
/**
 * [ISagaEffect] whose [ISagaOutput] awaits for a [Single] to complete.
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
    SagaOutput(p1.scope, this.transformer(it).toFlowable()) { }
  }
}
