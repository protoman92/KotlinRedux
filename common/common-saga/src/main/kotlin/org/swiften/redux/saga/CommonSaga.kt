/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.saga

import io.reactivex.Scheduler
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import org.swiften.redux.core.IActionDispatcher
import org.swiften.redux.core.IAwaitable
import org.swiften.redux.core.IReduxAction
import org.swiften.redux.core.IReduxStore
import org.swiften.redux.core.IStateGetter
import org.swiften.redux.core.IUniqueIDProvider
import org.swiften.redux.core.NoopActionDispatcher

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
 * @param lastState See [IReduxStore.lastState].
 * @param dispatch See [IReduxStore.dispatch].
 */
class SagaInput(
  internal val dispatch: IActionDispatcher = NoopActionDispatcher,
  internal val lastState: IStateGetter<*> = {},
  val monitor: ISagaMonitor = SagaMonitor(),
  internal val scheduler: Scheduler = Schedulers.io()
)

/**
 * Stream values for a [ISagaEffect]. This stream has functional operators that can transform
 * emitted values.
 * @param T The emission value type.
 */
interface ISagaOutput<T> : IAwaitable<T>, IUniqueIDProvider where T : Any {
  /** Trigger every time an [IReduxAction] arrives. */
  val onAction: IActionDispatcher

  /**
   * Debounce emissions by [millis], i.e. accepting only values that are [millis] away from their
   * immediate predecessors.
   * @param millis Debounce time in milliseconds.
   * @param scheduler A [Scheduler] instance.
   * @return An [ISagaOutput] instance.
   */
  fun debounce(millis: Long, scheduler: Scheduler): ISagaOutput<T>

  /**
   * Flatten emissions from [ISagaOutput] produced by [transform].
   * @param T2 The type of emission of the resulting [ISagaOutput].
   * @param transform Function that flat maps from [T] to [ISagaOutput].
   * @return An [ISagaOutput] instance.
   */
  fun <T2> flatMap(transform: (T) -> ISagaOutput<T2>): ISagaOutput<T2> where T2 : Any

  /**
   * Flatten emissions from [ISagaOutput] produced by [transform], but accept only those from
   * the latest one.
   * @param T2 The type of emission of the resulting [ISagaOutput].
   * @param transform Function that switch maps from [T] to [ISagaOutput].
   * @return An [ISagaOutput] instance.
   */
  fun <T2> switchMap(transform: (T) -> ISagaOutput<T2>): ISagaOutput<T2> where T2 : Any

  /**
   * Subscribe to values with [onValue], and error with [onError].
   * @param onValue Function that takes [T] and performs some side effects.
   * @param onError Function that takes [Throwable] and performs some side effects.
   * @return A [Disposable] instance.
   */
  fun subscribe(onValue: (T) -> Unit, onError: (Throwable) -> Unit = { }): Disposable
}

/**
 * Abstract class to allow better interfacing with Java.
 * @param R The result emission type.
 */
abstract class SagaEffect<R> : ISagaEffect<R> where R : Any {
  /**
   * Transform into another [SagaEffect] with [transformer].
   * @param R2 The emission type of the resulting [SagaEffect].
   * @param transformer A [ISagaEffectTransformer] instance.
   * @return A [SagaEffect] instance.
   */
  fun <R2> transform(transformer: ISagaEffectTransformer<R, R2>): SagaEffect<R2> where R2 : Any {
    return transformer(this)
  }
}

/**
 * Represents a [SagaEffect] whose [ISagaOutput] only produces a single [R] instance, instead of
 * a stream.
 * @param R The result emission type.
 */
abstract class SingleEffect<R> : SagaEffect<R>() where R : Any {
  /**
   * See [ISagaOutput.await]. We invoke this [SingleEffect] with [input] then call
   * [ISagaOutput.await].
   * @param input A [SagaInput] instance.
   * @param defaultValue A [R] instance.
   * @return A [R] instance.
   */
  fun await(input: SagaInput, defaultValue: R): R {
    return this.invoke(input).await(defaultValue)
  }

  /**
   * See [ISagaOutput.await]. We invoke this [SingleEffect] with [input] then call
   * [ISagaOutput.await].
   * @param input A [SagaInput] instance.
   * @param timeoutMillis Timeout time in milliseconds.
   * @return A [R] instance.
   */
  fun awaitFor(input: SagaInput, timeoutMillis: Long): R {
    return this.invoke(input).awaitFor(timeoutMillis)
  }
}
