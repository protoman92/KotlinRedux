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
import org.swiften.redux.core.DefaultSubscriberIDProvider
import org.swiften.redux.core.EmptyJob
import org.swiften.redux.core.IActionDispatcher
import org.swiften.redux.core.ISubscriberIDProvider
import org.swiften.redux.saga.common.ISagaMonitor
import org.swiften.redux.saga.common.ISagaOutput
import java.util.concurrent.TimeUnit

/** Created by haipham on 2018/12/22 */
/**
 * This is the default implementation of [ISagaOutput].
 * @param T The result emission type.
 * @param scope A [CoroutineScope] instance.
 * @param monitor A [ISagaMonitor] instance that is used to track created [ISagaOutput]. This
 * [ISagaMonitor] implementation must be able to handle multi-threaded
 * [ISagaMonitor.setOutputDispatcher] and [ISagaMonitor.removeOutputDispatcher] events.
 * @param stream A [Flowable] instance.
 * @param onAction See [ISagaOutput.onAction].
 */
class SagaOutput<T : Any>(
  private val scope: CoroutineScope,
  private val monitor: ISagaMonitor,
  stream: Flowable<T>,
  private val onDispose: () -> Unit = {},
  override val onAction: IActionDispatcher
) : ISagaOutput<T>,
  ISubscriberIDProvider by DefaultSubscriberIDProvider(),
  CoroutineScope by scope {
  companion object {
    /**
     * Create a [ISagaOutput] from [creator] using [CoroutineScope.rxSingle].
     * @param T The emission value type.
     * @param scope A [CoroutineScope] instance.
     * @param creator Suspending function that produces [T].
     * @return An [ISagaOutput] instance.
     */
    fun <T> from(
      scope: CoroutineScope,
      monitor: ISagaMonitor,
      creator: suspend CoroutineScope.() -> T
    ): ISagaOutput<T> where T : Any {
      return SagaOutput(scope, monitor, scope.rxSingle { creator() }.toFlowable()) { EmptyJob }
    }

    /**
     * See [Flowable.merge]. Produces a [SagaOutput] whose [SagaOutput.stream] triggers any time
     * a [SagaOutput.stream] from [outputs] emits a value.
     * @param T The emission value type.
     * @param scope A [CoroutineScope] instance.
     * @param outputs A [Collection] of [SagaOutput].
     * @return A [SagaOutput] instance.
     */
    fun <T> merge(
      scope: CoroutineScope,
      monitor: ISagaMonitor,
      outputs: Collection<SagaOutput<T>>
    ): SagaOutput<T> where T : Any {
      return SagaOutput(scope, monitor, Flowable.merge(outputs.map { it.stream })) { EmptyJob }
    }
  }

  private val stream: Flowable<T>
  private val disposable by lazy { CompositeDisposable() }

  init {
    val monitor = this.monitor
    val subscriberID = this.uniqueSubscriberID
    monitor.setOutputDispatcher(subscriberID, this.onAction)

    this.stream = stream
      /** This will handle onError/onComplete. */
      .doOnTerminate { monitor.removeOutputDispatcher(subscriberID) }
      /** This will handle cancel events (e.g. take effect publisher calling onComplete. */
      .doOnCancel { monitor.removeOutputDispatcher(subscriberID) }
  }

  private fun <T2> with(newStream: Flowable<T2>): ISagaOutput<T2> where T2 : Any {
    return SagaOutput(this.scope, this.monitor, newStream, { this.dispose() }) { EmptyJob }
  }

  override fun <T2> map(transform: (T) -> T2): ISagaOutput<T2> where T2 : Any {
    return this.with(this.stream.map(transform))
  }

  override fun <T2> mapSuspend(transform: suspend CoroutineScope.(T) -> T2): ISagaOutput<T2> where T2 : Any {
    return this.with(this.stream.flatMap { v ->
      this@SagaOutput.rxSingle { transform(this, v) }.toFlowable()
    })
  }

  override fun <T2> mapAsync(
    transform: suspend CoroutineScope.(T) -> Deferred<T2>
  ): ISagaOutput<T2> where T2 : Any {
    return this.mapSuspend { transform(it).await() }
  }

  override fun doOnValue(performer: (T) -> Unit): ISagaOutput<T> {
    return this.with(this.stream.doOnNext(performer))
  }

  override fun <T2> flatMap(transform: (T) -> ISagaOutput<T2>): ISagaOutput<T2> where T2 : Any {
    return this.with(this.stream.flatMap { (transform(it) as SagaOutput<T2>).stream })
  }

  override fun <T2> switchMap(transform: (T) -> ISagaOutput<T2>): ISagaOutput<T2> where T2 : Any {
    return this.with(this.stream.switchMap { (transform(it) as SagaOutput<T2>).stream })
  }

  override fun filter(predicate: (T) -> Boolean): ISagaOutput<T> {
    return this.with(this.stream.filter(predicate))
  }

  override fun delay(millis: Long): ISagaOutput<T> {
    if (millis <= 0) { return this }
    return this.with(this.stream.delay(millis, TimeUnit.MILLISECONDS))
  }

  override fun debounce(millis: Long): ISagaOutput<T> {
    if (millis <= 0) { return this }
    return this.with(this.stream.debounce(millis, TimeUnit.MILLISECONDS))
  }

  override fun catchError(catcher: (Throwable) -> T): ISagaOutput<T> {
    return this.with(this.stream.onErrorReturn(catcher))
  }

  override fun catchError(secondOutput: ISagaOutput<T>): ISagaOutput<T> {
    return this.with(this.stream.onErrorResumeNext((secondOutput as SagaOutput<T>).stream))
  }

  override fun catchSuspend(catcher: suspend CoroutineScope.(Throwable) -> T): ISagaOutput<T> {
    return this.with(this.stream.onErrorResumeNext { e: Throwable ->
      this@SagaOutput.rxSingle { catcher(this, e) }.toFlowable()
    })
  }

  override fun catchAsync(catcher: suspend CoroutineScope.(Throwable) -> Deferred<T>): ISagaOutput<T> {
    return this.catchSuspend { catcher(it).await() }
  }

  override fun dispose() {
    this.disposable.clear(); this.onDispose()
  }

  override fun ifEmpty(defaultValue: T): ISagaOutput<T> {
    return this.with(this.stream.defaultIfEmpty(defaultValue))
  }

  override fun ifEmpty(secondOutput: ISagaOutput<T>): ISagaOutput<T> {
    return this.with(this.stream.switchIfEmpty((secondOutput as SagaOutput<T>).stream))
  }

  override fun retry(times: Long) = this.with(this.stream.retry(times))

  override fun timeout(millis: Long) = this.with(this.stream.timeout(millis, TimeUnit.MILLISECONDS))

  override fun await(): T = this.stream.blockingFirst()

  override fun await(defaultValue: T): T = this.stream.blockingFirst(defaultValue)

  override fun awaitFor(timeoutMillis: Long): T = this.stream
    .timeout(timeoutMillis, TimeUnit.MILLISECONDS)
    .blockingFirst()

  override fun subscribe(onValue: (T) -> Unit, onError: (Throwable) -> Unit) {
    this.disposable.add(this.stream.subscribe(onValue, onError))
  }
}
