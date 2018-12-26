/*
 * Copyright (c) haipham 2018. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.saga

import kotlinx.coroutines.channels.Channel
import org.testng.Assert
import org.testng.annotations.Test

/**
 * Created by haipham on 2018/12/26.
 */
class LifecycleChannelTest {
  @Test
  fun `Lifecycle channels should invoke side effects on lifecycle change`() {
    /// Setup
    var closed = 0
    val channel = LifecycleChannel(Channel<Int>()) { closed += 1 }

    /// When
    channel.close()
    channel.close()
    channel.close()

    /// Then
    Assert.assertEquals(closed, 1)
  }
}
