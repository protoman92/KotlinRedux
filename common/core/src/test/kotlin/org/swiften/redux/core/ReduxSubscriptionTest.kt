/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.core

import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.testng.Assert
import org.testng.annotations.Test
import java.util.concurrent.atomic.AtomicInteger

/** Created by haipham on 2019/01/15 */
class ReduxSubscriptionTest {
  @Test
  fun `Unsubscribing from multiple threads should perform only once`() {
    // Setup
    val unsubCount = AtomicInteger()
    val subscription = ReduxSubscription { unsubCount.incrementAndGet() }

    // When
    val coroutines = (0 until 100).map {
      GlobalScope.launch(start = CoroutineStart.LAZY) { subscription.unsubscribe() }
    }

    coroutines.forEach { it.start() }

    runBlocking {
      delay(1000)

      // Then
      Assert.assertEquals(unsubCount.get(), 1)
    }
  }
}
