/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.store

import org.swiften.redux.core.IReduxDeinitializer
import org.swiften.redux.core.IReduxDispatcher
import org.swiften.redux.core.IReduxReducer
import org.swiften.redux.core.IReduxStore
import org.swiften.redux.core.IReduxSubscriber
import org.swiften.redux.core.ReduxSubscription
import java.util.concurrent.locks.ReentrantReadWriteLock
import kotlin.concurrent.read
import kotlin.concurrent.write

/** Created by haipham on 2018/12/16 */
/**
 * [SimpleReduxStore] is a [IReduxStore] implementation that supports thread-safe accesses and
 * modifications. Pass in the initial [state] and the store's [reducer] in the constructor.
 */
class SimpleReduxStore<State>(
  private var state: State,
  override val reducer: IReduxReducer<State>
) : IReduxStore<State> {
  private val lock = ReentrantReadWriteLock()
  private val subscribers = HashMap<String, (State) -> Unit>()

  override val lastState = { this.lock.read { this.state } }

  override val dispatch: IReduxDispatcher = { action ->
    this.lock.write { this.state = this.reducer(this.state, action) }
    this.lock.read { this.subscribers.forEach { it.value(this.state) } }
  }

  override val subscribe: IReduxSubscriber<State> = { id, callback ->
    this.lock.write { this.subscribers[id] = callback }

    /** Relay the last [State] to this subscriber */
    this.lock.read { callback(this.state) }
    ReduxSubscription(id) { this.lock.write { this.subscribers.remove(id) } }
  }

  override val deinitialize: IReduxDeinitializer = {
    this.lock.write { this.subscribers.clear() }
  }
}
