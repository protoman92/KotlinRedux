/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.core

import java.util.concurrent.atomic.AtomicBoolean
import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.withLock

/** Created by haipham on 2019/01/24/1 */
/**
 * Represents a subscription object that can be unsubscribed from. Each [IReduxSubscription]
 * must also have an [uniqueID] that can be used to perform selective unsubscription.
 */
interface IReduxSubscription : IUniqueIDProvider {
  /**
   * Check if this [IReduxSubscription] is unsubscribed.
   * @return The subscription status.
   */
  fun isUnsubscribed(): Boolean

  /** Unsubscribe from this [IReduxSubscription]. */
  fun unsubscribe()
}

interface ICompositeReduxSubscription : IReduxSubscription {
  /**
   * Add an [IReduxSubscription].
   * @param subscription An [IReduxSubscription] instance.
   */
  fun add(subscription: IReduxSubscription)

  /**
   * Remove an [IReduxSubscription] instance whose [IReduxSubscription.uniqueID] equals [uniqueID].
   * @param subscribeId A [String] value.
   * @return The remove [IReduxSubscription], if any.
   */
  fun remove(subscribeId: String): IReduxSubscription?
}

/**
 * Use this class to perform some [unsubscribe] logic. For e.g.: terminate a [IReduxSubscription]
 * from [IReduxStore.subscribe].
 * @param uniqueID See [IReduxSubscription.uniqueID].
 * @param _unsubscribe Function that contains unsubscription logic.
 */
class ReduxSubscription(
  override val uniqueID: String,
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
 * @param uniqueID See [IReduxSubscription.uniqueID].
 */
class CompositeReduxSubscription private constructor(override val uniqueID: String) : ICompositeReduxSubscription {
  companion object {
    /**
     * Create a [CompositeReduxSubscription] and provide it with locking mechanisms.
     * @param uniqueID See [CompositeReduxSubscription.uniqueID].
     * @return An [ICompositeReduxSubscription] instance.
     */
    fun create(uniqueID: String): ICompositeReduxSubscription {
      return object : ICompositeReduxSubscription {
        private val lock = ReentrantLock()
        private val subscription = CompositeReduxSubscription(uniqueID)
        override val uniqueID: String get() = this.subscription.uniqueID

        override fun isUnsubscribed(): Boolean {
          return this.lock.withLock { this.subscription.isUnsubscribed() }
        }

        override fun unsubscribe() {
          return this.lock.withLock { this.subscription.unsubscribe() }
        }

        override fun add(subscription: IReduxSubscription) {
          return this.lock.withLock { this.subscription.add(subscription) }
        }

        override fun remove(subscribeId: String): IReduxSubscription? {
          return this.lock.withLock { this.subscription.remove(subscribeId) }
        }
      }
    }
  }

  private val subscriptions = hashMapOf<String, IReduxSubscription>()
  private var _isUnsubscribed = false

  override fun isUnsubscribed() = this._isUnsubscribed

  override fun unsubscribe() {
    if (!this._isUnsubscribed) {
      this.subscriptions.forEach { it.value.unsubscribe() }
      this.subscriptions.clear()
      this._isUnsubscribed = true
    }
  }

  override fun add(subscription: IReduxSubscription) {
    this.subscriptions[subscription.uniqueID] = subscription
  }

  override fun remove(subscribeId: String): IReduxSubscription? {
    return this.subscriptions.remove(subscribeId)
  }
}

/**
 * Remove an [IReduxSubscription] from [subscriptions].
 * @receiver An [ICompositeReduxSubscription] instance.
 * @param subscription An [IReduxSubscription] instance.
 * @return The remove [IReduxSubscription], if any.
 */
fun ICompositeReduxSubscription.remove(subscription: IReduxSubscription): IReduxSubscription? {
  return this.remove(subscription.uniqueID)
}
