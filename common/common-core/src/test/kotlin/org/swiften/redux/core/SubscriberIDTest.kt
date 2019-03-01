/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.core

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.Collections.synchronizedList

/** Created by viethai.pham on 2019/03/01 */
class SubscriberIDTest {
  @Test
  fun `Creating unique IDs with default ID provider should not create duplicate IDs`() {
    // Setup
    val iteration = 1000000
    val idProviders = synchronizedList(arrayListOf<IUniqueIDProvider>())

    // When
    val jobs = (0 until iteration).map {
      GlobalScope.launch(Dispatchers.IO) { idProviders.add(DefaultUniqueIDProvider()) }
    }

    runBlocking {
      jobs.joinAll()

      // Then
      val generatedIDs = idProviders.map { it.uniqueID }.toSet()
      assertEquals(generatedIDs.size, iteration)
    }
  }
}
