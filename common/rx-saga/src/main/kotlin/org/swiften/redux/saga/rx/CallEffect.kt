/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.saga.rx

import io.reactivex.Single
import org.swiften.redux.saga.IReduxSagaOutput
import org.swiften.redux.saga.Input
import org.swiften.redux.saga.ReduxSagaEffect
import org.swiften.redux.saga.ReduxSagaEffects

/** Created by haipham on 2019/01/05 */
/** [ReduxSagaEffect] whose [IReduxSagaOutput] awaits for a [Single] to complete */
internal class CallEffect<State, P, R>(
  private val source: ReduxSagaEffect<State, P>,
  private val block: (P) -> Single<R>
) : ReduxSagaEffect<State, R> {
  override fun invoke(p1: Input<State>) = this.source.invoke(p1).flatMap {
    ReduxSagaOutput(p1.scope, this.block(it).toFlowable()) { }
  }
}

/** Invoke a [CallEffect] on [this] */
fun <State, P, R> ReduxSagaEffect<State, P>.call(block: (P) -> Single<R>) =
  ReduxSagaEffects.call(this, block)
