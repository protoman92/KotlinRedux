/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.store

import org.swiften.redux.core.IActionDispatcher
import org.swiften.redux.core.IDeinitializer
import org.swiften.redux.core.IReducer
import org.swiften.redux.core.IReduxStore
import org.swiften.redux.core.IReduxSubscriber
import org.swiften.redux.core.ReduxSubscription
import java.util.concurrent.locks.ReentrantReadWriteLock
import kotlin.concurrent.read
import kotlin.concurrent.write

/** Created by haipham on 2018/12/16 */
/**
 * [ThreadSafeReduxStore] is a [IReduxStore] implementation that supports thread-safe accesses and
 * modifications. Pass in the initial [state] and the store's [reducer] in the constructor.
 */
class ThreadSafeReduxStore<State>(
  private var state: State,
  override val reducer: IReducer<State>
) : IReduxStore<State> {
  private val lock = ReentrantReadWriteLock()
  private val subscribers = HashMap<String, (State) -> Unit>()

  override val lastState = { this.lock.read { this.state } }

  override val dispatch: IActionDispatcher = { action ->
    this.lock.write { this.state = this.reducer(this.state, action) }
    this.lock.read { this.subscribers.forEach { it.value(this.state) } }
  }

  override val subscribe: IReduxSubscriber<State> = { id, callback ->
    this.lock.write { this.subscribers[id] = callback }

    /** Relay the last [State] to this subscriber */
    this.lock.read { callback(this.state) }
    ReduxSubscription(id) { this.lock.write { this.subscribers.remove(id) } }
  }

  override val deinitialize: IDeinitializer = {
    this.lock.write { this.subscribers.clear() }
  }
}
