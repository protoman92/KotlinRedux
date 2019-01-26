/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.saga.common

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import org.swiften.redux.core.IReduxAction

/** Created by haipham on 2019/01/07 */
/** Top-level namespace for [ISagaEffect] helpers */
object CommonEffects {
  /** Create a [DelayEffect] */
  @JvmStatic
  fun <R> delay(millis: Long): ISagaEffectTransformer<R, R> = {
    DelayEffect(
      it,
      millis
    )
  }

  /** Create a [CatchErrorEffect]. */
  @JvmStatic
  fun <R> catchError(catcher: (Throwable) -> R): ISagaEffectTransformer<R, R> =
    { CatchErrorEffect(it, catcher) }

  /** Create a [SuspendCatchErrorEffect] */
  @JvmStatic
  fun <R> catchErrorSuspend(catcher: suspend CoroutineScope.(Throwable) -> R):
    ISagaEffectTransformer<R, R> = {
    SuspendCatchErrorEffect(
      it,
      catcher
    )
  }

  /** Create a [AsyncCatchErrorEffect] */
  @JvmStatic
  fun <R> catchErrorAsync(catcher: suspend CoroutineScope.(Throwable) -> Deferred<R>):
    ISagaEffectTransformer<R, R> = {
    AsyncCatchErrorEffect(
      it,
      catcher
    )
  }

  /** Create a [DoOnValueEffect] instance */
  @JvmStatic
  fun <R> doOnValue(performer: (R) -> Unit): ISagaEffectTransformer<R, R> =
    { DoOnValueEffect(it, performer) }

  /** Create a [FilterEffect] */
  @JvmStatic
  fun <R> filter(predicate: (R) -> Boolean): ISagaEffectTransformer<R, R> =
    { FilterEffect(it, predicate) }

  /** Create a [MapEffect] */
  @JvmStatic
  fun <P, R> map(transformer: (P) -> R): ISagaEffectTransformer<P, R> =
    { MapEffect(it, transformer) }

  /** Create a [SuspendMapEffect] */
  @JvmStatic
  fun <P, R> mapSuspend(transformer: suspend CoroutineScope.(P) -> R):
    ISagaEffectTransformer<P, R> = {
    SuspendMapEffect(
      it,
      transformer
    )
  }

  /** Create a [AsyncMapEffect] */
  @JvmStatic
  fun <P, R> mapAsync(transformer: suspend CoroutineScope.(P) -> Deferred<R>):
    ISagaEffectTransformer<P, R> = {
    AsyncMapEffect(
      it,
      transformer
    )
  }

  /** Create a [PutEffect] */
  @JvmStatic
  fun <P> put(actionCreator: (P) -> IReduxAction):
    ISagaEffectTransformer<P, Any> = {
    PutEffect(
      it,
      actionCreator
    )
  }

  /** Create a [RetryEffect] */
  @JvmStatic
  fun <R> retry(times: Long): ISagaEffectTransformer<R, R> = {
    RetryEffect(
      it,
      times
    )
  }

  /** Create a [ThenEffect] on [source1] and [source2] */
  @JvmStatic
  fun <R, R2, R3> then(
    source1: ISagaEffect<R>,
    source2: ISagaEffect<R2>,
    combiner: Function2<R, R2, R3>
  ): SagaEffect<R3> =
    ThenEffect(source1, source2, combiner)

  /** Create a [TimeoutEffect] */
  @JvmStatic
  fun <R> timeout(millis: Long): ISagaEffectTransformer<R, R> = {
    TimeoutEffect(
      it,
      millis
    )
  }
}
