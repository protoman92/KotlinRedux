/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.android.dagger

import org.swiften.redux.android.dagger.business1.Business1Redux
import org.swiften.redux.android.dagger.business2.Business2Redux
import org.swiften.redux.android.dagger.business3.Business3Redux
import org.swiften.redux.core.IReducer
import org.swiften.redux.core.IReduxAction
import org.swiften.redux.core.IRouterScreen
import org.swiften.redux.saga.SagaEffect
import java.io.Serializable

/** Created by viethai.pham on 2019/02/21/2 */
object Redux {
  data class State(
    val business1: Business1Redux.State = Business1Redux.State(),
    val business2: Business2Redux.State = Business2Redux.State(),
    val business3: Business3Redux.State = Business3Redux.State()
  ) : Serializable

  sealed class Screen : IRouterScreen {
    object Business1 : Screen()
    object Business2 : Screen()
    object Business3 : Screen()
  }

  object Reducer : IReducer<State, IReduxAction> {
    override fun invoke(p1: State, p2: IReduxAction): State {
      return when (p2) {
        is Business1Redux.Action -> p1.copy(business1 = Business1Redux.Reducer(p1.business1, p2))
        is Business2Redux.Action -> p1.copy(business2 = Business2Redux.Reducer(p1.business2, p2))
        is Business3Redux.Action -> p1.copy(business3 = Business3Redux.Reducer(p1.business3, p2))
        else -> p1
      }
    }
  }

  object Saga {
    fun allSagas(component: MainComponent): Collection<SagaEffect<Any>> {
      return arrayListOf(Business1Redux.Saga.allSagas(component))
    }
  }
}
