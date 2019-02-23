/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.android.dagger.business2

import org.swiften.redux.core.IReducer
import org.swiften.redux.core.IReduxAction
import java.io.Serializable

/** Created by viethai.pham on 2019/02/21 */
object Business2Redux {
  data class State(
    val flow2: Business2HostFragment.S = Business2HostFragment.S()
  ) : Serializable

  sealed class Action : IReduxAction

  object Reducer : IReducer<State, Action> {
    override fun invoke(p1: State, p2: Action): Business2Redux.State {
      return p1
    }
  }
}
