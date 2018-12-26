/*
 * Copyright (c) haipham 2018. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.saga

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi

/**
 * Created by haipham on 2018/12/26.
 */
/**
 * [ReduxSaga.IEffect] whose [ReduxSaga.Output] catches [Throwable] from
 * upstream
 */
internal class CatchErrorEffect<State, R>(
  private val source: ReduxSaga.IEffect<State, R>,
  private val catcher: ReduxSaga.Output.IErrorCatcher<R>
): ReduxSaga.IEffect<State, R> {
  @ExperimentalCoroutinesApi
  override fun invoke(input: ReduxSaga.Input<State>) =
    this.source.invoke(input).catchError(this.catcher)
}

/** Catch [Throwable] from upstream with [catcher] */
fun <State, R> ReduxSaga.IEffect<State, R>.catchError(
  catcher: ReduxSaga.Output.IErrorCatcher<R>
) = ReduxSagaEffect.catchError(this, catcher)

/** Catch [Throwable] from upstream with [catcher] */
fun <State, R> ReduxSaga.IEffect<State, R>.catchError(
  catcher: CoroutineScope.(Throwable) -> R
) = this.catchError(object : ReduxSaga.Output.IErrorCatcher<R> {
  override suspend fun invoke(scope: CoroutineScope, error: Throwable) =
    catcher(scope, error)
})
