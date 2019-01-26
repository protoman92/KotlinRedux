/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.saga.common

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import org.swiften.redux.core.IReduxAction

/** Created by haipham on 2019/01/26/1 */
/** Cast the emission from the current [ISagaEffect] to [R2] if possible */
fun <R2> SagaEffect<*>.cast(cls: Class<R2>) = this.filter { cls.isInstance(it) }.map { cls.cast(it) }

/** Cast the emission from the current [ISagaEffect] to [R2] if possible */
inline fun <reified R2> SagaEffect<*>.cast() = this.filter { it is R2 }.map { it as R2 }

/** Catch [Throwable] from upstream with [catcher] */
fun <R> SagaEffect<R>.catchError(catcher: (Throwable) -> R) =
  this.transform(CommonEffects.catchError(catcher))

/** Invoke a [SuspendCatchErrorEffect] on [this] */
fun <R> SagaEffect<R>.catchErrorSuspend(catcher: suspend CoroutineScope.(Throwable) -> R) =
  this.transform(CommonEffects.catchErrorSuspend(catcher))

/** Invoke a [AsyncCatchErrorEffect] on [this] */
fun <R> SagaEffect<R>.catchErrorAsync(catcher: suspend CoroutineScope.(Throwable) -> Deferred<R>) =
  this.transform(CommonEffects.catchErrorAsync(catcher))

/** Invoke a [DelayEffect] on [this] */
fun <R> SagaEffect<R>.delay(millis: Long) = this.transform(
  CommonEffects.delay(
    millis
  )
)

/** Invoke a [DoOnValueEffect] on [this] */
fun <R> SagaEffect<R>.doOnValue(performer: (R) -> Unit) =
  this.transform(CommonEffects.doOnValue(performer))

/** Invoke a [FilterEffect] on the current [ISagaEffect] */
fun <R> SagaEffect<R>.filter(predicate: (R) -> Boolean) =
  this.transform(CommonEffects.filter(predicate))

/** Invoke a [MapEffect] on the current [ISagaEffect] */
fun <R, R2> SagaEffect<R>.map(transformer: (R) -> R2) =
  this.transform(CommonEffects.map(transformer))

/** Invoke a [AsyncMapEffect] on [this] */
fun <P, R> SagaEffect<P>.mapAsync(transformer: suspend CoroutineScope.(P) -> Deferred<R>) =
  this.transform(CommonEffects.mapAsync(transformer))

/** Invoke a [PutEffect] on the current [CommonEffects] */
fun <P> SagaEffect<P>.put(actionCreator: (P) -> IReduxAction) =
  this.transform(CommonEffects.put(actionCreator))

/** Invoke a [RetryEffect] on [this] */
fun <R> SagaEffect<R>.retry(times: Long) = this.transform(
  CommonEffects.retry(
    times
  )
)

fun <R> SagaEffect<R>.retry(times: Int) = this.retry(times.toLong())

/** Invoke a [SuspendMapEffect] on [this] */
fun <P, R> SagaEffect<P>.mapSuspend(transformer: suspend CoroutineScope.(P) -> R) =
  this.transform(CommonEffects.mapSuspend(transformer))

/** Invoke a [ThenEffect] on the current [ISagaEffect] */
fun <R, R2, R3> SagaEffect<R>.then(
  effect: ISagaEffect<R2>,
  combiner: Function2<R, R2, R3>
) = CommonEffects.then(this, effect, combiner)

/** Invoke a [ThenEffect] but ignore emissions from [this] */
fun <R, R2> SagaEffect<R>.then(effect: ISagaEffect<R2>) = this.then(effect) { _, b -> b }

/** Invoke [then] with a single [value] */
fun <R, R2> SagaEffect<R>.justThen(value: R2) = this.map { value }

/** Invoke a [TimeoutEffect] on [this] */
fun <R> SagaEffect<R>.timeout(millis: Long) = this.transform(
  CommonEffects.timeout(
    millis
  )
)
