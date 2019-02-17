/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.android.sample

import kotlinx.coroutines.async
import org.swiften.kotlinfp.Option
import org.swiften.redux.core.IReducer
import org.swiften.redux.core.IReduxAction
import org.swiften.redux.core.IReduxActionWithKey
import org.swiften.redux.saga.common.SagaEffect
import org.swiften.redux.saga.common.mapAsync
import org.swiften.redux.saga.common.mapIgnoringNull
import org.swiften.redux.saga.common.putInStore
import org.swiften.redux.saga.common.thenMightAsWell
import org.swiften.redux.saga.common.thenNoMatterWhat
import org.swiften.redux.saga.rx.SagaEffects.putInStore
import org.swiften.redux.saga.rx.SagaEffects.selectFromState
import org.swiften.redux.saga.rx.SagaEffects.takeLatestForKeys
import org.swiften.redux.saga.rx.debounceTake
import java.io.Serializable

/** Created by haipham on 26/1/19 */
interface IMusicResultProvider {
  val musicResult: MusicResult?
}

interface ISelectedTrackProvider {
  val selectedTrack: Int?
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
      companion object {
        const val UPDATE_QUERY = "UPDATE_QUERY"
        const val UPDATE_LIMIT = "UPDATE_LIMIT"
      }

      data class SetLoading(val loading: Boolean) : Search()

      data class UpdateQuery(val query: String?) : Search(), IReduxActionWithKey {
        override val key: String get() = Search.UPDATE_QUERY
      }

      data class UpdateLimit(val limit: ResultLimit?) : Search(), IReduxActionWithKey {
        override val key: String get() = Search.UPDATE_LIMIT
      }
    }

    data class UpdateMusicResult(val result: MusicResult?) : Action()
    data class UpdateSelectedTrack(val index: Int?) : Action()
  }

  object Reducer : IReducer<State> {
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
    fun searchSaga(api: ISearchAPI<MusicResult?>): SagaEffect<Any> {
      return takeLatestForKeys(setOf(Action.Search.UPDATE_LIMIT, Action.Search.UPDATE_QUERY)) { a ->
        selectFromState(State::class) {
          Option.wrap(it.search.query).zipWithNullable(it.search.limit) { a, b -> a to b.count }
        }
          .mapIgnoringNull { it.value }
          .thenMightAsWell(putInStore(Action.Search.SetLoading(true)))
          .mapAsync { this.async { Option.wrap(api.searchMusicStore(it.first, it.second)) } }
          .putInStore { Action.UpdateMusicResult(it.value) }
          .thenNoMatterWhat(putInStore(Action.Search.SetLoading(false)))
      }.debounceTake(1000)
    }

    fun allSagas(api: ISearchAPI<MusicResult?>) = arrayListOf(searchSaga(api))
  }
}
