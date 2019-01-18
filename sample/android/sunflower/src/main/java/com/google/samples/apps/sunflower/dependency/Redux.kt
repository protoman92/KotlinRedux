/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package com.google.samples.apps.sunflower.dependency

import com.google.samples.apps.sunflower.data.GardenPlantingRepository
import com.google.samples.apps.sunflower.data.Plant
import com.google.samples.apps.sunflower.data.PlantRepository
import org.swiften.redux.android.livedata.rx.LiveDataEffects.takeEveryData
import org.swiften.redux.core.IReduxAction
import org.swiften.redux.core.IReduxReducer
import org.swiften.redux.router.IReduxRouterScreen
import org.swiften.redux.saga.doOnValue
import org.swiften.redux.saga.map
import org.swiften.redux.saga.mapSuspend
import org.swiften.redux.saga.put
import org.swiften.redux.saga.rx.ReduxSagaEffects.just
import org.swiften.redux.saga.rx.ReduxSagaEffects.takeLatestAction
import java.io.Serializable

/** Created by haipham on 2019/01/16 */
object Redux {
  data class SelectedPlant(val id: String, val isPlanted: Boolean? = null)

  data class State(
    val plants: List<Plant>? = null,
    val selectedPlant: SelectedPlant? = null
  ) : Serializable

  sealed class Action : IReduxAction {
    class AddPlantToGarden(val plantId: String) : Action()
    class UpdatePlants(val plants: List<Plant>?) : Action()
    class UpdateSelectedPlantStatus(val isPlanted: Boolean) : Action()
  }

  sealed class Screen : IReduxRouterScreen {
    class PlantDetail(val plantId: String) : Screen()
  }

  object Reducer : IReduxReducer<State> {
    override fun invoke(p1: State, p2: IReduxAction) = when (p2) {
      is Action -> when (p2) {
        is Action.UpdatePlants -> p1.copy(plants = p2.plants)

        is Action.UpdateSelectedPlantStatus ->
          p1.copy(selectedPlant = p1.selectedPlant?.copy(isPlanted = p2.isPlanted))

        else -> p1
      }

      is Screen -> when (p2) {
        is Screen.PlantDetail -> p1.copy(selectedPlant = SelectedPlant(p2.plantId))
        else -> p1
      }

      else -> p1
    }
  }

  object Saga {
    object GardenPlanting {
      @Suppress("MemberVisibilityCanBePrivate")
      fun addGardenPlantingSaga(api: GardenPlantingRepository) =
        takeLatestAction<Action.AddPlantToGarden, String, Any>({ it.plantId }, { plantId ->
          just(plantId)
            .mapSuspend { api.createGardenPlanting(it) }
            .put { Action.UpdateSelectedPlantStatus(true) }
        })

      @Suppress("SENSELESS_COMPARISON")
      fun checkSelectedPlantStatusSaga(api: GardenPlantingRepository) =
        takeLatestAction<Screen.PlantDetail, String, Any>({ it.plantId }, { plantId ->
          takeEveryData { api.getGardenPlantingForPlant(plantId) }
            .map { it != null }
            .doOnValue { println("Redux $it") }
            .put { Action.UpdateSelectedPlantStatus(it) }
        })

      fun allSagas(api: GardenPlantingRepository) = arrayListOf(
        this.addGardenPlantingSaga(api),
        this.checkSelectedPlantStatusSaga(api)
      )
    }

    object Plant {
      @Suppress("MemberVisibilityCanBePrivate")
      fun plantSyncSaga(api: PlantRepository) =
        takeEveryData { api.getPlants() }.put { Redux.Action.UpdatePlants(it) }

      fun allSagas(api: PlantRepository) = arrayListOf(this.plantSyncSaga(api))
    }
  }
}
