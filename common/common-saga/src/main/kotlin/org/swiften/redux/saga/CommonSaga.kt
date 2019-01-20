/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.saga

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.GlobalScope
import org.swiften.redux.core.IReduxAction
import org.swiften.redux.core.IReduxDispatcher
import org.swiften.redux.core.IReduxStateGetter
import org.swiften.redux.core.IReduxStore

/** Created by haipham on 2019/01/07 */
/**
 * Abstraction for Redux saga that handles [IReduxAction] in the pipeline. Faithful to the original
 * redux-saga for React.js, classes that implement [IReduxSagaEffect] should be able to intercept
 * [IReduxAction] that are sent with [IReduxDispatcher], via [ReduxSagaMiddleware].
 */
typealias IReduxSagaEffect<R> = Function1<Input, IReduxSagaOutput<R>>

/** Transform one [ReduxSagaEffect] to another */
typealias IReduxSagaEffectTransformer<R, R2> = Function1<ReduxSagaEffect<R>, ReduxSagaEffect<R2>>

/** [Input] for an [IReduxSagaEffect], which exposes a [IReduxStore]'s internal functionalities */
class Input(
  val scope: CoroutineScope = GlobalScope,
  val stateGetter: IReduxStateGetter<*>,
  val dispatch: IReduxDispatcher
)

/**
 * Stream values for a [IReduxSagaEffect]. This stream has functional operators that can transform
 * emitted values.
 */
interface IReduxSagaOutput<T> {
  /** Trigger every time an [IReduxAction] arrives */
  val onAction: IReduxDispatcher

  /** Catch error with [catcher] */
  fun catchError(catcher: (Throwable) -> T): IReduxSagaOutput<T>

  /** Catch error with suspending [catcher] */
  fun catchErrorSuspend(catcher: suspend CoroutineScope.(Throwable) -> T): IReduxSagaOutput<T>

  /** Catch error with async [catcher] */
  fun catchErrorAsync(
    catcher: suspend CoroutineScope.(Throwable) -> Deferred<T>
  ): IReduxSagaOutput<T>

  /** Delay each emission by [delayMillis] */
  fun delay(delayMillis: Long): IReduxSagaOutput<T>

  /** Terminate internal stream subscriptions */
  fun dispose()

  /** Perform some side effects with [performer] on each emission */
  fun doOnValue(performer: (T) -> Unit): IReduxSagaOutput<T>

  /**
   * Debounce emissions by [timeMillis], i.e. accepting only values that are [timeMillis] away
   * from their immediate predecessors.
   */
  fun debounce(timeMillis: Long): IReduxSagaOutput<T>

  /** Filter out values that do not pass [predicate] */
  fun filter(predicate: (T) -> Boolean): IReduxSagaOutput<T>

  /** Map emissions from [T] to [T2] with [transform] */
  fun <T2> map(transform: (T) -> T2): IReduxSagaOutput<T2>

  /** Map emissions from [T] to [T2] with suspending [transform] */
  fun <T2> mapSuspend(transform: suspend CoroutineScope.(T) -> T2): IReduxSagaOutput<T2>

  /** Map emissions from [T] to [T2] with async [transform] */
  fun <T2> mapAsync(transform: suspend CoroutineScope.(T) -> Deferred<T2>): IReduxSagaOutput<T2>

  /** Flatten emissions from [IReduxSagaOutput] produced by [transform] */
  fun <T2> flatMap(transform: (T) -> IReduxSagaOutput<T2>): IReduxSagaOutput<T2>

  /** Retry [times] if a [Throwable] is encountered */
  fun retry(times: Long): IReduxSagaOutput<T>

  /**
   * Flatten emissions from [IReduxSagaOutput] produced by [transform], but accept only those from
   * the latest one.
   */
  fun <T2> switchMap(transform: (T) -> IReduxSagaOutput<T2>): IReduxSagaOutput<T2>

  /** Time out if no element is emitted within [millis]] */
  fun timeout(millis: Long): IReduxSagaOutput<T>

  /** Get the next [T], but only if it arrives before [timeoutMillis] */
  fun nextValue(timeoutMillis: Long): T?

  /** Subscribe to values with [onValue], and error with [onError] */
  fun subscribe(onValue: (T) -> Unit, onError: (Throwable) -> Unit = { })
}

/** Abstract class to allow better interfacing with Java */
abstract class ReduxSagaEffect<R> : IReduxSagaEffect<R> {
  /** Call [IReduxSagaEffect] with convenience parameters for testing */
  fun invoke(scope: CoroutineScope, state: Any, dispatch: IReduxDispatcher) =
    this.invoke(Input(scope, { state }, dispatch))

  fun <R2> transform(transformer: IReduxSagaEffectTransformer<R, R2>):
    ReduxSagaEffect<R2> = transformer(this)
}
