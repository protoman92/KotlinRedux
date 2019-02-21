/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.android.dagger.business3

import org.swiften.redux.core.IReducer
import org.swiften.redux.core.IReduxAction
import java.io.Serializable

/** Created by viethai.pham on 2019/02/21 */
object Business3Redux {
  data class State(
    override val flow3: ParentFragment3.S = ParentFragment3.S()
  ) : Serializable, ParentFragment3.ILocalState

  sealed class Action : IReduxAction

  object Reducer : IReducer<Business3Redux.State> {
    override fun invoke(p1: Business3Redux.State, p2: IReduxAction): Business3Redux.State {
      return p1
    }
  }
}
