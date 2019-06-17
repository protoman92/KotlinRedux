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
  fun <R> delayUpstreamValue(millis: Long): ISagaEffectTransformer<R, R> where R : Any {
    return { DelayEffect(it, millis) }
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
   * Create a [FlatMapEffect] instance with [FlatMapEffect.Mode.EVERY].
   * @param P The source emission type.
   * @param R The result emission type.
   * @param transformer See [FlatMapEffect.transformer].
   * @return An [ISagaEffectTransformer] instance.
   */
  @JvmStatic
  fun <P, R> flatMap(transformer: (P) -> SagaEffect<R>): ISagaEffectTransformer<P, R> where P : Any, R : Any {
    return { FlatMapEffect(it, FlatMapEffect.Mode.EVERY, transformer) }
  }

  /**
   * Create a [FlatMapEffect] instance with [FlatMapEffect.Mode.LATEST].
   * @param P The source emission type.
   * @param R The result emission type.
   * @param transformer See [FlatMapEffect.transformer].
   * @return An [ISagaEffectTransformer] instance.
   */
  @JvmStatic
  fun <P, R> switchMap(transformer: (P) -> SagaEffect<R>): ISagaEffectTransformer<P, R> where P : Any, R : Any {
    return { FlatMapEffect(it, FlatMapEffect.Mode.LATEST, transformer) }
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
  fun <P, R> mapIgnoringNull(transformer: (P) -> R?):
    ISagaEffectTransformer<P, R> where P : Any, R : Any {
    return { CompactMapEffect(it, transformer) }
  }

  /**
   * Create a [PutEffect].
   * @param action See [PutEffect.action].
   * @return A [PutEffect] instance.
   */
  @JvmStatic
  fun putInStore(action: IReduxAction): PutEffect {
    return PutEffect(action)
  }
}
