/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.android.dagger.business1

import org.swiften.redux.core.IReducer
import org.swiften.redux.core.IReduxAction
import java.io.Serializable

/** Created by viethai.pham on 2019/02/21 */
object Business1Redux {
  data class State(
    val parent: ParentFragment1.S = ParentFragment1.S(),
    val search: SearchView1.S = SearchView1.S()
  ) : Serializable

  sealed class Action : IReduxAction {
    data class SetQuery(val query: String?) : Action()
  }

  object Reducer : IReducer<State> {
    override fun invoke(p1: State, p2: IReduxAction): State {
      return p1
    }
  }
}
