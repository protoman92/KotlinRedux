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
  @JvmStatic
  fun <State, R> delay(delayMillis: Long): ReduxSagaEffectTransformer<State, R, R> =
    { DelayEffect(it, delayMillis) }

  /** Create a [CatchErrorEffect] instance. */
  @JvmStatic
  fun <State, R> catchError(catcher: (Throwable) -> R): ReduxSagaEffectTransformer<State, R, R> =
    { CatchErrorEffect(it, catcher) }

  /** Create a [SuspendCatchErrorEffect] */
  @JvmStatic
  fun <State, R> catchErrorSuspend(catcher: suspend CoroutineScope.(Throwable) -> R):
    ReduxSagaEffectTransformer<State, R, R> = { SuspendCatchErrorEffect(it, catcher) }

  /** Create a [AsyncCatchErrorEffect] */
  @JvmStatic
  fun <State, R> catchErrorAsync(catcher: suspend CoroutineScope.(Throwable) -> Deferred<R>):
    ReduxSagaEffectTransformer<State, R, R> = { AsyncCatchErrorEffect(it, catcher) }

  /** Create a [DoOnValueEffect] instance */
  @JvmStatic
  fun <State, R> doOnValue(block: (R) -> Unit): ReduxSagaEffectTransformer<State, R, R> =
    { DoOnValueEffect(it, block) }

  /** Create a [FilterEffect] */
  @JvmStatic
  fun <State, R> filter(selector: (R) -> Boolean): ReduxSagaEffectTransformer<State, R, R> =
    { FilterEffect(it, selector) }

  /** Create a [MapEffect] */
  @JvmStatic
  fun <State, P, R> map(block: (P) -> R): ReduxSagaEffectTransformer<State, P, R> =
    { MapEffect(it, block) }

  /** Create a [SuspendMapEffect] */
  @JvmStatic
  fun <State, P, R> mapSuspend(block: suspend CoroutineScope.(P) -> R):
    ReduxSagaEffectTransformer<State, P, R> = { SuspendMapEffect(it, block) }

  /** Create a [AsyncMapEffect] */
  @JvmStatic
  fun <State, P, R> mapAsync(block: suspend CoroutineScope.(P) -> Deferred<R>):
    ReduxSagaEffectTransformer<State, P, R> = { AsyncMapEffect(it, block) }

  /** Create a [PutEffect] */
  @JvmStatic
  fun <State, P> put(actionCreator: (P) -> IReduxAction):
    ReduxSagaEffectTransformer<State, P, Any> = { PutEffect(it, actionCreator) }

  /** Create a [RetryEffect] instance */
  @JvmStatic
  fun <State, R> retry(times: Long): ReduxSagaEffectTransformer<State, R, R> =
    { RetryEffect(it, times) }

  /** Create a [ThenEffect] on [source1] and [source2] */
  @JvmStatic
  fun <State, R, R2, R3> then(
    source1: ReduxSagaEffect<State, R>,
    source2: ReduxSagaEffect<State, R2>,
    selector: Function2<R, R2, R3>
  ): ReduxSagaEffect<State, R3> = ThenEffect(source1, source2, selector)

  /** Create a [TimeoutEffect] */
  @JvmStatic
  fun <State, R> timeout(millis: Long): ReduxSagaEffectTransformer<State, R, R> =
    { TimeoutEffect(it, millis) }
}
