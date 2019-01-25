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
interface IReduxSubscription {
  val id: String
  fun unsubscribe()
}

/**
 * Use this class to perform some [unsubscribe] logic. For e.g.: terminate a [IReduxSubscription]
 * from [IReduxStore.subscribe].
 */
class ReduxSubscription(
  override val id: String,
  private val _unsubscribe: () -> Unit
) : IReduxSubscription {
  private val isUnsubscribed = AtomicBoolean()

  override fun unsubscribe() { if (!this.isUnsubscribed.getAndSet(true)) this._unsubscribe() }
}

/**
 * Composite [IReduxSubscription] that may contain other [IReduxSubscription], and when
 * [IReduxSubscription.unsubscribe] is called, all the children [IReduxSubscription] will also
 * be unsubscribed from.
 */
class CompositeReduxSubscription(override val id: String) : IReduxSubscription {
  private val subscriptions = mutableMapOf<String, IReduxSubscription>()
  private val lock = ReentrantReadWriteLock()
  private var isUnsubscribed = false

  override fun unsubscribe() {
    if (!this.lock.read { this.isUnsubscribed }) {
      this.lock.write {
        this.subscriptions.forEach { it.value.unsubscribe() }
        this.subscriptions.clear()
        this.isUnsubscribed = true
      }
    }
  }

  fun add(subscription: IReduxSubscription) {
    this.lock.write { this.subscriptions[subscription.id] = subscription }
  }

  fun remove(subscribeId: String) = this.lock.write { this.subscriptions.remove(subscribeId) }
  fun remove(subscription: IReduxSubscription) = this.remove(subscription.id)
}
