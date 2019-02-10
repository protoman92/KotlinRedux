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
   * @param millis See [DelayEffect.millis].
   * @return An [ISagaEffectTransformer] instance.
   */
  @JvmStatic
  fun <R> delay(millis: Long): ISagaEffectTransformer<R, R> where R : Any {
    return { DelayEffect(it, millis) }
  }

  /**
   * Create a [CatchErrorEffect].
   * @param R The result emission type.
   * @param catcher See [CatchErrorEffect.catcher].
   * @return An [ISagaEffectTransformer] instance.
   */
  @JvmStatic
  fun <R> catchError(catcher: (Throwable) -> R): ISagaEffectTransformer<R, R> where R : Any {
    return { CatchErrorEffect(it, catcher) }
  }

  /**
   * Create a [SuspendCatchErrorEffect].
   * @param R The result emission type.
   * @param catcher See [SuspendCatchErrorEffect.catcher].
   * @return An [ISagaEffectTransformer] instance.
   */
  @JvmStatic
  fun <R> catchErrorSuspend(catcher: suspend CoroutineScope.(Throwable) -> R):
    ISagaEffectTransformer<R, R> where R : Any {
    return { SuspendCatchErrorEffect(it, catcher) }
  }

  /**
   * Create an [AsyncCatchErrorEffect].
   * @param R The result emission type.
   * @param catcher See [AsyncCatchErrorEffect.catcher].
   * @return An [ISagaEffectTransformer] instance.
   */
  @JvmStatic
  fun <R> catchErrorAsync(catcher: suspend CoroutineScope.(Throwable) -> Deferred<R>):
    ISagaEffectTransformer<R, R> where R : Any {
    return { AsyncCatchErrorEffect(it, catcher) }
  }

  /**
   * Create a [DoOnValueEffect] instance.
   * @param R The result emission type.
   * @param performer See [DoOnValueEffect.performer].
   * @return An [ISagaEffectTransformer] instance.
   */
  @JvmStatic
  fun <R> doOnValue(performer: (R) -> Unit): ISagaEffectTransformer<R, R> where R : Any {
    return { DoOnValueEffect(it, performer) }
  }

  /**
   * Create a [FilterEffect].
   * @param R The result emission type.
   * @param predicate See [FilterEffect.predicate].
   * @return An [ISagaEffectTransformer] instance.
   */
  @JvmStatic
  fun <R> filter(predicate: (R) -> Boolean): ISagaEffectTransformer<R, R> where R : Any {
    return { FilterEffect(it, predicate) }
  }

  /**
   * Create a [MapEffect].
   * @param R The result emission type.
   * @param transformer See [MapEffect.transformer].
   * @return An [ISagaEffectTransformer] instance.
   */
  @JvmStatic
  fun <P, R> map(transformer: (P) -> R): ISagaEffectTransformer<P, R> where P : Any, R : Any {
    return { MapEffect(it, transformer) }
  }

  /**
   * Create a [SuspendMapEffect].
   * @param R The result emission type.
   * @param transformer See [SuspendMapEffect.transformer].
   * @return An [ISagaEffectTransformer] instance.
   */
  @JvmStatic
  fun <P, R> mapSuspend(transformer: suspend CoroutineScope.(P) -> R):
    ISagaEffectTransformer<P, R> where P : Any, R : Any {
    return { SuspendMapEffect(it, transformer) }
  }

  /**
   * Create an [AsyncMapEffect].
   * @param R The result emission type.
   * @param transformer See [AsyncMapEffect.transformer].
   * @return An [ISagaEffectTransformer] instance.
   */
  @JvmStatic
  fun <P, R> mapAsync(transformer: suspend CoroutineScope.(P) -> Deferred<R>):
    ISagaEffectTransformer<P, R> where P : Any, R : Any {
    return { AsyncMapEffect(it, transformer) }
  }

  /**
   * Create a [CompactMapEffect].
   * @param P The source emission type.
   * @param R The result emission type.
   * @param transformer See [CompactMapEffect.transformer].
   * @return An [ISagaEffectTransformer] instance.
   */
  @JvmStatic
  fun <P, R> compactMap(transformer: (P) -> R?): ISagaEffectTransformer<P, R>
    where P : Any, R : Any {
    return { CompactMapEffect(it, transformer) }
  }

  /**
   * Create a [PutEffect].
   * @param P The source emission type.
   * @param actionCreator See [PutEffect.actionCreator].
   * @return An [ISagaEffectTransformer] instance.
   */
  @JvmStatic
  fun <P> put(actionCreator: (P) -> IReduxAction): ISagaEffectTransformer<P, Any> where P : Any {
    return { PutEffect(it, actionCreator) }
  }

  /**
   * Create a [RetryEffect].
   * @param R The result emission type.
   * @param times See [RetryEffect.times].
   * @return An [ISagaEffectTransformer] instance.
   */
  @JvmStatic
  fun <R> retry(times: Long): ISagaEffectTransformer<R, R> where R : Any {
    return { RetryEffect(it, times) }
  }

  /**
   * Create a [ThenEffect] on [source1] and [source2].
   * @param R The first source emission type.
   * @param R2 The second source emission type.
   * @param R3 The result emission type.
   * @param source1 See [ThenEffect.source1].
   * @param source2 See [ThenEffect.source2].
   * @param combiner See [ThenEffect.combiner].
   * @return A [ThenEffect] instance.
   */
  @JvmStatic
  fun <R, R2, R3> then(
    source1: ISagaEffect<R>,
    source2: ISagaEffect<R2>,
    combiner: Function2<R, R2, R3>
  ): SagaEffect<R3> where R : Any, R2 : Any, R3 : Any {
    return ThenEffect(source1, source2, combiner)
  }

  /**
   * Create a [TimeoutEffect].
   * @param R The result emission type.
   * @param millis See [TimeoutEffect.millis].
   * @return An [ISagaEffectTransformer] instance.
   */
  @JvmStatic
  fun <R> timeout(millis: Long): ISagaEffectTransformer<R, R> where R : Any {
    return { TimeoutEffect(it, millis) }
  }
}
