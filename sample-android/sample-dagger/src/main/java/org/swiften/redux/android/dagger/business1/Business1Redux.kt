/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.android.dagger.business1

import org.swiften.redux.core.IReducer
import org.swiften.redux.core.IReduxAction
import org.swiften.redux.saga.common.SagaEffect
import org.swiften.redux.saga.rx.SagaEffects.await
import org.swiften.redux.saga.rx.SagaEffects.doNothing
import org.swiften.redux.saga.rx.SagaEffects.mergeAll
import org.swiften.redux.saga.rx.SagaEffects.putInStore
import org.swiften.redux.saga.rx.SagaEffects.takeLatest
import org.swiften.redux.saga.rx.debounceTake
import java.io.Serializable

/** Created by viethai.pham on 2019/02/21 */
object Business1Redux {
  data class State(
    val parent: ParentFragment1.S = ParentFragment1.S(),
    val search: SearchView1.S = SearchView1.S(),
    val result: SearchResultList1.S = SearchResultList1.S()
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
        is Action.SetResult -> p1.copy(result = p1.result.copy(result = p2.result))
        else -> p1
      }
    }
  }

  object Saga {
    fun allSagas(provider: Business1SagaComponentProvider): SagaEffect<Any> {
      return takeLatest(Action::class, {
        when (it) {
          is Action.Initialize -> true
          is Action.Deinitialize -> false
          else -> null
        }
      }) {
        if (it) {
          val module = Business1SagaModule()
          val component = provider.provide(module)
          activeSagas(component.searchRepository())
        } else {
          doNothing()
        }
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
            putInStore(Action.SetResult(result)).await(input)
          } finally {
            putInStore(Action.SetLoading(false)).await(input)
          }
        } }
        .debounceTake(500)
    }
  }
}
