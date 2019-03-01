/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.saga.common

import org.swiften.redux.core.EmptyJob
import org.swiften.redux.core.IActionDispatcher
import org.swiften.redux.core.IDispatcherProvider
import org.swiften.redux.core.NoopActionDispatcher
import java.util.concurrent.ConcurrentHashMap

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
  private val dispatchers = ConcurrentHashMap<Long, IActionDispatcher>()

  override val dispatch: IActionDispatcher = { a ->
    this@SagaMonitor.dispatchers.forEach { it.value(a) }; EmptyJob
  }

  override fun addOutputDispatcher(id: Long, dispatch: IActionDispatcher) {
    if (dispatch != NoopActionDispatcher) {
      this.dispatchers[id] = dispatch
    }
  }

  override fun removeOutputDispatcher(id: Long) {
    this.dispatchers.remove(id)
  }
}
