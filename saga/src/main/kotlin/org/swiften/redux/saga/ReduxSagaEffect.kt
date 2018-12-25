/*
 * Copyright (c) haipham 2018. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.saga

import kotlinx.coroutines.CoroutineScope
import org.swiften.redux.core.Redux

/**
 * Created by haipham on 2018/12/24.
 */
/** Top-level namespace for [ReduxSaga.IEffect] helpers */
object ReduxSagaEffect {
  /** Create a [CallEffect] */
  fun <State, P, R> call(
    param: ReduxSaga.IEffect<State, P>,
    block: suspend CoroutineScope.(P) -> R
  ): ReduxSaga.IEffect<State, R> = CallEffect(param, block)

  /** Create a [CallEffect] with [param] */
  fun <State, P, R> call(param: P, block: suspend CoroutineScope.(P) -> R) =
    this.call(this.just<State, P>(param), block)

  /** Create a [JustEffect] */
  fun <State, R> just(value: R): ReduxSaga.IEffect<State, R> = JustEffect(value)

  /** Create a [TakeEveryEffect] instance. */
  fun <State, P, R> takeEvery(
    extract: suspend (Redux.IAction) -> P?,
    block: suspend CoroutineScope.(P) -> ReduxSaga.IEffect<State, R>
  ): ReduxSaga.IEffect<State, R> = TakeEveryEffect(extract, block)

  /** Create a [TakeLatestEffect] instance. */
  fun <State, P, R> takeLatest(
    extract: suspend (Redux.IAction) -> P?,
    block: suspend CoroutineScope.(P) -> ReduxSaga.IEffect<State, R>
  ): ReduxSaga.IEffect<State, R> = TakeLatestEffect(extract, block)
}
