/*
 * Copyright (c) haipham 2018. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.saga

/** Created by haipham on 2018/12/26 */
/**
 * [ReduxSagaEffect] whose [ReduxSaga.Output] catches [Throwable] from
 * upstream
 */
internal class CatchErrorEffect<State, R>(
  private val source: ReduxSagaEffect<State, R>,
  private val catcher: (Throwable) -> R
): ReduxSagaEffect<State, R> {
  override fun invoke(input: ReduxSaga.Input<State>) =
    this.source.invoke(input).catchError(this.catcher)
}

/** Catch [Throwable] from upstream with [catcher] */
fun <State, R> ReduxSagaEffect<State, R>.catchError(catcher: (Throwable) -> R) =
  ReduxSagaHelper.catchError(this, catcher)
