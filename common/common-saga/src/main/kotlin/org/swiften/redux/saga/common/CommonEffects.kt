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
  /**
   * Create a [DelayEffect].
   * @param R The result emission type.
   * @param millis The delay time in milliseconds.
   * @return An [ISagaEffectTransformer] instance.
   */
  @JvmStatic
  fun <R> delay(millis: Long): ISagaEffectTransformer<R, R> = { DelayEffect(it, millis) }

  /**
   * Create a [CatchErrorEffect].
   * @param R The result emission type.
   * @param catcher Function that catches [Throwable].
   * @return An [ISagaEffectTransformer] instance.
   */
  @JvmStatic
  fun <R> catchError(catcher: (Throwable) -> R): ISagaEffectTransformer<R, R> {
    return { CatchErrorEffect(it, catcher) }
  }

  /**
   * Create a [SuspendCatchErrorEffect].
   * @param R The result emission type.
   * @param catcher Function that catches [Throwable].
   * @return An [ISagaEffectTransformer] instance.
   */
  @JvmStatic
  fun <R> catchErrorSuspend(catcher: suspend CoroutineScope.(Throwable) -> R):
    ISagaEffectTransformer<R, R> {
    return { SuspendCatchErrorEffect(it, catcher) }
  }


  /**
   * Create an [AsyncCatchErrorEffect].
   * @param R The result emission type.
   * @param catcher Function that catches [Throwable].
   * @return An [ISagaEffectTransformer] instance.
   */
  @JvmStatic
  fun <R> catchErrorAsync(catcher: suspend CoroutineScope.(Throwable) -> Deferred<R>):
    ISagaEffectTransformer<R, R> {
    return { AsyncCatchErrorEffect(it, catcher) }
  }

  /**
   * Create a [DoOnValueEffect] instance.
   * @param R The result emission type.
   * @param performer Function that performs side effects on [R].
   * @return An [ISagaEffectTransformer] instance.
   */
  @JvmStatic
  fun <R> doOnValue(performer: (R) -> Unit): ISagaEffectTransformer<R, R> =
    { DoOnValueEffect(it, performer) }

  /**
   * Create a [FilterEffect].
   * @param R The result emission type.
   * @param predicate Function that performs conditional checking on [R].
   * @return An [ISagaEffectTransformer] instance.
   */
  @JvmStatic
  fun <R> filter(predicate: (R) -> Boolean): ISagaEffectTransformer<R, R> {
    return { FilterEffect(it, predicate) }
  }

  /**
   * Create a [MapEffect].
   * @param R The result emission type.
   * @param transformer Function that transforms [P] to [R].
   * @return An [ISagaEffectTransformer] instance.
   */
  @JvmStatic
  fun <P, R> map(transformer: (P) -> R): ISagaEffectTransformer<P, R> {
    return { MapEffect(it, transformer) }
  }

  /**
   * Create a [SuspendMapEffect].
   * @param R The result emission type.
   * @param transformer Function that transforms [P] to [R].
   * @return An [ISagaEffectTransformer] instance.
   */
  @JvmStatic
  fun <P, R> mapSuspend(transformer: suspend CoroutineScope.(P) -> R):
    ISagaEffectTransformer<P, R> {
    return { SuspendMapEffect(it, transformer) }
  }

  /**
   * Create a [AsyncMapEffect].
   * @param R The result emission type.
   * @param transformer Function that transforms [P] to [R].
   * @return An [ISagaEffectTransformer] instance.
   */
  @JvmStatic
  fun <P, R> mapAsync(transformer: suspend CoroutineScope.(P) -> Deferred<R>):
    ISagaEffectTransformer<P, R> {
    return { AsyncMapEffect(it, transformer) }
  }

  /**
   * Create a [PutEffect].
   * @param P The source emission type.
   * @param actionCreator Function that creates [IReduxAction] from [P].
   * @return An [ISagaEffectTransformer] instance.
   */
  @JvmStatic
  fun <P> put(actionCreator: (P) -> IReduxAction): ISagaEffectTransformer<P, Any> {
    return { PutEffect(it, actionCreator) }
  }

  /**
   * Create a [RetryEffect].
   * @param R The result emission type.
   * @param times The number of times to retry for.
   * @return An [ISagaEffectTransformer] instance.
   */
  @JvmStatic
  fun <R> retry(times: Long): ISagaEffectTransformer<R, R> {
    return { RetryEffect(it, times) }
  }

  /**
   * Create a [ThenEffect] on [source1] and [source2].
   * @param R The first source emission type.
   * @param R2 The second source emission type.
   * @param R3 The result emission type.
   * @param source1 The first source [ISagaEffect].
   * @param source2 The second source [ISagaEffect].
   * @param combiner Function that combines [R] and [R2] to produce [R3].
   * @return A [ThenEffect] instance.
   */
  @JvmStatic
  fun <R, R2, R3> then(
    source1: ISagaEffect<R>,
    source2: ISagaEffect<R2>,
    combiner: Function2<R, R2, R3>
  ): SagaEffect<R3> {
    return ThenEffect(source1, source2, combiner)
  }

  /**
   * Create a [TimeoutEffect].
   * @param R The result emission type.
   * @param millis The timeout time in milliseconds.
   * @return An [ISagaEffectTransformer] instance.
   */
  @JvmStatic
  fun <R> timeout(millis: Long): ISagaEffectTransformer<R, R> {
    return { TimeoutEffect(it, millis) }
  }
}
