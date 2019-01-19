/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.saga.rx

import io.reactivex.Single
import org.swiften.redux.saga.IReduxSagaEffect
import org.swiften.redux.saga.IReduxSagaOutput
import org.swiften.redux.saga.Input
import org.swiften.redux.saga.ReduxSagaEffect

/** Created by haipham on 2019/01/05 */
/** [IReduxSagaEffect] whose [IReduxSagaOutput] awaits for a [Single] to complete */
internal class CallEffect<P, R>(
  private val source: IReduxSagaEffect<P>,
  private val transformer: (P) -> Single<R>
) : ReduxSagaEffect<R>() {
  override fun invoke(p1: Input) = this.source.invoke(p1).flatMap {
    ReduxSagaOutput(p1.scope, this.transformer(it).toFlowable()) { }
  }
}

/** Invoke a [CallEffect] on [this] */
fun <P, R> ReduxSagaEffect<P>.call(transformer: (P) -> Single<R>) =
  this.transform(ReduxSagaEffects.call(transformer))
