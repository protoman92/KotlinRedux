/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.android.sample

import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import org.swiften.redux.core.EmptyJob
import org.swiften.redux.core.IReduxAction
import org.swiften.redux.saga.common.SagaInput
import org.swiften.redux.saga.common.SagaMonitor
import java.util.Collections.synchronizedList

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
    val monitor = SagaMonitor()
    val result = MusicResult(1000, arrayListOf())
    val dispatched = synchronizedList(arrayListOf<IReduxAction>())

    val api = object : ISearchAPI<MusicResult?> {
      override fun searchMusicStore(query: String, limit: Int): MusicResult? = result
    }

    Redux.Saga.searchSaga(api, debounce = 0)
      .invoke(SagaInput({ a -> dispatched.add(a); EmptyJob }, { state }, monitor))
      .subscribe({})

    // When
    monitor.dispatch(Redux.Action.Search.UpdateQuery(null))

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
    val monitor = SagaMonitor()
    val error = Exception("Oops!")
    val dispatched = synchronizedList(arrayListOf<IReduxAction>())

    val api = object : ISearchAPI<MusicResult?> {
      override fun searchMusicStore(query: String, limit: Int): MusicResult? {
        throw error
      }
    }

    Redux.Saga.searchSaga(api, debounce = 0)
      .invoke(SagaInput({ a -> dispatched.add(a); EmptyJob }, { state }, monitor))
      .subscribe({})

    // When
    monitor.dispatch(Redux.Action.Search.UpdateLimit(null))

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
