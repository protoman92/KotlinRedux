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
    val onAction: ReduxDispatcher
    fun catchError(fallback: (Throwable) -> T): IOutput<T>
    fun delay(delayMillis: Long): IOutput<T>
    fun dispose()
    fun doOnValue(perform: (T) -> Unit): IOutput<T>
    fun debounce(timeMillis: Long): IOutput<T>
    fun filter(selector: (T) -> Boolean): IOutput<T>
    fun <T2> map(transform: (T) -> T2): IOutput<T2>
    fun <T2 : Any> mapSuspend(transform: suspend CoroutineScope.(T) -> T2): IOutput<T2>
    fun <T2 : Any> mapAsync(transform: suspend CoroutineScope.(T) -> Deferred<T2>): IOutput<T2>
    fun <T2> flatMap(transform: (T) -> IOutput<T2>): IOutput<T2>
    fun <T2> switchMap(transform: (T) -> IOutput<T2>): IOutput<T2>
    fun nextValue(timeoutMillis: Long): T?
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
