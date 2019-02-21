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
    override val flow2: ParentFragment2.S = ParentFragment2.S()
  ) : Serializable, ParentFragment2.ILocalState

  sealed class Action : IReduxAction

  object Reducer : IReducer<Business2Redux.State> {
    override fun invoke(p1: Business2Redux.State, p2: IReduxAction): Business2Redux.State {
      return p1
    }
  }
}
