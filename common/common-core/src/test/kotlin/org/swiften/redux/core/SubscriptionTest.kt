/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.core

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.concurrent.atomic.AtomicInteger

/** Created by haipham on 2019/01/15 */
class SubscriptionTest {
  @Test
  @Suppress("ForEachParameterNotUsed")
  fun `Unsubscribing from multiple threads should perform only once`() {
    // Setup
    val unsubCount = AtomicInteger()
    val subscription = ReduxSubscription(-1L) { unsubCount.incrementAndGet() }

    // When
    (0 until 100).forEach { GlobalScope.launch { subscription.unsubscribe() } }

    runBlocking {
      delay(1000)

      // Then
      assertEquals(unsubCount.get(), 1)
    }
  }

  @Test
  @Suppress("ForEachParameterNotUsed")
  fun `Unsubscribing from composite subscription should perform only once`() {
    // Setup
    var unsubCount = 0
    val subscriptions = (0 until 100).map { ReduxSubscription(it.toLong()) { unsubCount += 1 } }
    val composite = CompositeReduxSubscription.create(-1L)

    runBlocking {
      // When
      subscriptions.forEach { GlobalScope.launch { composite.add(it) } }
      delay(1000)
      (0 until 100).forEach { GlobalScope.launch { composite.unsubscribe() } }
      delay(1000)

      // Then
      assertEquals(unsubCount, subscriptions.size)
    }
  }

  @Test
  fun `Removing subscription from composite should work`() {
    // Setup
    var unsubCount = 0
    val subscription = ReduxSubscription(123L) { unsubCount += 1 }
    val composite = CompositeReduxSubscription.create(-1L)

    // When
    composite.remove(subscription)
    composite.unsubscribe()

    // Then
    assertEquals(unsubCount, 0)
  }
}
