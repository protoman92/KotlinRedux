/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.saga

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import org.swiften.redux.core.IReduxAction

/** Created by haipham on 2019/01/07 */
/** Top-level namespace for [IReduxSagaEffect] helpers */
object CommonSagaEffects {
  /** Create a [DelayEffect] */
  @JvmStatic
  fun <R> delay(millis: Long): IReduxSagaEffectTransformer<R, R> = { DelayEffect(it, millis) }

  /** Create a [CatchErrorEffect]. */
  @JvmStatic
  fun <R> catchError(catcher: (Throwable) -> R): IReduxSagaEffectTransformer<R, R> =
    { CatchErrorEffect(it, catcher) }

  /** Create a [SuspendCatchErrorEffect] */
  @JvmStatic
  fun <R> catchErrorSuspend(catcher: suspend CoroutineScope.(Throwable) -> R):
    IReduxSagaEffectTransformer<R, R> = { SuspendCatchErrorEffect(it, catcher) }

  /** Create a [AsyncCatchErrorEffect] */
  @JvmStatic
  fun <R> catchErrorAsync(catcher: suspend CoroutineScope.(Throwable) -> Deferred<R>):
    IReduxSagaEffectTransformer<R, R> = { AsyncCatchErrorEffect(it, catcher) }

  /** Create a [DoOnValueEffect] instance */
  @JvmStatic
  fun <R> doOnValue(performer: (R) -> Unit): IReduxSagaEffectTransformer<R, R> =
    { DoOnValueEffect(it, performer) }

  /** Create a [FilterEffect] */
  @JvmStatic
  fun <R> filter(predicate: (R) -> Boolean): IReduxSagaEffectTransformer<R, R> =
    { FilterEffect(it, predicate) }

  /** Create a [MapEffect] */
  @JvmStatic
  fun <P, R> map(transformer: (P) -> R): IReduxSagaEffectTransformer<P, R> =
    { MapEffect(it, transformer) }

  /** Create a [SuspendMapEffect] */
  @JvmStatic
  fun <P, R> mapSuspend(transformer: suspend CoroutineScope.(P) -> R):
    IReduxSagaEffectTransformer<P, R> = { SuspendMapEffect(it, transformer) }

  /** Create a [AsyncMapEffect] */
  @JvmStatic
  fun <P, R> mapAsync(transformer: suspend CoroutineScope.(P) -> Deferred<R>):
    IReduxSagaEffectTransformer<P, R> = { AsyncMapEffect(it, transformer) }

  /** Create a [PutEffect] */
  @JvmStatic
  fun <P> put(actionCreator: (P) -> IReduxAction):
    IReduxSagaEffectTransformer<P, Any> = { PutEffect(it, actionCreator) }

  /** Create a [RetryEffect] */
  @JvmStatic
  fun <R> retry(times: Long): IReduxSagaEffectTransformer<R, R> = { RetryEffect(it, times) }

  /** Create a [ThenEffect] on [source1] and [source2] */
  @JvmStatic
  fun <R, R2, R3> then(
    source1: IReduxSagaEffect<R>,
    source2: IReduxSagaEffect<R2>,
    combiner: Function2<R, R2, R3>
  ): ReduxSagaEffect<R3> = ThenEffect(source1, source2, combiner)

  /** Create a [TimeoutEffect] */
  @JvmStatic
  fun <R> timeout(millis: Long): IReduxSagaEffectTransformer<R, R> = { TimeoutEffect(it, millis) }
}
