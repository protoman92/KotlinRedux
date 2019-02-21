/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.android.dagger.business1

import kotlinx.coroutines.delay
import org.swiften.redux.core.IReducer
import org.swiften.redux.core.IReduxAction
import org.swiften.redux.saga.common.SagaEffect
import org.swiften.redux.saga.rx.SagaEffects.await
import org.swiften.redux.saga.rx.SagaEffects.mergeAll
import org.swiften.redux.saga.rx.SagaEffects.putInStore
import org.swiften.redux.saga.rx.SagaEffects.takeLatest
import java.io.Serializable

/** Created by viethai.pham on 2019/02/21 */
object Business1Redux {
  data class State(
    val parent: ParentFragment1.S = ParentFragment1.S(),
    val search: SearchView1.S = SearchView1.S()
  ) : Serializable

  sealed class Action : IReduxAction {
    data class SetLoading(val loading: Boolean) : Action()
    data class SetQuery(val query: String?) : Action()
  }

  object Reducer : IReducer<State, Action> {
    override fun invoke(p1: State, p2: Action): State {
      return when (p2) {
        is Action.SetLoading -> p1.copy(parent = p1.parent.copy(loading = p2.loading))
        is Action.SetQuery -> p1.copy(search = p1.search.copy(query = p2.query))
      }
    }
  }

  object Saga {
    fun allSagas(): SagaEffect<Any> {
      return takeLatest(Action::class, { Unit }) { activeSagas() }
    }

    private fun activeSagas(): SagaEffect<Any> {
      return mergeAll(searchSaga())
    }

    private fun searchSaga(): SagaEffect<Any> {
      return takeLatest(Action.SetQuery::class, { it.query }) { query ->
        await<Any> { input ->
          putInStore(Action.SetLoading(true)).await(input)

          try {
            delay(2000)
          } finally {
            putInStore(Action.SetLoading(false)).await(input)
          }
        }
      }
    }
  }
}
