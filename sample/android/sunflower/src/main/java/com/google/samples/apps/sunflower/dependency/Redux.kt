/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package com.google.samples.apps.sunflower.dependency

import com.google.samples.apps.sunflower.data.Plant
import org.swiften.redux.core.IReduxAction
import org.swiften.redux.core.IReduxReducer
import org.swiften.redux.router.IReduxRouterScreen
import java.io.Serializable

/** Created by haipham on 2019/01/16 */
object Redux {
  data class State(
    val plants: List<Plant>? = null,
    val selectedPlantId: String? = null
  ) : Serializable

  sealed class Action : IReduxAction {
    class UpdatePlants(val plants: List<Plant>?) : Action()
  }

  sealed class Screen : IReduxRouterScreen {
    class PlantDetail(val plantId: String) : Screen()
  }

  object Reducer : IReduxReducer<State> {
    override fun invoke(p1: State, p2: IReduxAction) = when (p2) {
      is Action -> when (p2) {
        is Action.UpdatePlants -> p1.copy(plants = p2.plants)
      }

      is Screen -> when (p2) {
        is Screen.PlantDetail -> p1.copy(selectedPlantId = p2.plantId)
        else -> p1
      }

      else -> p1
    }
  }
}
