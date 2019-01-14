/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.saga

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred

/** Created by haipham on 2018/12/26 */
/** [ReduxSagaEffect] whose [IReduxSagaOutput] catches [Throwable] from upstream */
internal class CatchErrorEffect<State, R>(
  private val source: ReduxSagaEffect<State, R>,
  private val catcher: (Throwable) -> R
) : ReduxSagaEffect<State, R> {
  override fun invoke(p1: Input<State>) =
    this.source.invoke(p1).catchError(this.catcher)
}

/** Similar to [CatchErrorEffect], but handles suspending [catcher] */
internal class SuspendCatchErrorEffect<State, R>(
  private val source: ReduxSagaEffect<State, R>,
  private val catcher: suspend CoroutineScope.(Throwable) -> R
) : ReduxSagaEffect<State, R> {
  override fun invoke(p1: Input<State>) =
    this.source.invoke(p1).catchErrorSuspend(this.catcher)
}

/** Similar to [CatchErrorEffect], but handles async [catcher] */
internal class AsyncCatchErrorEffect<State, R>(
  private val source: ReduxSagaEffect<State, R>,
  private val catcher: suspend CoroutineScope.(Throwable) -> Deferred<R>
) : ReduxSagaEffect<State, R> {
  override fun invoke(p1: Input<State>) =
    this.source.invoke(p1).catchErrorAsync(this.catcher)
}

/** Catch [Throwable] from upstream with [catcher] */
fun <State, R> ReduxSagaEffect<State, R>.catchError(catcher: (Throwable) -> R) =
  this.transform(ReduxSagaEffects.catchError(catcher))

/** Invoke a [SuspendCatchErrorEffect] on [this] */
fun <State, R> ReduxSagaEffect<State, R>.catchErrorSuspend(
  catcher: suspend CoroutineScope.(Throwable) -> R
) = this.transform(ReduxSagaEffects.catchErrorSuspend(catcher))

/** Invoke a [AsyncCatchErrorEffect] on [this] */
fun <State, R> ReduxSagaEffect<State, R>.catchErrorAsync(
  catcher: suspend CoroutineScope.(Throwable) -> Deferred<R>
) = this.transform(ReduxSagaEffects.catchErrorAsync(catcher))
