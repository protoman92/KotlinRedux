/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.saga

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.GlobalScope
import org.swiften.redux.core.Redux
import org.swiften.redux.core.ReduxDispatcher
import org.swiften.redux.core.ReduxStateGetter

/** Created by haipham on 2019/01/07 */
/** Abstraction for Redux saga that handles [Redux.IAction] in the pipeline */
typealias ReduxSagaEffect<State, R> =
  Function1<CommonSaga.Input<State>, CommonSaga.IOutput<R>>

/** Top-level namespace for common Saga functionalities */
object CommonSaga {
  /**
   * [Input] for an [ReduxSagaEffect], which exposes a [Redux.IStore]'s internal
   * functionalities.
   */
  class Input<State>(
    val scope: CoroutineScope = GlobalScope,
    val stateGetter: ReduxStateGetter<State>,
    val dispatch: ReduxDispatcher
  )

  /**
   * Stream values for a [ReduxSagaEffect]. This stream has functional operators
   * that can transform emitted values.
   */
  interface IOutput<T> {
    /** Trigger every time an [Redux.IAction] arrives */
    val onAction: ReduxDispatcher

    /** Catch error with [fallback] */
    fun catchError(fallback: (Throwable) -> T): IOutput<T>

    /** Delay each emission by [delayMillis] */
    fun delay(delayMillis: Long): IOutput<T>

    /** Terminate internal stream subscriptions */
    fun dispose()

    /** Perform some side effects with [perform] on each emission */
    fun doOnValue(perform: (T) -> Unit): IOutput<T>

    /**
     * Debounce emissions by [timeMillis], i.e. accepting only values that are [timeMillis] away
     * from their immediate predecessors.
     */
    fun debounce(timeMillis: Long): IOutput<T>

    /** Filter out values that do not pass [selector] */
    fun filter(selector: (T) -> Boolean): IOutput<T>

    /** Map emissions from [T] to [T2] with [transform] */
    fun <T2> map(transform: (T) -> T2): IOutput<T2>

    /** Map emissions from [T] to [T2] with suspending [transform] */
    fun <T2 : Any> mapSuspend(transform: suspend CoroutineScope.(T) -> T2): IOutput<T2>

    /** Map emissions from [T] to [T2] with async [transform] */
    fun <T2 : Any> mapAsync(transform: suspend CoroutineScope.(T) -> Deferred<T2>): IOutput<T2>

    /** Flatten emissions from [IOutput] produced by [transform] */
    fun <T2> flatMap(transform: (T) -> IOutput<T2>): IOutput<T2>

    /**
     * Flatten emissions from [IOutput] produced by [transform], but accept only those from the
     * latest one.
     */
    fun <T2> switchMap(transform: (T) -> IOutput<T2>): IOutput<T2>

    /** Get the next [T], but only if it arrives before [timeoutMillis] */
    fun nextValue(timeoutMillis: Long): T?

    /** Subscribe to values with [onValue], and error with [onError] */
    fun subscribe(onValue: (T) -> Unit, onError: (Throwable) -> Unit = { })
  }
}

/**
 * Convenience method to call [ReduxSagaEffect] with convenience parameters
 * for testing.
 */
fun <State, R> ReduxSagaEffect<State, R>.invoke(
  scope: CoroutineScope,
  state: State,
  dispatch: ReduxDispatcher
) = this.invoke(CommonSaga.Input(scope, { state }, dispatch))
