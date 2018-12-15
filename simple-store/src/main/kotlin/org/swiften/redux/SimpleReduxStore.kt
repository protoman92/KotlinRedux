package org.swiften.redux

import java.util.concurrent.locks.ReentrantReadWriteLock
import kotlin.concurrent.read
import kotlin.concurrent.write

/**
 * Created by haipham on 2018/12/16.
 */
/**
 * [SimpleReduxStore] is a store implementation that supports thread-safe
 * accesses and modifications. Pass in the initial [state] and the store's
 * [reducer] in the constructor.
 */
class SimpleReduxStore<State>(
  internal var state: State,
  internal val reducer: Redux.IReducer<State>
): Redux.IStore<State> {
  private val lock = ReentrantReadWriteLock()
  private val subscribers = HashMap<String, (State) -> Unit>()

  override fun lastState(): State {
    return this.lock.read { this.state }
  }

  override fun dispatch(action: Redux.IAction) {
    this.lock.write { this.state = this.reducer(this.state, action) }
    return this.lock.read { this.subscribers.forEach { it.value(this.state) } }
  }

  override fun subscribe(
    subscriberId: String,
    callback: (State) -> Unit
  ): Redux.Subscription {
    this.lock.write { this.subscribers[subscriberId] = callback }

    // Relay the last state to this subscriber.
    this.lock.read { callback(this.state) }

    return Redux.Subscription {
      this.lock.write { this.subscribers.remove(subscriberId) }
    }
  }
}