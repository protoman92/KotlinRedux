/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.android.sample

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import org.swiften.redux.core.EmptyJob
import org.swiften.redux.core.IReduxAction

/** Created by viethai.pham on 2019/02/20 */
open class ReduxSagaTest {
  protected val delay = 500L
}

class SearchSagaTest : ReduxSagaTest() {
  private val state = object : ISearchStateProvider {
    override val search = SearchFragment.S("Query", true, ResultLimit.TEN)
  }

  @Test
  fun `Search saga should work in success case`() {
    // Setup
    val result = MusicResult(1000, arrayListOf())
    val dispatched = arrayListOf<IReduxAction>()

    val api = object : ISearchAPI<MusicResult?> {
      override fun searchMusicStore(query: String, limit: Int): MusicResult? = result
    }

    val outputStream = Redux.Saga.searchSaga(api, debounce = 0)
      .invoke(GlobalScope, state) { a -> dispatched.add(a); EmptyJob }

    outputStream.subscribe({})

    // When
    outputStream.onAction(Redux.Action.Search.UpdateQuery(null))

    runBlocking {
      delay(this@SearchSagaTest.delay)

      // Then
      assertEquals(
        dispatched, arrayListOf(
          Redux.Action.Search.SetLoading(true),
          Redux.Action.UpdateMusicResult(result),
          Redux.Action.Search.SetLoading(false)
        )
      )
    }
  }

  @Test
  fun `Search saga should work in error case`() {
    // Setup
    val error = Exception("Oops!")
    val dispatched = arrayListOf<IReduxAction>()

    val api = object : ISearchAPI<MusicResult?> {
      override fun searchMusicStore(query: String, limit: Int): MusicResult? {
        throw error
      }
    }

    val outputStream = Redux.Saga.searchSaga(api, debounce = 0)
      .invoke(GlobalScope, state) { a -> dispatched.add(a); EmptyJob }

    outputStream.subscribe({})

    // When
    outputStream.onAction(Redux.Action.Search.UpdateLimit(null))

    runBlocking {
      delay(this@SearchSagaTest.delay)

      // Then
      assertEquals(
        dispatched, arrayListOf(
          Redux.Action.Search.SetLoading(true),
          Redux.Action.Search.SetLoading(false)
        )
      )
    }
  }
}
