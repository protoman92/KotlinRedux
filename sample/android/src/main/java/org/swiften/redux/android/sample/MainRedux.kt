/*
 * Copyright (c) haipham 2018. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.android.sample

import org.swiften.redux.core.IReduxAction
import org.swiften.redux.core.IReduxReducer
import org.swiften.redux.router.IReduxRouterScreen

/** Created by haipham on 2018/12/19 */
object MainRedux {
  sealed class Action : IReduxAction {
    class UpdateAutocompleteQuery(val query: String?) : Action()
    class UpdateMusicResult(val result: MusicResult?) : Action()
    class UpdateLoadingResult(val loading: Boolean?) : Action()
  }

  sealed class Screen : IReduxRouterScreen {
    class MusicDetail(val index: Int) : Screen()
    class WebView(val url: String) : Screen()
  }

  object Reducer : IReduxReducer<State> {
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
