/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.saga.common

import io.reactivex.Single

/** Created by haipham on 2019/01/26 */
/**
 * Invoke a [CallEffect] on [this].
 * @receiver See [CallEffect.source].
 * @param P The source emission type.
 * @param R The result emission type.
 * @param transformer See [CallEffect.transformer].
 * @return A [SagaEffect] instance.
 */
fun <P, R> SagaEffect<P>.mapSingle(transformer: (P) -> Single<R>):
  SagaEffect<R> where P : Any, R : Any {
  return this.transform(SagaEffects.mapSingle(transformer))
}

/**
 * Invoke a [DebounceEffect] on [this].
 * @receiver See [DebounceEffect.source].
 * @param R The result emission type.
 * @param millis See [DebounceEffect.millis].
 * @return A [SagaEffect] instance.
 */
fun <R> SagaEffect<R>.debounce(millis: Long): SagaEffect<R> where R : Any {
  return (SagaEffects.debounce<R>(millis))(this)
}
