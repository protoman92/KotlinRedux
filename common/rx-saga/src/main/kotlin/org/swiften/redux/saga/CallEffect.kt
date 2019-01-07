/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.saga

import io.reactivex.Single

/** Created by haipham on 2019/01/05 */
/** Similar to [MapEffect], but handles [Single] */
internal class CallEffect<State, P, R>(
  private val source: ReduxSagaEffect<State, P>,
  private val block: (P) -> Single<R>
) : ReduxSagaEffect<State, R> {
  override fun invoke(p1: CommonSaga.Input<State>) =
    this.source.invoke(p1).flatMap {
      ReduxSaga.Output(p1.scope, this.block(it).toFlowable()) { }
    }
}

/** Invoke a [CallEffect] on [this] */
fun <State, P, R> ReduxSagaEffect<State, P>.call(block: (P) -> Single<R>) =
  ReduxSagaHelper.call(this, block)
