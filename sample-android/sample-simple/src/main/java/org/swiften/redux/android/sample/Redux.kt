/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.android.sample

import org.swiften.redux.core.IReducer
import org.swiften.redux.core.IReduxAction
import org.swiften.redux.core.IRouterScreen
import org.swiften.redux.saga.common.CommonEffects.await
import org.swiften.redux.saga.common.CommonEffects.put
import org.swiften.redux.saga.common.CommonEffects.select
import org.swiften.redux.saga.common.CommonEffects.takeAction
import org.swiften.redux.saga.common.SagaEffect
import org.swiften.redux.saga.common.debounce
import org.swiften.redux.saga.common.switchMap
import java.io.Serializable

/** Created by haipham on 26/1/19 */
interface IMusicResultProvider {
  val musicResult: MusicResult?
}

interface ISelectedTrackProvider {
  val selectedTrack: Int?
}

interface ISearchStateProvider {
  val search: SearchFragment.S
}

object Redux {
  data class State(
    override val main: MainFragment.S = MainFragment.S(),
    override val search: SearchFragment.S = SearchFragment.S(),
    override val musicResult: MusicResult? = null,
    override val selectedTrack: Int? = null
  ) : Serializable,
    DetailFragment.ILocalState,
    MainFragment.ILocalState,
    SearchFragment.ILocalState

  sealed class Action : IReduxAction {
    sealed class Main : Action() {
      data class UpdateSelectedPage(val page: Int) : Main()
    }

    sealed class Search : Action() {
      data class SetLoading(val loading: Boolean) : Search()
      data class UpdateQuery(val query: String?) : Search()
      data class UpdateLimit(val limit: ResultLimit?) : Search()
    }

    data class UpdateMusicResult(val result: MusicResult?) : Action()
    data class UpdateSelectedTrack(val index: Int?) : Action()
  }

  sealed class Screen : IRouterScreen {
    object MainFragment: Screen()
    object Back : Screen()
  }

  object Reducer : IReducer<State, IReduxAction> {
    override fun invoke(p1: State, p2: IReduxAction): State {
      return when (p2) {
        is Action -> when (p2) {
          is Action.UpdateMusicResult -> p1.copy(musicResult = p2.result)

          is Action.UpdateSelectedTrack -> p1.copy(
            selectedTrack = p2.index,
            main = p1.main.copy(selectedPage = Constants.MAIN_PAGE_DETAIL_INDEX)
          )

          is Action.Main -> when (p2) {
            is Action.Main.UpdateSelectedPage ->
              p1.copy(main = p1.main.copy(selectedPage = p2.page))
          }

          is Action.Search -> when (p2) {
            is Action.Search.SetLoading -> p1.copy(search = p1.search.copy(loading = p2.loading))
            is Action.Search.UpdateQuery -> p1.copy(search = p1.search.copy(query = p2.query))

            is Action.Search.UpdateLimit ->
              p1.copy(search = p1.search.copy(limit = p2.limit))
          }
        }

        else -> p1
      }
    }
  }

  object Saga {
    @Suppress("MemberVisibilityCanBePrivate")
    fun searchSaga(api: ISearchAPI<MusicResult?>, debounce: Long = 1000): SagaEffect<Unit> {
      return takeAction(Action::class) {
        when (it) {
          is Action.Search.UpdateLimit -> true
          is Action.Search.UpdateQuery -> true
          else -> null
        }
      }
        .debounce(debounce)
        .switchMap {
          await { input ->
            val searchState = select(ISearchStateProvider::class) { it.search }.await(input)
            val query = searchState.query
            val limit = (searchState.limit ?: ResultLimit.FIVE).count

            try {
              if (query != null) {
                put(Action.Search.SetLoading(true)).await(input)
                val result = api.searchMusicStore(query, limit)
                put(Action.UpdateMusicResult(result)).await(input)
              }
            } catch (e: Throwable) {
              println("Redux: not handling $e right now yet")
            } finally {
              put(Action.Search.SetLoading(false)).await(input)
            }
          }
        }
    }

    fun allSagas(api: ISearchAPI<MusicResult?>) = arrayListOf(searchSaga(api))
  }
}
