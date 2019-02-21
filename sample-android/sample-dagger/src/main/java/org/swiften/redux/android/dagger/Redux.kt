/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.android.dagger

import org.swiften.redux.core.IReducer
import org.swiften.redux.core.IReduxAction
import java.io.Serializable

/** Created by viethai.pham on 2019/02/21/2 */
object Redux {
  data class State(val dummy: Int = 0) : Serializable

  object Reducer : IReducer<State> {
    override fun invoke(p1: State, p2: IReduxAction): State = p1
  }
}
