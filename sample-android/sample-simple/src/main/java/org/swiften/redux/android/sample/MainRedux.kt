/*
 * Copyright (c) haipham 2018. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.android.sample

import kotlinx.coroutines.async
import org.swiften.redux.core.IReducer
import org.swiften.redux.core.IReduxAction
import org.swiften.redux.core.IRouterScreen
import org.swiften.redux.saga.common.SagaEffect
import org.swiften.redux.saga.common.catchAsync
import org.swiften.redux.saga.common.justThen
import org.swiften.redux.saga.common.map
import org.swiften.redux.saga.common.mapAsync
import org.swiften.redux.saga.common.put
import org.swiften.redux.saga.common.then
import org.swiften.redux.saga.rx.SagaEffects
import org.swiften.redux.saga.rx.SagaEffects.justPut
import org.swiften.redux.saga.rx.SagaEffects.select
import org.swiften.redux.saga.rx.SagaEffects.takeEveryAction
import org.swiften.redux.saga.rx.SagaEffects.takeLatestAction
import org.swiften.redux.saga.rx.TakeEffectOptions
import java.io.Serializable

/** Created by haipham on 2018/12/19 */
object MainRedux {
  data class State(
    val autocompleteQuery: String? = "",
    val musicResult: MusicResult? = null,
    val selectedResultIndex: Int? = null,
    val loadingMusic: Boolean? = null
  ) : Serializable {
    fun currentSelectedTrack() = this.selectedResultIndex?.let { index ->
      this.musicResult?.results?.elementAtOrNull(index)
    }
  }

  sealed class Action : IReduxAction {
    class UpdateAutocompleteQuery(val query: String?) : Action()
    class UpdateMusicResult(val result: MusicResult?) : Action()
    class UpdateLoadingResult(val loading: Boolean?) : Action()
    object PreviewTrack : Action();
  }

  sealed class Screen : IRouterScreen {
    object MainScreen : Screen()
    class MusicDetail(val index: Int) : Screen()
    class WebView(val url: String) : Screen()
  }

  object Reducer : IReducer<State> {
    override operator fun invoke(p1: State, p2: IReduxAction): State {
      return when (p2) {
        is Action -> when (p2) {
          is Action.UpdateAutocompleteQuery -> p1.copy(autocompleteQuery = p2.query)
          is Action.UpdateMusicResult -> p1.copy(musicResult = p2.result)
          is Action.UpdateLoadingResult -> p1.copy(loadingMusic = p2.loading)
          else -> p1
        }

        is Screen -> when (p2) {
          is Screen.MusicDetail -> p1.copy(selectedResultIndex = p2.index)
          else -> p1
        }

        else -> return p1
      }
    }
  }

  object Saga {
    fun sagas(api: IMainRepository) = arrayListOf(
      this.autocompleteSaga(api),
      this.previewSelectedTrack()
    )

    private fun autocompleteSaga(api: IMainRepository): SagaEffect<Any> {
      return takeLatestAction<Action.UpdateAutocompleteQuery, String, Any>(
        { it.query },
        TakeEffectOptions(500)
      ) { query ->
        justPut(Action.UpdateLoadingResult(true))
          .justThen(query)
          .mapAsync { this.async { api.searchMusicStore(it) } }
          .catchAsync { this.async { api.createFakeResult() } }
          .put { Action.UpdateMusicResult(it) }
          .then(SagaEffects.justPut(Action.UpdateLoadingResult(false)))
      }
    }

    private fun previewSelectedTrack(): SagaEffect<Any> {
      return takeEveryAction<Action.PreviewTrack, Unit, Any>({ Unit }) {
        select<State, MusicTrack?> { it.currentSelectedTrack() }
          .map { it?.previewUrl ?: "" }
          .put { MainRedux.Screen.WebView(it) }
      }
    }
  }
}
