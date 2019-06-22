/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.saga.common

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
class SagaMonitor : ISagaMonitor {
  private val lock = ReentrantLock()
  private val dispatchers = HashMap<Long, IActionDispatcher>()

  override val dispatch: IActionDispatcher = { a ->
    this@SagaMonitor.lock.withLock {
      @Suppress("UNCHECKED_CAST")
      BatchAwaitable(this@SagaMonitor.dispatchers.map { it.value(a) }) as IAwaitable<Any>
    }
  }

  override fun addOutputDispatcher(id: Long, dispatch: IActionDispatcher) {
    this.lock.withLock { if (dispatch != NoopActionDispatcher) { this.dispatchers[id] = dispatch } }
  }

  override fun removeOutputDispatcher(id: Long) {
    this.lock.withLock { this.dispatchers.remove(id) }
  }
}
