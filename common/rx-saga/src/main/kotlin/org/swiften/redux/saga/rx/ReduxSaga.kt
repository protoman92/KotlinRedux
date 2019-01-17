/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.saga.rx

import io.reactivex.Flowable
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.rx2.rxSingle
import org.swiften.redux.core.IReduxDispatcher
import org.swiften.redux.saga.IReduxSagaOutput
import java.util.concurrent.TimeUnit

/** Created by haipham on 2018/12/22 */
/** Use this for [rxSingle] to avoid forcing [T] to be subtype of [Any] */
internal data class Boxed<T>(val value: T)

/** @see [IReduxSagaOutput] */
class ReduxSagaOutput<T>(
  private val scope: CoroutineScope,
  private val stream: Flowable<T>,
  override val onAction: IReduxDispatcher
) : IReduxSagaOutput<T>, CoroutineScope by scope {
  internal var source: ReduxSagaOutput<*>? = null
  private var onDispose: () -> Unit = { }
  private val disposable by lazy { CompositeDisposable() }

  private fun <T2> with(newStream: Flowable<T2>): IReduxSagaOutput<T2> {
    val result = ReduxSagaOutput(this.scope, newStream, this.onAction)
    result.source = this
    result.onDispose = { this.dispose() }
    return result
  }

  override fun <T2> map(transform: (T) -> T2) = this.with(this.stream.map(transform))

  override fun <T2> mapSuspend(transform: suspend CoroutineScope.(T) -> T2) =
    this.with(this.stream.flatMap { v ->
      this@ReduxSagaOutput.rxSingle { Boxed(transform(this, v)) }.map(Boxed<T2>::value).toFlowable()
    })

  override fun <T2> mapAsync(transform: suspend CoroutineScope.(T) -> Deferred<T2>) =
    this.mapSuspend { transform(it).await() }

  override fun doOnValue(performer: (T) -> Unit) =
    this.with(this.stream.doOnNext(performer))

  override fun <T2> flatMap(transform: (T) -> IReduxSagaOutput<T2>) =
    this.with(this.stream.flatMap { (transform(it) as ReduxSagaOutput<T2>).stream })

  override fun <T2> switchMap(transform: (T) -> IReduxSagaOutput<T2>) =
    this.with(this.stream.switchMap { (transform(it) as ReduxSagaOutput<T2>).stream })

  override fun filter(predicate: (T) -> Boolean) =
    this.with(this.stream.filter(predicate))

  override fun delay(delayMillis: Long): IReduxSagaOutput<T> {
    if (delayMillis <= 0) { return this }
    return this.with(this.stream.delay(delayMillis, TimeUnit.MILLISECONDS))
  }

  override fun debounce(timeMillis: Long): IReduxSagaOutput<T> {
    if (timeMillis <= 0) { return this }
    return this.with(this.stream.debounce(timeMillis, TimeUnit.MILLISECONDS))
  }

  override fun catchError(catcher: (Throwable) -> T) =
    this.with(this.stream.onErrorReturn(catcher))

  override fun catchErrorSuspend(catcher: suspend CoroutineScope.(Throwable) -> T) =
    this.with(this.stream.onErrorResumeNext { e: Throwable ->
      this@ReduxSagaOutput.rxSingle { Boxed(catcher(this, e)) }.map { it.value }.toFlowable()
    })

  override fun catchErrorAsync(catcher: suspend CoroutineScope.(Throwable) -> Deferred<T>) =
    this.catchErrorSuspend { catcher(it).await() }

  override fun dispose() { this.disposable.clear(); this.onDispose() }

  override fun retry(times: Long) = this.with(this.stream.retry(times))

  override fun timeout(millis: Long) = this.with(this.stream.timeout(millis, TimeUnit.MILLISECONDS))

  override fun nextValue(timeoutMillis: Long) = try {
    this.stream
      .timeout(timeoutMillis, TimeUnit.MILLISECONDS)
      .blockingFirst()
  } catch (e: Throwable) { null }

  override fun subscribe(onValue: (T) -> Unit, onError: (Throwable) -> Unit) {
    this.disposable.add(this.stream.subscribe(onValue, onError))
  }
}

/** Options for [TakeEffect] */
data class TakeEffectOptions(internal val debounceMillis: Long = 0)
