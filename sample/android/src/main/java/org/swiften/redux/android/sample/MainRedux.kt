/*
 * Copyright (c) haipham 2018. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.android.sample

import org.swiften.redux.core.IReduxAction
import org.swiften.redux.core.IReduxReducer

/** Created by haipham on 2018/12/19 */
object MainRedux {
  sealed class Action : IReduxAction {
    data class UpdateAutocompleteQuery(val query: String?) : Action()
    data class UpdateMusicResult(val result: MusicResult?) : Action()
  }

  object Reducer : IReduxReducer<State> {
    override operator fun invoke(previous: State, action: IReduxAction): State {
      return when (action) {
        is Action -> when (action) {
          is Action.UpdateAutocompleteQuery -> previous.copy(autocompleteQuery = action.query)
          is Action.UpdateMusicResult -> previous.copy(musicResult = action.result)
        }

        else -> return previous
      }
    }
  }
}
