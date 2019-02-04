/*
 * Copyright (c) haipham 2018. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.android.sample

import org.swiften.redux.core.IReduxAction
import org.swiften.redux.core.IReducer
import org.swiften.redux.core.IRouterScreen
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
  }

  sealed class Screen : IRouterScreen {
    object MainScreen : Screen()
    class MusicDetail(val index: Int) : Screen()
    class WebView(val url: String) : Screen()
  }

  object Reducer : IReducer<State> {
    override operator fun invoke(previous: State, action: IReduxAction): State {
      return when (action) {
        is Action -> when (action) {
          is Action.UpdateAutocompleteQuery -> previous.copy(autocompleteQuery = action.query)
          is Action.UpdateMusicResult -> previous.copy(musicResult = action.result)
          is Action.UpdateLoadingResult -> previous.copy(loadingMusic = action.loading)
        }

        is Screen -> when (action) {
          is Screen.MusicDetail -> previous.copy(selectedResultIndex = action.index)
          else -> previous
        }

        else -> return previous
      }
    }
  }
}
