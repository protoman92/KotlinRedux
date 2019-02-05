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
}
