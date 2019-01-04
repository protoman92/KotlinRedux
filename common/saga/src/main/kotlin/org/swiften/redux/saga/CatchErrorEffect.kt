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
 * [ReduxSagaEffect] whose [ReduxSaga.Output] catches [Throwable] from
 * upstream
 */
internal class CatchErrorEffect<State, R>(
  private val source: ReduxSagaEffect<State, R>,
  private val catcher: suspend CoroutineScope.(Throwable) -> R
): ReduxSagaEffect<State, R> {
  @ExperimentalCoroutinesApi
  override fun invoke(input: ReduxSaga.Input<State>) =
    this.source.invoke(input).catchError(this.catcher)
}

/** Catch [Throwable] from upstream with [catcher] */
fun <State, R> ReduxSagaEffect<State, R>.catchError(
  catcher: suspend CoroutineScope.(Throwable) -> R
) = ReduxSagaHelper.catchError(this, catcher)
