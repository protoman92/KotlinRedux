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
 * Cast the emission from the current [ISagaEffect] to [R] if possible.
 * @receiver A [SagaEffect] instance.
 * @param R The result emission type.
 * @param cls The [Class] of [R].
 * @return A [SagaEffect] instance.
 */
fun <R> SagaEffect<*>.castValue(cls: Class<R>): SagaEffect<R> where R : Any {
  return this.filter { cls.isInstance(it) }.map { cls.cast(it) }
}

/**
 * Cast the emission from the current [ISagaEffect] to [R] if possible.
 * @receiver A [SagaEffect] instance.
 * @param R The result emission type.
 * @return A [SagaEffect] instance.
 */
inline fun <reified R> SagaEffect<*>.castValue(): SagaEffect<R> where R : Any {
  return this.filter { it is R }.map { it as R }
}

/**
 * Catch [Throwable] from upstream with [catcher].
 * @receiver A [SagaEffect] instance.
 * @param R The result emission type.
 * @param catcher See [CatchErrorEffect.catcher].
 * @return A [SagaEffect] instance.
 */
fun <R> SagaEffect<R>.catchError(catcher: (Throwable) -> R): SagaEffect<R> where R : Any {
  return this.transform(CommonEffects.catchError(catcher))
}

/**
 * Invoke a [SuspendCatchErrorEffect] on [this].
 * @receiver A [SagaEffect] instance.
 * @param R The result emission type.
 * @param catcher See [SuspendCatchErrorEffect.catcher].
 * @return A [SagaEffect] instance.
 */
fun <R> SagaEffect<R>.catchSuspend(catcher: suspend CoroutineScope.(Throwable) -> R):
  SagaEffect<R> where R : Any {
  return this.transform(CommonEffects.catchErrorSuspend(catcher))
}

/**
 * Invoke a [AsyncCatchErrorEffect] on [this].
 * @receiver A [SagaEffect] instance.
 * @param R The result emission type.
 * @param catcher See [AsyncCatchErrorEffect.catcher].
 * @return A [SagaEffect] instance.
 */
fun <R> SagaEffect<R>.catchAsync(catcher: suspend CoroutineScope.(Throwable) -> Deferred<R>):
  SagaEffect<R> where R : Any {
  return this.transform(CommonEffects.catchErrorAsync(catcher))
}

/**
 * Invoke a [DelayEffect] on [this].
 * @receiver A [SagaEffect] instance.
 * @param R The result emission type.
 * @param millis See [DelayEffect.millis].
 * @return A [SagaEffect] instance.
 */
fun <R> SagaEffect<R>.delayUpstreamValue(millis: Long): SagaEffect<R> where R : Any {
  return this.transform(CommonEffects.delayUpstreamValue(millis))
}

/**
 * Invoke a [DoOnValueEffect] on [this].
 * @receiver A [SagaEffect] instance.
 * @param R The result emission type.
 * @param performer See [DoOnValueEffect.performer].
 * @return A [SagaEffect] instance.*
 */
fun <R> SagaEffect<R>.doOnValue(performer: (R) -> Unit): SagaEffect<R> where R : Any {
  return this.transform(CommonEffects.doOnValue(performer))
}

/**
 * Invoke a [FilterEffect] on [this].
 * @receiver A [SagaEffect] instance.
 * @param R The result emission type.
 * @param predicate See [FilterEffect.predicate].
 * @return A [SagaEffect] instance.
 */
fun <R> SagaEffect<R>.filter(predicate: (R) -> Boolean): SagaEffect<R> where R : Any {
  return this.transform(CommonEffects.filter(predicate))
}

/**
 * Invoke a [IfEmptyEffect] on [this].
 * @receiver A [SagaEffect] instance.
 * @param R The result emission type.
 * @param defaultValue See [IfEmptyEffect.defaultValue].
 * @return A [SagaEffect] instance.
 */
fun <R> SagaEffect<R>.ifEmptyThenReturnValue(defaultValue: () -> R): SagaEffect<R> where R : Any {
  return CommonEffects.ifEmptyThenReturnValue(defaultValue)(this)
}

/**
 * Invoke a [MapEffect] on the current [ISagaEffect].
 * @receiver A [SagaEffect] instance.
 * @param P The source emission type.
 * @param R The result emission type.
 * @param transformer See [MapEffect.transformer].
 * @return A [SagaEffect] instance.
 */
fun <P, R> SagaEffect<P>.map(transformer: (P) -> R): SagaEffect<R> where P : Any, R : Any {
  return this.transform(CommonEffects.map(transformer))
}

/**
 * Invoke a [AsyncMapEffect] on [this].
 * @receiver A [SagaEffect] instance.
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
 * @receiver A [SagaEffect] instance.
 * @param R The result emission type.
 * @param transformer See [CompactMapEffect.transformer].
 * @return An [ISagaEffect] instance.
 */
fun <P, R> SagaEffect<P>.mapIgnoringNull(transformer: (P) -> R?): SagaEffect<R> where P : Any, R : Any {
  return this.transform(CommonEffects.mapIgnoringNull(transformer))
}

/**
 * Invoke a [PutEffect] on [this].
 * @receiver A [SagaEffect] instance.
 * @param P The source emission type.
 * @param actionCreator See [PutEffect.actionCreator].
 * @return A [SagaEffect] instance.
 */
fun <P> SagaEffect<P>.putInStore(actionCreator: (P) -> IReduxAction): SagaEffect<Any> where P : Any {
  return this.transform(CommonEffects.putInStore(actionCreator))
}

/**
 * Invoke a [RetryEffect] on [this].
 * @receiver A [SagaEffect] instance.
 * @param R The result emission type.
 * @param times See [RetryEffect.times].
 * @return A [SagaEffect] instance.
 */
fun <R> SagaEffect<R>.retryMultipleTimes(times: Long): SagaEffect<R> where R : Any {
  return this.transform(CommonEffects.retryMultipleTimes(times))
}

/**
 * Invoke a [RetryEffect] on [this].
 * @receiver A [SagaEffect] instance.
 * @param R The result emission type.
 * @param times See [RetryEffect.times].
 * @return A [SagaEffect] instance.
 */
fun <R> SagaEffect<R>.retryMultipleTimes(times: Int) where R : Any = this.retryMultipleTimes(times.toLong())

/**
 * Invoke a [SuspendMapEffect] on [this].
 * @receiver A [SagaEffect] instance.
 * @param P The source emission type.
 * @param R The result emission type.
 * @param transformer See [SuspendMapEffect.transformer].
 * @return A [SagaEffect] instance.
 */
fun <P, R> SagaEffect<P>.mapSuspend(
  transformer: suspend CoroutineScope.(P) -> R
): SagaEffect<R> where P : Any, R : Any {
  return this.transform(CommonEffects.mapSuspend(transformer))
}

/**
 * Invoke a [ThenEffect] on [this].
 * @receiver A [SagaEffect] instance.
 * @param R The first source emission type.
 * @param R2 The second source emission type.
 * @param R3 The result emission type.
 * @param effect See [ThenEffect.source2].
 * @param combiner See [ThenEffect.combiner].
 * @return A [SagaEffect] instance.
 */
fun <R, R2, R3> SagaEffect<R>.thenSwitchToEffect(
  effect: ISagaEffect<R2>,
  combiner: Function2<R, R2, R3>
): SagaEffect<R3> where R : Any, R2 : Any, R3 : Any {
  return CommonEffects.then(this, effect, combiner)
}

/**
 * Invoke a [ThenEffect] but ignore emissions from [this]
 * @receiver A [SagaEffect] instance.
 * @param R The first source emission type.
 * @param R2 The result emission type.
 * @param effect See [ThenEffect.source2].
 * @return A [SagaEffect] instance.
 */
fun <R, R2> SagaEffect<R>.thenSwitchToEffect(effect: ISagaEffect<R2>): SagaEffect<R2> where R : Any, R2 : Any {
  return this.thenSwitchToEffect(effect) { _, b -> b }
}

/**
 * Invoke [thenSwitchToEffect] with a single [value].
 * @receiver A [SagaEffect] instance.
 * @param R The first source emission type.
 * @param R2 The result emission type.
 * @param value See [ThenEffect.source2].
 * @return A [SagaEffect] instance.
 */
fun <R, R2> SagaEffect<R>.thenSwitchToValue(value: R2) where R : Any, R2 : Any = this.map { value }

/**
 * Invoke a [TimeoutEffect] on [this].
 * @receiver A [SagaEffect] instance.
 * @param R The result emission type.
 * @param millis See [TimeoutEffect.millis].
 * @return A [SagaEffect] instance.
 */
fun <R> SagaEffect<R>.timeoutUntilFailure(millis: Long): SagaEffect<R> where R : Any {
  return this.transform(CommonEffects.timeoutUntilFailure(millis))
}
