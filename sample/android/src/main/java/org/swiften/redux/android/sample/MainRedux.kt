/*
 * Copyright (c) haipham 2018. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.android.sample

import org.swiften.redux.core.Redux
import org.swiften.redux.core.ReduxReducer

/**
 * Created by haipham on 12/19/18.
 */
object MainRedux {
  sealed class Action: Redux.IAction {
    data class UpdateAutocompleteQuery(val query: String?): Action()
  }

  object Reducer: ReduxReducer<State> {
    override operator fun invoke(previous: State, action: Redux.IAction): State {
      return when (action) {
        is Action -> when(action) {
          is Action.UpdateAutocompleteQuery -> previous.copy(action.query)
        }

        else -> return previous
      }
    }
  }
}
