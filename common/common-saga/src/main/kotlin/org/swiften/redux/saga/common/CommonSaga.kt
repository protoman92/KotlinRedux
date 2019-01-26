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
 */
typealias ISagaEffect<R> = (SagaInput) -> ISagaOutput<R>

/** Transform one [SagaEffect] to another */
typealias ISagaEffectTransformer<R, R2> = Function1<SagaEffect<R>, SagaEffect<R2>>

/** [SagaInput] for an [ISagaEffect], which exposes a [IReduxStore]'s internal functionalities */
class SagaInput(
  val scope: CoroutineScope = GlobalScope,
  val stateGetter: IStateGetter<*>,
  val dispatch: IActionDispatcher
)

/**
 * Stream values for a [ISagaEffect]. This stream has functional operators that can transform
 * emitted values.
 */
interface ISagaOutput<T> {
  /** Trigger every time an [IReduxAction] arrives */
  val onAction: IActionDispatcher

  /** Catch error with [catcher] */
  fun catchError(catcher: (Throwable) -> T): ISagaOutput<T>

  /** Catch error with suspending [catcher] */
  fun catchErrorSuspend(catcher: suspend CoroutineScope.(Throwable) -> T): ISagaOutput<T>

  /** Catch error with async [catcher] */
  fun catchErrorAsync(catcher: suspend CoroutineScope.(Throwable) -> Deferred<T>): ISagaOutput<T>

  /** Delay each emission by [delayMillis] */
  fun delay(delayMillis: Long): ISagaOutput<T>

  /** Terminate internal stream subscriptions */
  fun dispose()

  /** Perform some side effects with [performer] on each emission */
  fun doOnValue(performer: (T) -> Unit): ISagaOutput<T>

  /**
   * Debounce emissions by [timeMillis], i.e. accepting only values that are [timeMillis] away
   * from their immediate predecessors.
   */
  fun debounce(timeMillis: Long): ISagaOutput<T>

  /** Filter out values that do not pass [predicate] */
  fun filter(predicate: (T) -> Boolean): ISagaOutput<T>

  /** Map emissions from [T] to [T2] with [transform] */
  fun <T2> map(transform: (T) -> T2): ISagaOutput<T2>

  /** Map emissions from [T] to [T2] with suspending [transform] */
  fun <T2> mapSuspend(transform: suspend CoroutineScope.(T) -> T2): ISagaOutput<T2>

  /** Map emissions from [T] to [T2] with async [transform] */
  fun <T2> mapAsync(transform: suspend CoroutineScope.(T) -> Deferred<T2>): ISagaOutput<T2>

  /** Flatten emissions from [ISagaOutput] produced by [transform] */
  fun <T2> flatMap(transform: (T) -> ISagaOutput<T2>): ISagaOutput<T2>

  /** Retry [times] if a [Throwable] is encountered */
  fun retry(times: Long): ISagaOutput<T>

  /**
   * Flatten emissions from [ISagaOutput] produced by [transform], but accept only those from
   * the latest one.
   */
  fun <T2> switchMap(transform: (T) -> ISagaOutput<T2>): ISagaOutput<T2>

  /** Time out if no element is emitted within [millis]] */
  fun timeout(millis: Long): ISagaOutput<T>

  /** Get the next [T], but only if it arrives before [timeoutMillis] */
  fun nextValue(timeoutMillis: Long): T?

  /** Subscribe to values with [onValue], and error with [onError] */
  fun subscribe(onValue: (T) -> Unit, onError: (Throwable) -> Unit = { })
}

/** Abstract class to allow better interfacing with Java */
abstract class SagaEffect<R> : ISagaEffect<R> {
  /** Call [ISagaEffect] with convenience parameters for testing */
  fun invoke(scope: CoroutineScope, state: Any, dispatch: IActionDispatcher) =
    this.invoke(SagaInput(scope, { state }, dispatch))

  fun <R2> transform(transformer: ISagaEffectTransformer<R, R2>): SagaEffect<R2> = transformer(this)
}