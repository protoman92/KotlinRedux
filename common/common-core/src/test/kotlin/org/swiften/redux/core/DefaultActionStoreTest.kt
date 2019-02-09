/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.core

import org.junit.Assert.assertEquals
import org.junit.Test

/** Created by haipham on 2019/01/15 */
class DefaultActionStoreTest : BaseStoreTest() {
  private var lastAction: IReduxAction? = null
  private val defaultState = 0

  override fun createStore(): IReduxStore<Int> {
    return DefaultActionStore(this.defaultState) { p, a ->
      this.lastAction = a
      this.reducer()(p, a)
    }
  }

  @Test
  fun test_dispatchingDefaultActions_shouldSendThemInPipeline() {
    // Setup
    val store = this.createStore()
    val newState = this.defaultState + 1000

    // When
    store.dispatch(DefaultReduxAction.ReplaceState(newState))

    // Then
    assertEquals(store.lastState(), newState)
  }

  @Test
  fun test_deinitializingStore_shouldSendDeinitializeAction() {
    // Setup
    val store = this.createStore()

    // When
    store.deinitialize()

    // Then
    assertEquals(this.lastAction, DefaultReduxAction.Deinitialize)
  }
}
