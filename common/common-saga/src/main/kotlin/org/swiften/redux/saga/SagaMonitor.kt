/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.saga

import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import org.swiften.redux.core.BatchAwaitable
import org.swiften.redux.core.IActionDispatcher
import org.swiften.redux.core.IAwaitable
import org.swiften.redux.core.IDispatcherProvider
import org.swiften.redux.core.NoopActionDispatcher
import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.withLock

/** Created by viethai.pham on 2019/02/22 */
/**
 * Monitors all [ISagaOutput] and calls [ISagaOutput.onAction] when an action arrives. This is
 * the only way to notify all [ISagaOutput] of new actions.
 */
interface ISagaMonitor : IDispatcherProvider {
  /** Store [dispatch] with a unique [id]. */
  fun addOutputDispatcher(id: Long, dispatch: IActionDispatcher)

  /** Remove the associated [IActionDispatcher] instance. */
  fun removeOutputDispatcher(id: Long)
}

/** Default implementation of [ISagaMonitor]. */
class SagaMonitor(private val scheduler: Scheduler) : ISagaMonitor {
  constructor() : this(scheduler = Schedulers.computation())

  private val lock = ReentrantLock()
  private val dispatchers = HashMap<Long, IActionDispatcher>()
  private val disposable = CompositeDisposable()

  override val dispatch: IActionDispatcher = { a ->
    this@SagaMonitor.lock.withLock {
      @Suppress("UNCHECKED_CAST")
      BatchAwaitable(awaitables = this@SagaMonitor.dispatchers.map { it.value(a) }) { runnable ->
        Single.create<Unit> { runnable.run(); it.onSuccess(Unit) }
          .subscribeOn(this@SagaMonitor.scheduler)
          .subscribe()
          .also { this@SagaMonitor.disposable.add(it) }
      } as IAwaitable<Any>
    }
  }

  override fun addOutputDispatcher(id: Long, dispatch: IActionDispatcher) {
    this.lock.withLock {
      if (dispatch != NoopActionDispatcher) { this.dispatchers[id] = dispatch }
    }
  }

  override fun removeOutputDispatcher(id: Long) {
    this.lock.withLock {
      this.dispatchers.remove(id)
    }
  }
}
