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
 * Cast the emission from the current [ISagaEffect] to [R2] if possible.
 * @receiver A [SagaEffect] instance.
 * @param R2 The result emission type.
 * @param cls The [Class] of [R2].
 * @return A [SagaEffect] instance.
 */
fun <R2> SagaEffect<*>.cast(cls: Class<R2>) = this.filter { cls.isInstance(it) }.map { cls.cast(it) }

/**
 * Cast the emission from the current [ISagaEffect] to [R2] if possible.
 * @receiver A [SagaEffect] instance.
 * @param R2 The result emission type.
 * @return A [SagaEffect] instance.
 */
inline fun <reified R2> SagaEffect<*>.cast() = this.filter { it is R2 }.map { it as R2 }

/**
 * Catch [Throwable] from upstream with [catcher].
 * @receiver A [SagaEffect] instance.
 * @param R The result emission type.
 * @param catcher Function that catches [Throwable].
 * @return A [SagaEffect] instance.
 */
fun <R> SagaEffect<R>.catchError(catcher: (Throwable) -> R): SagaEffect<R> {
  return this.transform(CommonEffects.catchError(catcher))
}

/**
 * Invoke a [SuspendCatchErrorEffect] on [this].
 * @receiver A [SagaEffect] instance.
 * @param R The result emission type.
 * @param catcher Function that catches [Throwable].
 * @return A [SagaEffect] instance.
 */
fun <R> SagaEffect<R>.catchSuspend(catcher: suspend CoroutineScope.(Throwable) -> R): SagaEffect<R> {
  return this.transform(CommonEffects.catchErrorSuspend(catcher))
}

/**
 * Invoke a [AsyncCatchErrorEffect] on [this].
 * @receiver A [SagaEffect] instance.
 * @param R The result emission type.
 * @param catcher Function that catches [Throwable].
 * @return A [SagaEffect] instance.
 */
fun <R> SagaEffect<R>.catchAsync(catcher: suspend CoroutineScope.(Throwable) -> Deferred<R>): SagaEffect<R> {
  return this.transform(CommonEffects.catchErrorAsync(catcher))
}

/**
 * Invoke a [DelayEffect] on [this].
 * @receiver A [SagaEffect] instance.
 * @param R The result emission type.
 * @param millis The delay time in milliseconds.
 * @return A [SagaEffect] instance.
 */
fun <R> SagaEffect<R>.delay(millis: Long) = this.transform(CommonEffects.delay(millis))

/**
 * Invoke a [DoOnValueEffect] on [this].
 * @receiver A [SagaEffect] instance.
 * @param R The result emission type.
 * @param performer Function that performs side effects on [R].
 * @return A [SagaEffect] instance.*
 */
fun <R> SagaEffect<R>.doOnValue(performer: (R) -> Unit): SagaEffect<R> {
  return this.transform(CommonEffects.doOnValue(performer))
}

/**
 * Invoke a [FilterEffect] on [this].
 * @receiver A [SagaEffect] instance.
 * @param R The result emission type.
 * @param predicate Function that performs logic checks on [R].
 * @return A [SagaEffect] instance.
 */
fun <R> SagaEffect<R>.filter(predicate: (R) -> Boolean): SagaEffect<R> {
  return this.transform(CommonEffects.filter(predicate))
}

/**
 * Invoke a [MapEffect] on the current [ISagaEffect].
 * @receiver A [SagaEffect] instance.
 * @param R The source emission type.
 * @param R2 The result emission type.
 * @param transformer Function that transforms [R] to [R2].
 * @return A [SagaEffect] instance.
 */
fun <R, R2> SagaEffect<R>.map(transformer: (R) -> R2): SagaEffect<R2> {
  return this.transform(CommonEffects.map(transformer))
}

/**
 * Invoke a [AsyncMapEffect] on [this].
 * @receiver A [SagaEffect] instance.
 * @param P The source emission type.
 * @param R The result emission type.
 * @param transformer Function that transforms [R] to [R2].
 * @return A [SagaEffect] instance.
 */
fun <P, R> SagaEffect<P>.mapAsync(transformer: suspend CoroutineScope.(P) -> Deferred<R>): SagaEffect<R> {
  return this.transform(CommonEffects.mapAsync(transformer))
}

/**
 * Invoke a [CompactMapEffect] on [this].
 * @receiver A [SagaEffect] instance.
 * @param R The result emission type.
 * @return An [ISagaEffect] instance.
 */
fun <R : Any> SagaEffect<R?>.compactMap() = this.transform<R>(CommonEffects.compactMap())

/**
 * Invoke a [PutEffect] on [this].
 * @receiver A [SagaEffect] instance.
 * @param P The source emission type.
 * @param actionCreator Function that creates [IReduxAction] from [P].
 * @return A [SagaEffect] instance.
 */
fun <P> SagaEffect<P>.put(actionCreator: (P) -> IReduxAction): SagaEffect<Any> {
  return this.transform(CommonEffects.put(actionCreator))
}

/**
 * Invoke a [RetryEffect] on [this].
 * @receiver A [SagaEffect] instance.
 * @param R The result emission type.
 * @param times The number of times to retry.
 * @return A [SagaEffect] instance.
 */
fun <R> SagaEffect<R>.retry(times: Long) = this.transform(CommonEffects.retry(times))

/**
 * Invoke a [RetryEffect] on [this].
 * @receiver A [SagaEffect] instance.
 * @param R The result emission type.
 * @param times The number of times to retry.
 * @return A [SagaEffect] instance.
 */
fun <R> SagaEffect<R>.retry(times: Int) = this.retry(times.toLong())

/**
 * Invoke a [SuspendMapEffect] on [this].
 * @receiver A [SagaEffect] instance.
 * @param P The source emission type.
 * @param R The result emission type.
 * @param transformer Function that transforms [R] to [R2].
 * @return A [SagaEffect] instance.
 */
fun <P, R> SagaEffect<P>.mapSuspend(transformer: suspend CoroutineScope.(P) -> R): SagaEffect<R> {
  return this.transform(CommonEffects.mapSuspend(transformer))
}

/**
 * Invoke a [ThenEffect] on [this].
 * @receiver A [SagaEffect] instance.
 * @param R The first source emission type.
 * @param R2 The second source emission type.
 * @param R3 The result emission type.
 * @param effect The [ISagaEffect] to perform after [this].
 * @param combiner Function that combines [R] and [R2] to produce [R3].
 * @return A [SagaEffect] instance.
 */
fun <R, R2, R3> SagaEffect<R>.then(
  effect: ISagaEffect<R2>,
  combiner: Function2<R, R2, R3>
): SagaEffect<R3> {
  return CommonEffects.then(this, effect, combiner)
}

/**
 * Invoke a [ThenEffect] but ignore emissions from [this]
 * @receiver A [SagaEffect] instance.
 * @param R The first source emission type.
 * @param R2 The result emission type.
 * @param effect The [ISagaEffect] to perform after [this].
 * @return A [SagaEffect] instance.
 */
fun <R, R2> SagaEffect<R>.then(effect: ISagaEffect<R2>) = this.then(effect) { _, b -> b }

/**
 * Invoke [then] with a single [value].
 * @receiver A [SagaEffect] instance.
 * @param R The first source emission type.
 * @param R2 The result emission type.
 * @param value The value to emit after [this].
 * @return A [SagaEffect] instance.
 */
fun <R, R2> SagaEffect<R>.justThen(value: R2) = this.map { value }

/**
 * Invoke a [TimeoutEffect] on [this].
 * @receiver A [SagaEffect] instance.
 * @param R The result emission type.
 * @param millis The timeout time in milliseconds.
 * @return A [SagaEffect] instance.
 */
fun <R> SagaEffect<R>.timeout(millis: Long) = this.transform(CommonEffects.timeout(millis))
