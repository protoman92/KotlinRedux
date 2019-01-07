/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.saga

import io.reactivex.Single
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred

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

/** Similar to [CallEffect], but handles suspend functions */
internal class SuspendCallEffect<State, P, R : Any>(
  private val source: ReduxSagaEffect<State, P>,
  private val block: suspend CoroutineScope.(P) -> R
) : ReduxSagaEffect<State, R> {
  override fun invoke(p1: CommonSaga.Input<State>) =
    this.source.invoke(p1).mapSuspend(this.block)
}

/** Similar to [CallEffect], but handles async functions */
internal class AsyncCallEffect<State, P, R : Any>(
  private val source: ReduxSagaEffect<State, P>,
  private val block: suspend CoroutineScope.(P) -> Deferred<R>
) : ReduxSagaEffect<State, R> {
  override fun invoke(p1: CommonSaga.Input<State>) =
    this.source.invoke(p1).mapAsync(this.block)
}

/** Invoke a [CallEffect] on [this] */
fun <State, P, R> ReduxSagaEffect<State, P>.call(block: (P) -> Single<R>) =
  ReduxSagaHelper.call(this, block)

/** Invoke a [SuspendCallEffect] on [this] */
fun <State, P, R : Any> ReduxSagaEffect<State, P>.callSuspend(
  block: suspend CoroutineScope.(P) -> R
) = ReduxSagaHelper.callSuspend(this, block)

/** Invoke a [AsyncCallEffect] on [this] */
fun <State, P, R : Any> ReduxSagaEffect<State, P>.callAsync(
  block: suspend CoroutineScope.(P) -> Deferred<R>
) = ReduxSagaHelper.callAsync(this, block)
