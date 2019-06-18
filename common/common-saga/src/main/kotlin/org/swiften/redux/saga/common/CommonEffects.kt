/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.saga.common

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
   * Create a [PutEffect].
   * @param action See [PutEffect.action].
   * @return A [PutEffect] instance.
   */
  @JvmStatic
  fun putInStore(action: IReduxAction): PutEffect {
    return PutEffect(action)
  }
}
