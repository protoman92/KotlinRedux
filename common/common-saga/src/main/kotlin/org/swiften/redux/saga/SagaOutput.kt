/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.saga

import io.reactivex.Flowable
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.rx2.rxSingle
import org.swiften.redux.core.DefaultUniqueIDProvider
import org.swiften.redux.core.IActionDispatcher
import org.swiften.redux.core.IUniqueIDProvider
import org.swiften.redux.core.NoopActionDispatcher
import java.util.concurrent.TimeUnit

/** Created by haipham on 2018/12/22 */
/**
 * This is the default implementation of [ISagaOutput]. Every time a new [SagaOutput] is created,
 * [monitor] will keep track of its [onAction] to call on [ISagaMonitor.dispatch], and when said
 * [SagaOutput] is disposed of, [monitor] will remove the reference.
 *
 * We do not call [onAction] directly on action because [onAction] is not actually passed down
 * from [SagaOutput] to its children (e.g. via transformation methods such as [map]). Each
 * [SagaOutput] instance has its own [onAction] that is not related to any other.
 * [ISagaMonitor.dispatch] is the only way to call all stored [onAction].
 * @param T The result emission type.
 * @param monitor A [ISagaMonitor] instance that is used to track created [ISagaOutput]. This
 * [ISagaMonitor] implementation must be able to handle multi-threaded
 * [ISagaMonitor.addOutputDispatcher] and [ISagaMonitor.removeOutputDispatcher] events.
 * @param stream A [Flowable] instance.
 * @param onAction See [ISagaOutput.onAction].
 */
class SagaOutput<T : Any>(
  private val monitor: ISagaMonitor,
  stream: Flowable<T>,
  override val onAction: IActionDispatcher = NoopActionDispatcher
) : ISagaOutput<T>, IUniqueIDProvider by DefaultUniqueIDProvider() {
  companion object {
    /**
     * Create a [ISagaOutput] from [creator] using [rxSingle].
     * @param T The emission value type.
     * @param scope A [CoroutineScope] instance.
     * @param monitor See [SagaOutput.monitor].
     * @param creator Suspending function that produces [T].
     * @return An [ISagaOutput] instance.
     */
    fun <T> from(
      scope: CoroutineScope,
      monitor: ISagaMonitor,
      creator: suspend CoroutineScope.() -> T
    ): ISagaOutput<T> where T : Any {
      return SagaOutput(monitor, rxSingle { creator() }.toFlowable())
    }

    /**
     * See [Flowable.merge]. Produces a [SagaOutput] whose [SagaOutput.stream] triggers any time
     * a [SagaOutput.stream] from [outputs] emits a value.
     * @param T The emission value type.
     * @param outputs A [Collection] of [SagaOutput].
     * @return A [SagaOutput] instance.
     */
    fun <T> merge(monitor: ISagaMonitor, outputs: Collection<SagaOutput<T>>): SagaOutput<T> where T : Any {
      return SagaOutput(monitor, Flowable.merge(outputs.map { it.stream }))
    }
  }

  private val stream: Flowable<T>
  private val disposable by lazy { CompositeDisposable() }

  init {
    val monitor = this.monitor
    val subscriberID = this.uniqueID
    monitor.addOutputDispatcher(subscriberID, this.onAction)

    this.stream = stream
      /** This will handle onError/onComplete. */
      .doOnTerminate { monitor.removeOutputDispatcher(subscriberID) }
      /** This will handle cancel events (e.g. take effect publisher calling onComplete. */
      .doOnCancel { monitor.removeOutputDispatcher(subscriberID) }
  }

  private fun <T2> with(newStream: Flowable<T2>): ISagaOutput<T2> where T2 : Any {
    return SagaOutput(this.monitor, newStream)
  }

  override fun <T2> flatMap(transform: (T) -> ISagaOutput<T2>): ISagaOutput<T2> where T2 : Any {
    return this.with(this.stream.flatMap { (transform(it) as SagaOutput<T2>).stream })
  }

  override fun <T2> switchMap(transform: (T) -> ISagaOutput<T2>): ISagaOutput<T2> where T2 : Any {
    return this.with(this.stream.switchMap { (transform(it) as SagaOutput<T2>).stream })
  }

  override fun debounce(millis: Long, scheduler: Scheduler): ISagaOutput<T> {
    return this.with(this.stream.debounce(millis, TimeUnit.MILLISECONDS, scheduler))
  }

  override fun await(): T = this.stream.blockingFirst()

  override fun await(defaultValue: T): T = this.stream.blockingFirst(defaultValue)

  override fun awaitFor(timeoutMillis: Long): T = this.stream
    .timeout(timeoutMillis, TimeUnit.MILLISECONDS)
    .blockingFirst()

  override fun subscribe(onValue: (T) -> Unit, onError: (Throwable) -> Unit): Disposable {
    return this.stream.subscribe(onValue, onError)
  }
}
