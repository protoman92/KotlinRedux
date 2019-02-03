/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.saga.common

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.GlobalScope
import org.swiften.redux.core.IActionDispatcher
import org.swiften.redux.core.IReduxAction
import org.swiften.redux.core.IReduxStore
import org.swiften.redux.core.IStateGetter

/** Created by haipham on 2019/01/07 */
/**
 * Abstraction for Redux saga that handles [IReduxAction] in the pipeline. Faithful to the original
 * redux-saga for React.js, classes that implement [ISagaEffect] should be able to intercept
 * [IReduxAction] that are sent with [IActionDispatcher], via [SagaMiddleware].
 * @param R The result of [ISagaOutput] emission.
 */
typealias ISagaEffect<R> = (SagaInput) -> ISagaOutput<R>

/**
 * Transform one [SagaEffect] to another.
 * @param R The emission type of the input [SagaEffect].
 * @param R2 The emission type of the output [SagaEffect].
 */
typealias ISagaEffectTransformer<R, R2> = (SagaEffect<R>) -> SagaEffect<R2>

/**
 * [SagaInput] for an [ISagaEffect], which exposes a [IReduxStore]'s internal functionalities.
 * @param scope A [CoroutineScope] instance.
 * @param stateGetter See [IReduxStore.lastState].
 * @param dispatch See [IReduxStore.dispatch].
 */
class SagaInput(
  val scope: CoroutineScope = GlobalScope,
  val stateGetter: IStateGetter<*>,
  val dispatch: IActionDispatcher
)

/**
 * Stream values for a [ISagaEffect]. This stream has functional operators that can transform
 * emitted values.
 * @param T The emission value type.
 */
interface ISagaOutput<T> {
  /** Trigger every time an [IReduxAction] arrives. */
  val onAction: IActionDispatcher

  /**
   * Catch error with [catcher].
   * @param catcher Function that catches [Throwable] and returns [T].
   * @return An [ISagaOutput] instance.
   */
  fun catchError(catcher: (Throwable) -> T): ISagaOutput<T>

  /**
   * Catch error with suspending [catcher].
   * @param catcher Function that catches [Throwable] in a [CoroutineScope] and returns [T].
   * @return An [ISagaOutput] instance.
   */
  fun catchSuspend(catcher: suspend CoroutineScope.(Throwable) -> T): ISagaOutput<T>

  /**
   * Catch error with async [catcher].
   * @param catcher Function that catches [Throwable] in a [CoroutineScope] and returns [T].
   * @return An [ISagaOutput] instance.
   */
  fun catchAsync(catcher: suspend CoroutineScope.(Throwable) -> Deferred<T>): ISagaOutput<T>

  /**
   * Delay each emission by [millis].
   * @param millis Delay time in milliseconds.
   * @return An [ISagaOutput] instance.
   */
  fun delay(millis: Long): ISagaOutput<T>

  /** Terminate internal stream subscriptions. */
  fun dispose()

  /**
   * Perform some side effects with [performer] on each emission.
   * @param performer Function that takes [T] to perform some side effects.
   * @return An [ISagaOutput] instance.
   */
  fun doOnValue(performer: (T) -> Unit): ISagaOutput<T>

  /**
   * Debounce emissions by [millis], i.e. accepting only values that are [millis] away from their
   * immediate predecessors.
   * @param millis Debounce time in milliseconds.
   * @return An [ISagaOutput] instance.
   */
  fun debounce(millis: Long): ISagaOutput<T>

  /**
   * Filter out values that do not pass [predicate].
   * @param predicate Function that takes [T] and performs some logic checking, returning [Boolean].
   * @return An [ISagaOutput] instance.
   */
  fun filter(predicate: (T) -> Boolean): ISagaOutput<T>

  /**
   * Map emissions from [T] to [T2] with [transform].
   * @param T2 The type of emission of the resulting [ISagaOutput].
   * @param transform Function that maps from [T] to [T2].
   * @return An [ISagaOutput] instance.
   */
  fun <T2> map(transform: (T) -> T2): ISagaOutput<T2>

  /**
   * Map emissions from [T] to [T2] with suspending [transform].
   * @param T2 The type of emission of the resulting [ISagaOutput].
   * @param transform Function that maps from [T] to [T2] in a [CoroutineScope].
   * @return An [ISagaOutput] instance.
   */
  fun <T2> mapSuspend(transform: suspend CoroutineScope.(T) -> T2): ISagaOutput<T2>

  /**
   * Map emissions from [T] to [T2] with async [transform].
   * @param T2 The type of emission of the resulting [ISagaOutput].
   * @param transform Function that maps from [T] to [T2] in a [CoroutineScope].
   * @return An [ISagaOutput] instance.
   */
  fun <T2> mapAsync(transform: suspend CoroutineScope.(T) -> Deferred<T2>): ISagaOutput<T2>

  /**
   * Flatten emissions from [ISagaOutput] produced by [transform].
   * @param T2 The type of emission of the resulting [ISagaOutput].
   * @param transform Function that flat maps from [T] to [ISagaOutput] in a [CoroutineScope].
   * @return An [ISagaOutput] instance.
   */
  fun <T2> flatMap(transform: (T) -> ISagaOutput<T2>): ISagaOutput<T2>

  /**
   * Retry [times] if a [Throwable] is encountered.
   * @param times The number of times to retry.
   * @return An [ISagaOutput] instance.
   */
  fun retry(times: Long): ISagaOutput<T>

  /**
   * Flatten emissions from [ISagaOutput] produced by [transform], but accept only those from
   * the latest one.
   * @param T2 The type of emission of the resulting [ISagaOutput].
   * @param transform Function that switch maps from [T] to [ISagaOutput] in a [CoroutineScope].
   * @return An [ISagaOutput] instance.
   */
  fun <T2> switchMap(transform: (T) -> ISagaOutput<T2>): ISagaOutput<T2>

  /**
   * Time out if no element is emitted within [millis].
   * @param millis Timeout time in milliseconds.
   * @return An [ISagaOutput] instance.
   */
  fun timeout(millis: Long): ISagaOutput<T>

  /**
   * Get the next [T], but only if it arrives before [timeoutMillis].
   * @param timeoutMillis Timeout time in milliseconds.
   * @return An [ISagaOutput] instance.
   */
  fun nextValue(timeoutMillis: Long): T?

  /**
   * Subscribe to values with [onValue], and error with [onError].
   * @param onValue Function that takes [T] and performs some side effects.
   * @param onError Function that takes [Throwable] and performs some side effects.
   */
  fun subscribe(onValue: (T) -> Unit, onError: (Throwable) -> Unit = { })
}

/**
 * Abstract class to allow better interfacing with Java.
 * @param R The type of emission value.
 */
abstract class SagaEffect<R> : ISagaEffect<R> {
  /**
   * Call [ISagaEffect] with convenience parameters for testing.
   * @param scope A [CoroutineScope] instance.
   * @param state See [SagaInput.stateGetter].
   * @param dispatch See [SagaInput.dispatch].
   * @return An [ISagaOutput] instance.
   */
  fun invoke(scope: CoroutineScope, state: Any, dispatch: IActionDispatcher): ISagaOutput<R> {
    return this.invoke(SagaInput(scope, { state }, dispatch))
  }

  /**
   * Transform into another [SagaEffect] with [transformer].
   * @param R2 The emission type of the resulting [SagaEffect].
   * @param transformer A [ISagaEffectTransformer] instance.
   * @return A [SagaEffect] instance.
   */
  fun <R2> transform(transformer: ISagaEffectTransformer<R, R2>): SagaEffect<R2> = transformer(this)
}
