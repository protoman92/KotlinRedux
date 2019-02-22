/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.saga.common

import org.swiften.redux.core.EmptyJob
import org.swiften.redux.core.IActionDispatcher
import org.swiften.redux.core.IDispatcherProvider
import java.util.concurrent.locks.ReentrantReadWriteLock
import kotlin.concurrent.read
import kotlin.concurrent.write

/** Created by viethai.pham on 2019/02/22 */
/** Monitors all [ISagaOutput] and calls [ISagaOutput.onAction] when an action arrives. */
interface ISagaMonitor : IDispatcherProvider {
  /** Store [dispatch] with a unique [id]. */
  fun set(id: String, dispatch: IActionDispatcher)

  /** Remove an [IActionDispatcher] with [id]. */
  fun remove(id: String)
}

/** Default implementation of [ISagaMonitor]. */
class SagaMonitor : ISagaMonitor {
  private val lock by lazy { ReentrantReadWriteLock() }
  private val dispatchers by lazy { hashMapOf<String, IActionDispatcher>() }

  override val dispatch: IActionDispatcher = { a ->
    this@SagaMonitor.lock.read { this@SagaMonitor.dispatchers.forEach { it.value(a) }; EmptyJob }
  }

  override fun set(id: String, dispatch: IActionDispatcher) {
    this.lock.write { this.dispatchers[id] = dispatch }
  }

  override fun remove(id: String) {
    this.lock.write { this.dispatchers.remove(id) }
  }
}
