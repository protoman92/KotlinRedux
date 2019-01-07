/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.saga

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred

/** Created by haipham on 2019/01/07 */
/** Similar to [MapEffect], but handles async functions */
internal class AsyncCallEffect<State, P, R : Any>(
  private val source: ReduxSagaEffect<State, P>,
  private val block: suspend CoroutineScope.(P) -> Deferred<R>
) : ReduxSagaEffect<State, R> {
  override fun invoke(p1: CommonSaga.Input<State>) =
    this.source.invoke(p1).mapAsync(this.block)
}

/** Invoke a [AsyncCallEffect] on [this] */
fun <State, P, R : Any> ReduxSagaEffect<State, P>.callAsync(
  block: suspend CoroutineScope.(P) -> Deferred<R>
) = CommonSagaHelper.callAsync(this, block)
