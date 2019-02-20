/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.core

import java.util.concurrent.atomic.AtomicBoolean
import java.util.concurrent.locks.ReentrantReadWriteLock
import kotlin.concurrent.read
import kotlin.concurrent.write

/** Created by haipham on 2019/01/24/1 */
/**
 * Represents a subscription object that can be unsubscribed from. Each [IReduxSubscription]
 * must also have an [id] that can be used to perform selective unsubscription.
 */
interface IReduxSubscription : ISubscriberIDProvider {
  /**
   * Check if this [IReduxSubscription] is unsubscribed.
   * @return The subscription status.
   */
  fun isUnsubscribed(): Boolean

  /** Unsubscribe from this [IReduxSubscription]. */
  fun unsubscribe()
}

/**
 * Use this class to perform some [unsubscribe] logic. For e.g.: terminate a [IReduxSubscription]
 * from [IReduxStore.subscribe].
 * @param uniqueSubscriberID See [IReduxSubscription.uniqueSubscriberID].
 * @param _unsubscribe Function that contains unsubscription logic.
 */
class ReduxSubscription(
  override val uniqueSubscriberID: String,
  private val _unsubscribe: () -> Unit
) : IReduxSubscription {
  companion object {
    private const val EMPTY_ID = "EMPTY"

    /**
     * Mock [ReduxSubscription] that is used every time there is no meaningful subscription logic
     * to be unsubscribed on.
     */
    val EMPTY = ReduxSubscription(this.EMPTY_ID) {}
  }

  private val _isUnsubscribed = AtomicBoolean()
  override fun isUnsubscribed() = this._isUnsubscribed.get()
  override fun unsubscribe() { if (!this._isUnsubscribed.getAndSet(true)) this._unsubscribe() }
}

/**
 * Composite [IReduxSubscription] that may contain other [IReduxSubscription], and when
 * [IReduxSubscription.unsubscribe] is called, all the children [IReduxSubscription] will also
 * be unsubscribed from.
 * @param uniqueSubscriberID See [IReduxSubscription.uniqueSubscriberID].
 */
class CompositeReduxSubscription(override val uniqueSubscriberID: String) : IReduxSubscription {
  private val subscriptions = mutableMapOf<String, IReduxSubscription>()
  private val lock = ReentrantReadWriteLock()
  private var _isUnsubscribed = false

  override fun isUnsubscribed() = this._isUnsubscribed

  override fun unsubscribe() {
    if (!this.lock.read { this._isUnsubscribed }) {
      this.lock.write {
        this.subscriptions.forEach { it.value.unsubscribe() }
        this.subscriptions.clear()
        this._isUnsubscribed = true
      }
    }
  }

  /**
   * Add an [IReduxSubscription] to [subscriptions].
   * @param subscription An [IReduxSubscription] instance.
   */
  fun add(subscription: IReduxSubscription) {
    this.lock.write { this.subscriptions[subscription.uniqueSubscriberID] = subscription }
  }

  /**
   * Remove an [IReduxSubscription] instance whose [IReduxSubscription.id] equals [id].
   * @param subscribeId A [String] value.
   * @return The remove [IReduxSubscription], if any.
   */
  fun remove(subscribeId: String): IReduxSubscription? {
    return this.lock.write { this.subscriptions.remove(subscribeId) }
  }

  /**
   * Remove an [IReduxSubscription] from [subscriptions].
   * @param subscription An [IReduxSubscription] instance.
   * @return The remove [IReduxSubscription], if any.
   */
  fun remove(subscription: IReduxSubscription): IReduxSubscription? {
    return this.remove(subscription.uniqueSubscriberID)
  }
}
