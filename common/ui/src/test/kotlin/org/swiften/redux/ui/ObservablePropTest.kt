/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.ui

import org.testng.Assert
import org.testng.annotations.Test

/** Created by haipham on 2019/01/16 */
class ObservablePropTest {
  @Test
  @Suppress("UNUSED_VALUE")
  fun `Lateinit observable prop should work correctly`() {
    // Setup
    var setCount = 0
    var prop by LateinitObservableProp<Int> { setCount += 1 }

    // When
    prop = 1
    prop = 2
    prop = 3

    // Then
    Assert.assertEquals(setCount, 3)
    Assert.assertEquals(prop, 3)
  }
}
