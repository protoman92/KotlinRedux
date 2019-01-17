/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.store

import org.junit.Assert
import org.junit.Test
import org.swiften.redux.core.BaseReduxStoreTest
import org.swiften.redux.core.DefaultReduxAction
import org.swiften.redux.core.IReduxAction

/** Created by haipham on 2019/01/15 */
class DefaultActionReduxStoreTest : BaseReduxStoreTest() {
  private var lastAction: IReduxAction? = null

  override fun createStore() = DefaultActionReduxStore(SimpleReduxStore(0) { p, a ->
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
