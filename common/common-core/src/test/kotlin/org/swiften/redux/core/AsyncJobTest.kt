/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.core

import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertNotEquals
import org.junit.Test
import java.util.Random

/** Created by haipham on 2019/02/17 */
class AsyncJobTest {
  @Test
  fun `Dispatching with async middleware should work correctly`() {
    // Setup
    class Action(val value: Int) : IReduxAction

    val store = applyMiddlewares<Int>(
      createAsyncMiddleware(),
      createAsyncMiddleware(),
      createAsyncMiddleware()
    )(FinalStore(-1) { s, a -> when (a) {
      is Action -> a.value
      else -> s
    } })

    val rand = Random()

    // When
    val iterations = 1000

    val jobs = (0 until iterations).map {
      GlobalScope.launch(Dispatchers.IO, start = CoroutineStart.LAZY) {
        store.dispatch(Action(rand.nextInt(1000))).await()
      }
    }

    runBlocking {
      jobs.forEach { it.join() }

      // Then
      assertNotEquals(store.lastState(), -1)
    }
  }
}
