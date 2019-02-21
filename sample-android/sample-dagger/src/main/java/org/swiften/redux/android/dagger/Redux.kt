/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.android.dagger

import org.swiften.redux.android.dagger.business1.ParentFragment1
import org.swiften.redux.android.dagger.business2.ParentFragment2
import org.swiften.redux.android.dagger.business3.ParentFragment3
import org.swiften.redux.core.IReducer
import org.swiften.redux.core.IReduxAction
import org.swiften.redux.core.IRouterScreen
import java.io.Serializable

/** Created by viethai.pham on 2019/02/21/2 */
object Redux {
  data class State(
    val dummy: Int = 0,
    override val flow1: ParentFragment1.S = ParentFragment1.S(),
    override val flow2: ParentFragment2.S = ParentFragment2.S(),
    override val flow3: ParentFragment3.S = ParentFragment3.S()
  ) : Serializable,
    ParentFragment1.ILocalState,
    ParentFragment2.ILocalState,
    ParentFragment3.ILocalState

  sealed class Screen : IRouterScreen {
    object Screen1 : Screen()
    object Screen2 : Screen()
    object Screen3 : Screen()
  }

  object Reducer : IReducer<State> {
    override fun invoke(p1: State, p2: IReduxAction): State = p1
  }
}
