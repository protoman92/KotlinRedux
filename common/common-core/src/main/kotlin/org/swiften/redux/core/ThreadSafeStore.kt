/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.core

import java.util.concurrent.locks.ReentrantReadWriteLock
import kotlin.concurrent.read
import kotlin.concurrent.write

/** Created by haipham on 2018/12/16 */
/**
 * [ThreadSafeStore] is a [IReduxStore] implementation that supports thread-safe accesses and
 * modifications. Pass in the initial [state] and the store's [reducer] in the constructor.
 * @param GState The global state type.
 * @param state The initial default [GState] instance.
 * @param reducer A [IReducer] instance.
 */
class ThreadSafeStore<GState>(
  private var state: GState,
  override val reducer: IReducer<GState>
) : IReduxStore<GState> {
  private val lock = ReentrantReadWriteLock()
  private val subscribers = HashMap<String, (GState) -> Unit>()

  override val lastState = { this.lock.read { this.state } }

  override val dispatch: IActionDispatcher = { action ->
    this.lock.write { this.state = this.reducer(this.state, action) }
    this.lock.read { this.subscribers.forEach { it.value(this.state) } }
  }

  override val subscribe: IReduxSubscriber<GState> = { id, callback ->
    this.lock.write { this.subscribers[id] = callback }

    /** Relay the last [GState] to this subscriber */
    this.lock.read { callback(this.state) }
    ReduxSubscription(id) { this.lock.write { this.subscribers.remove(id) } }
  }

  override val deinitialize: IDeinitializer = {
    this.lock.write { this.subscribers.clear() }
  }
}
