/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.saga.rx

import io.reactivex.Single
import org.swiften.redux.saga.ISagaEffect
import org.swiften.redux.saga.ISagaOutput
import org.swiften.redux.saga.SagaInput
import org.swiften.redux.saga.SagaEffect

/** Created by haipham on 2019/01/05 */
/** [ISagaEffect] whose [ISagaOutput] awaits for a [Single] to complete */
internal class CallEffect<P, R>(
  private val source: ISagaEffect<P>,
  private val transformer: (P) -> Single<R>
) : SagaEffect<R>() {
  override fun invoke(p1: SagaInput) = this.source.invoke(p1).flatMap {
    SagaOutput(p1.scope, this.transformer(it).toFlowable()) { }
  }
}

/** Invoke a [CallEffect] on [this] */
fun <P, R> SagaEffect<P>.call(transformer: (P) -> Single<R>) =
  this.transform(SagaEffects.call(transformer))
