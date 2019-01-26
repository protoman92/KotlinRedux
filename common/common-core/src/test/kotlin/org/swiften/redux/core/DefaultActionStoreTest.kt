/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.core

import org.junit.Assert
import org.junit.Test

/** Created by haipham on 2019/01/15 */
class DefaultActionStoreTest : BaseStoreTest() {
  private var lastAction: IReduxAction? = null

  override fun createStore() =
    DefaultActionStore(ThreadSafeStore(0) { p, a ->
      this.lastAction = a; this.reducer()(p, a)
    })

  @Test
  fun test_deinitializingStore_shouldSendDeinitializeAction() {
    // Setup
    val store = this.createStore()

    // / When
    store.deinitialize()

    // Then
    Assert.assertEquals(lastAction, DefaultReduxAction.Deinitialize)
  }
}
