/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.saga.common

/** Created by haipham on 2019/01/26/1 */
/**
 * Invoke a [FlatMapEffect] on the current [ISagaEffect] with [FlatMapEffect.Mode.EVERY].
 * @receiver See [FlatMapEffect.source].
 * @param P The source emission type.
 * @param R The result emission type.
 * @param transformer See [FlatMapEffect.transformer].
 * @return A [SagaEffect] instance.
 */
fun <P, R> SagaEffect<P>.flatMap(transformer: (P) -> SagaEffect<R>): SagaEffect<R> where P : Any, R : Any {
  return this.transform(CommonEffects.flatMap(transformer))
}

/**
 * Invoke a [FlatMapEffect] on the current [ISagaEffect] with [FlatMapEffect.Mode.LATEST].
 * @receiver See [FlatMapEffect.source].
 * @param P The source emission type.
 * @param R The result emission type.
 * @param transformer See [FlatMapEffect.transformer].
 * @return A [SagaEffect] instance.
 */
fun <P, R> SagaEffect<P>.switchMap(transformer: (P) -> SagaEffect<R>): SagaEffect<R> where P : Any, R : Any {
  return this.transform(CommonEffects.switchMap(transformer))
}
