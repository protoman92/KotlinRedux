/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.saga.common

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import org.swiften.redux.core.IReduxAction

/** Created by haipham on 2019/01/26/1 */
/**
 * Invoke a [DelayEffect] on [this].
 * @receiver See [DelayEffect.source].
 * @param R The result emission type.
 * @param millis See [DelayEffect.millis].
 * @return A [SagaEffect] instance.
 */
fun <R> SagaEffect<R>.delayUpstreamValue(millis: Long): SagaEffect<R> where R : Any {
  return this.transform(CommonEffects.delayUpstreamValue(millis))
}

/**
 * Invoke a [DoOnValueEffect] on [this].
 * @receiver See [DoOnValueEffect.source].
 * @param R The result emission type.
 * @param performer See [DoOnValueEffect.performer].
 * @return A [SagaEffect] instance.*
 */
fun <R> SagaEffect<R>.doOnValue(performer: (R) -> Unit): SagaEffect<R> where R : Any {
  return this.transform(CommonEffects.doOnValue(performer))
}

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

/**
 * Invoke a [MapEffect] on the current [ISagaEffect].
 * @receiver See [MapEffect.source].
 * @param P The source emission type.
 * @param R The result emission type.
 * @param transformer See [MapEffect.transformer].
 * @return A [SagaEffect] instance.
 */
fun <P, R> SagaEffect<P>.map(transformer: (P) -> R): SagaEffect<R> where P : Any, R : Any {
  return this.transform(CommonEffects.map(transformer))
}

/**
 * Invoke a [SuspendMapEffect] on [this].
 * @receiver See [SuspendMapEffect.source].
 * @param P The source emission type.
 * @param R The result emission type.
 * @param transformer See [SuspendMapEffect.transformer].
 * @return A [SagaEffect] instance.
 */
fun <P, R> SagaEffect<P>.mapSuspend(transformer: suspend CoroutineScope.(P) -> R):
  SagaEffect<R> where P : Any, R : Any {
  return this.transform(CommonEffects.mapSuspend(transformer))
}

/**
 * Invoke a [AsyncMapEffect] on [this].
 * @receiver See [AsyncMapEffect.source].
 * @param P The source emission type.
 * @param R The result emission type.
 * @param transformer See [AsyncMapEffect.transformer].
 * @return A [SagaEffect] instance.
 */
fun <P, R> SagaEffect<P>.mapAsync(transformer: suspend CoroutineScope.(P) -> Deferred<R>):
  SagaEffect<R> where P : Any, R : Any {
  return this.transform(CommonEffects.mapAsync(transformer))
}

/**
 * Invoke a [CompactMapEffect] on [this].
 * @receiver See [CompactMapEffect.source].
 * @param R The result emission type.
 * @param transformer See [CompactMapEffect.transformer].
 * @return An [ISagaEffect] instance.
 */
fun <P, R> SagaEffect<P>.mapIgnoringNull(transformer: (P) -> R?):
  SagaEffect<R> where P : Any, R : Any {
  return this.transform(CommonEffects.mapIgnoringNull(transformer))
}

/**
 * Invoke a [PutEffect] on [this].
 * @receiver See [PutEffect.source].
 * @param P The source emission type.
 * @param actionCreator See [PutEffect.actionCreator].
 * @return A [SagaEffect] instance.
 */
fun <P> SagaEffect<P>.putInStore(actionCreator: (P) -> IReduxAction):
  SagaEffect<Any> where P : Any {
  return this.transform(CommonEffects.putInStore(actionCreator))
}

/**
 * Invoke [thenSwitchTo] with a single [value].
 * @receiver See [ThenEffect.source1].
 * @param R The first source emission type.
 * @param R2 The result emission type.
 * @param value See [ThenEffect.source2].
 * @return A [SagaEffect] instance.
 */
fun <R, R2> SagaEffect<R>.thenSwitchToValue(value: R2) where R : Any, R2 : Any = this.map { value }
