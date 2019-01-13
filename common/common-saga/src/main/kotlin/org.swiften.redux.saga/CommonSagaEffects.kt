/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.saga

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import org.swiften.redux.core.IReduxAction

/** Created by haipham on 2019/01/07 */
/** Top-level namespace for [ReduxSagaEffect] helpers */
object CommonSagaEffects {
  /** Create a [DelayEffect] */
  fun <State, R> delay(source: ReduxSagaEffect<State, R>, delayMillis: Long):
    ReduxSagaEffect<State, R> = DelayEffect(source, delayMillis)

  /** Create a [CatchErrorEffect] instance. */
  fun <State, R> catchError(
    source: ReduxSagaEffect<State, R>,
    catcher: (Throwable) -> R
  ): ReduxSagaEffect<State, R> = CatchErrorEffect(source, catcher)

  /** Create a [DoOnValueEffect] instance */
  fun <State, R> doOnValue(source: ReduxSagaEffect<State, R>, block: (R) -> Unit):
    ReduxSagaEffect<State, R> = DoOnValueEffect(source, block)

  /** Create a [FilterEffect] */
  fun <State, R> filter(source: ReduxSagaEffect<State, R>, selector: (R) -> Boolean):
    ReduxSagaEffect<State, R> = FilterEffect(source, selector)

  /** Create a [MapEffect] */
  fun <State, P, R> map(param: ReduxSagaEffect<State, P>, block: (P) -> R):
    ReduxSagaEffect<State, R> = MapEffect(param, block)

  /** Create a [SuspendMapEffect] */
  fun <State, P, R : Any> mapSuspend(
    source: ReduxSagaEffect<State, P>,
    block: suspend CoroutineScope.(P) -> R
  ): ReduxSagaEffect<State, R> = SuspendMapEffect(source, block)

  /** Create a [AsyncMapEffect] */
  fun <State, P, R : Any> mapAsync(
    source: ReduxSagaEffect<State, P>,
    block: suspend CoroutineScope.(P) -> Deferred<R>
  ): ReduxSagaEffect<State, R> = AsyncMapEffect(source, block)

  /** Create a [PutEffect] */
  fun <State, P> put(
    source: ReduxSagaEffect<State, P>,
    actionCreator: (P) -> IReduxAction
  ): ReduxSagaEffect<State, Any> = PutEffect(source, actionCreator)

  /** Create a [RetryEffect] instance */
  fun <State, R> retry(source: ReduxSagaEffect<State, R>, times: Long):
    ReduxSagaEffect<State, R> = RetryEffect(source, times)

  /** Create a [ThenEffect] on [source1] and [source2] */
  fun <State, R, R2, R3> then(
    source1: ReduxSagaEffect<State, R>,
    source2: ReduxSagaEffect<State, R2>,
    selector: Function2<R, R2, R3>
  ): ReduxSagaEffect<State, R3> = ThenEffect(source1, source2, selector)
}
