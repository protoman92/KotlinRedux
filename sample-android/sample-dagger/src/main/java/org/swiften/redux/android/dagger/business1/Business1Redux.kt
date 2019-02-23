/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.android.dagger.business1

import org.swiften.redux.android.dagger.MainComponent
import org.swiften.redux.core.IReducer
import org.swiften.redux.core.IReduxAction
import org.swiften.redux.saga.common.SagaEffect
import org.swiften.redux.saga.rx.SagaEffects.await
import org.swiften.redux.saga.rx.SagaEffects.mergeAll
import org.swiften.redux.saga.rx.SagaEffects.putInStore
import org.swiften.redux.saga.rx.SagaEffects.takeLatest
import org.swiften.redux.saga.rx.debounceTake
import java.io.Serializable

/** Created by viethai.pham on 2019/02/21 */
object Business1Redux {
  data class State(
    val parent: ParentFragment1.S = ParentFragment1.S(),
    val search: SearchView1.S = SearchView1.S()
  ) : Serializable

  sealed class Action : IReduxAction {
    object Initialize : Action()
    object Deinitialize : Action()
    data class SetLoading(val loading: Boolean) : Action()
    data class SetQuery(val query: String?) : Action()
    data class SetResult(val result: MusicResult?) : Action()
  }

  object Reducer : IReducer<State, Action> {
    override fun invoke(p1: State, p2: Action): State {
      return when (p2) {
        is Action.SetLoading -> p1.copy(parent = p1.parent.copy(loading = p2.loading))
        is Action.SetQuery -> p1.copy(search = p1.search.copy(query = p2.query))
        else -> p1
      }
    }
  }

  object Saga {
    fun allSagas(component: MainComponent): SagaEffect<Any> {
      return takeLatest(Action::class, {
        when (it) {
          is Action.Initialize -> Unit
          is Action.Deinitialize -> Unit
          else -> null
        }
      }) {
        activeSagas(component.plus(Business1Module()).searchRepository())
      }
    }

    private fun activeSagas(repository: ISearchRepository): SagaEffect<Any> {
      return mergeAll(searchSaga(repository))
    }

    private fun searchSaga(repository: ISearchRepository): SagaEffect<Any> {
      return takeLatest(Action.SetQuery::class, { it.query }) { query ->
        await<Any> { input ->
          putInStore(Action.SetLoading(true)).await(input)

          try {
            val result = repository.searchMusicStore(query, 5)
            putInStore(Action.SetResult(result))
          } finally {
            putInStore(Action.SetLoading(false)).await(input)
          }
        } }
        .debounceTake(500)
    }
  }
}
