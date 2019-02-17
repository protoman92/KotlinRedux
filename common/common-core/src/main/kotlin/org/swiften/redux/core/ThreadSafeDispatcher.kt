/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.core

import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.withLock

/** Created by haipham on 2019/02/17 */
/**
 * Use this [IActionDispatcher] to wrap a base [IActionDispatcher] and dispatch [IReduxAction]
 * instances in a thread-safe manner.
 * @param lock A [ReentrantLock] instance.
 * @param dispatch The [IActionDispatcher] to be wrapped.
 */
class ThreadSafeDispatcher(
  private val lock: ReentrantLock = ReentrantLock(),
  private val dispatch: IActionDispatcher
) : IActionDispatcher {
  override fun invoke(p1: IReduxAction): IAsyncJob {
    return this.lock.withLock { this@ThreadSafeDispatcher.dispatch(p1) }
  }
}
