/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.saga

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred

/** Created by haipham on 2018/12/26 */
/** [IReduxSagaEffect] whose [IReduxSagaOutput] catches [Throwable] from upstream */
internal class CatchErrorEffect<R>(
  private val source: IReduxSagaEffect<R>,
  private val catcher: (Throwable) -> R
) : ReduxSagaEffect<R>() {
  override fun invoke(p1: Input) = this.source.invoke(p1).catchError(this.catcher)
}

/** Similar to [CatchErrorEffect], but handles suspending [catcher] */
internal class SuspendCatchErrorEffect<R>(
  private val source: IReduxSagaEffect<R>,
  private val catcher: suspend CoroutineScope.(Throwable) -> R
) : ReduxSagaEffect<R>() {
  override fun invoke(p1: Input) = this.source.invoke(p1).catchErrorSuspend(this.catcher)
}

/** Similar to [CatchErrorEffect], but handles async [catcher] */
internal class AsyncCatchErrorEffect<R>(
  private val source: IReduxSagaEffect<R>,
  private val catcher: suspend CoroutineScope.(Throwable) -> Deferred<R>
) : ReduxSagaEffect<R>() {
  override fun invoke(p1: Input) = this.source.invoke(p1).catchErrorAsync(this.catcher)
}

/** Catch [Throwable] from upstream with [catcher] */
fun <R> ReduxSagaEffect<R>.catchError(catcher: (Throwable) -> R) =
  this.transform(CommonSagaEffects.catchError(catcher))

/** Invoke a [SuspendCatchErrorEffect] on [this] */
fun <R> ReduxSagaEffect<R>.catchErrorSuspend(
  catcher: suspend CoroutineScope.(Throwable) -> R
) = this.transform(CommonSagaEffects.catchErrorSuspend(catcher))

/** Invoke a [AsyncCatchErrorEffect] on [this] */
fun <R> ReduxSagaEffect<R>.catchErrorAsync(
  catcher: suspend CoroutineScope.(Throwable) -> Deferred<R>
) = this.transform(CommonSagaEffects.catchErrorAsync(catcher))
