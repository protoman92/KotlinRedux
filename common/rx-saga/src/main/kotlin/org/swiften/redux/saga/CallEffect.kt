/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.saga

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred

/** Created by haipham on 2019/01/05 */
/** Similar to [MapEffect], but handles [Deferred] */
internal class CallEffect<State, P, R>(
  private val source: ReduxSagaEffect<State, P>,
  private val block: suspend CoroutineScope.(P) -> Deferred<R>
) : ReduxSagaEffect<State, R> {
  override fun invoke(p1: ReduxSaga.Input<State>) =
    this.source.invoke(p1).mapAsync(this.block)
}

/** Invoke a [CallEffect] on [this] */
fun <State, P, R> ReduxSagaEffect<State, P>.call(
  block: suspend CoroutineScope.(P) -> Deferred<R>
) = ReduxSagaHelper.call(this, block)
