/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package com.google.samples.apps.sunflower.dependency

import android.content.Context
import android.os.Parcel
import android.os.Parcelable
import com.google.samples.apps.sunflower.data.GardenPlanting
import com.google.samples.apps.sunflower.data.GardenPlantingRepository
import com.google.samples.apps.sunflower.data.Plant
import com.google.samples.apps.sunflower.data.PlantAndGardenPlantings
import com.google.samples.apps.sunflower.data.PlantRepository
import kotlinx.android.parcel.Parcelize
import org.swiften.redux.android.saga.rx.core.CoreAndroidEffects.watchConnectivity
import org.swiften.redux.android.saga.rx.livedata.LiveDataEffects.takeEveryData
import org.swiften.redux.core.IReduxAction
import org.swiften.redux.core.IReducer
import org.swiften.redux.router.IRouterScreen
import org.swiften.redux.saga.common.map
import org.swiften.redux.saga.common.mapSuspend
import org.swiften.redux.saga.common.put
import org.swiften.redux.saga.rx.SagaEffects.just
import org.swiften.redux.saga.rx.SagaEffects.takeLatestAction
import org.swiften.redux.saga.rx.select

/** Created by haipham on 2019/01/16 */
object Redux {
  const val NO_GROW_ZONE = -1

  @Parcelize
  data class SelectedPlant(val id: String, val isPlanted: Boolean? = null) : Parcelable

  data class State(
    val isConnected: Boolean = false,
    val plants: List<Plant>? = null,
    val gardenPlantings: List<GardenPlanting>? = null,
    val plantAndGardenPlantings: List<PlantAndGardenPlantings>? = null,
    val selectedPlant: SelectedPlant? = null,
    val selectedGrowZone: Int = NO_GROW_ZONE
  ) : Parcelable {
    companion object CREATOR : Parcelable.Creator<State> {
      override fun createFromParcel(parcel: Parcel) = State(parcel)
      override fun newArray(size: Int) = arrayOfNulls<State?>(size)
    }

    constructor(parcel: Parcel) : this(
      isConnected = parcel.readByte() != 0.toByte(),
      plants = null,
      gardenPlantings = null,
      plantAndGardenPlantings = null,
      selectedPlant = parcel.readParcelable<SelectedPlant>(SelectedPlant::class.java.classLoader),
      selectedGrowZone = parcel.readInt()
    )

    override fun writeToParcel(dest: Parcel?, flags: Int) {
      dest?.also {
        it.writeByte(if (this.isConnected) 1 else 0)
        it.writeParcelable(this.selectedPlant, 0)
        it.writeInt(this.selectedGrowZone)
      }
    }

    override fun describeContents() = 0
  }

  sealed class Action : IReduxAction {
    class UpdateConnectivity(val isConnected: Boolean) : Action()

    class AddPlantToGarden(val plantId: String) : Action()
    class SelectGrowZone(val zone: Int) : Action()
    class UpdatePlants(val plants: List<Plant>?) : Action()
    class UpdateGardenPlantings(val gardenPlantings: List<GardenPlanting>?) : Action()
    class UpdatePlantAndGardenPlantings(val data: List<PlantAndGardenPlantings>?) : Action()
    class UpdateSelectedPlantStatus(val isPlanted: Boolean) : Action()
  }

  sealed class Screen : IRouterScreen {
    class GardenToPlantDetail(val plantId: String) : Screen()
    class PlantListToPlantDetail(val plantId: String) : Screen()
  }

  object Reducer : IReducer<State> {
    override fun invoke(p1: State, p2: IReduxAction) = when (p2) {
      is Action -> when (p2) {
        is Action.UpdateConnectivity -> p1.copy(isConnected = p2.isConnected)

        is Action.SelectGrowZone -> p1.copy(selectedGrowZone = p2.zone)
        is Action.UpdatePlants -> p1.copy(plants = p2.plants)
        is Action.UpdatePlantAndGardenPlantings -> p1.copy(plantAndGardenPlantings = p2.data)
        is Action.UpdateGardenPlantings -> p1.copy(gardenPlantings = p2.gardenPlantings)

        is Action.UpdateSelectedPlantStatus ->
          p1.copy(selectedPlant = p1.selectedPlant?.copy(isPlanted = p2.isPlanted))

        else -> p1
      }

      is Screen -> when (p2) {
        is Screen.GardenToPlantDetail -> p1.copy(selectedPlant = SelectedPlant(p2.plantId))
        is Screen.PlantListToPlantDetail -> p1.copy(selectedPlant = SelectedPlant(p2.plantId))
        else -> p1
      }

      else -> p1
    }
  }

  object Saga {
    object CoreSaga {
      fun watchNetworkConnectivity(context: Context) =
        watchConnectivity(context).put { Action.UpdateConnectivity(it) }
    }

    object GardenPlantingSaga {
      /**
       * Every time [Action.AddPlantToGarden] is received, call
       * [GardenPlantingRepository.createGardenPlanting] to signal that the user wants to add
       * a [Plant] to the garden.
       */
      private fun addGardenPlanting(api: GardenPlantingRepository) =
        takeLatestAction<Action.AddPlantToGarden, String, Any>({ it.plantId }) { plantId ->
          just(plantId)
            .mapSuspend { api.createGardenPlanting(it) }
            .put { Action.UpdateSelectedPlantStatus(true) }
        }

      /**
       * Every time the user navigates to plant detail screen, update planted status for
       * [State.selectedPlant] so show/hide the FAB.
       */
      @Suppress("SENSELESS_COMPARISON")
      fun checkSelectedPlantStatus(api: GardenPlantingRepository) =
        takeLatestAction<Screen, String, Any>({
          when (it) {
            is Screen.GardenToPlantDetail -> it.plantId
            is Screen.PlantListToPlantDetail -> it.plantId
          }
        }) { plantId ->
          takeEveryData { api.getGardenPlantingForPlant(plantId) }
            .map { it != null }
            .put { Action.UpdateSelectedPlantStatus(it) }
        }

      /** Bridge to sync [GardenPlanting] using [GardenPlantingRepository.getGardenPlantings] */
      private fun syncGardenPlantings(api: GardenPlantingRepository) =
        takeEveryData { api.getGardenPlantings() }.put { Action.UpdateGardenPlantings(it) }

      /**
       * Bridge to sync [PlantAndGardenPlantings] using
       * [GardenPlantingRepository.getPlantAndGardenPlantings]
       */
      private fun syncPlantAndGardenPlantings(api: GardenPlantingRepository) =
        takeEveryData { api.getPlantAndGardenPlantings() }
          .map { p -> p.filter { it.gardenPlantings.isNotEmpty() } }
          .put { Action.UpdatePlantAndGardenPlantings(it) }

      fun allSagas(api: GardenPlantingRepository) = arrayListOf(
        this.addGardenPlanting(api),
        this.checkSelectedPlantStatus(api),
        this.syncGardenPlantings(api),
        this.syncPlantAndGardenPlantings(api)
      )
    }

    object PlantSaga {
      /**
       * Bridge to sync all [Plant] using [PlantRepository.getPlants], but also pay attention to
       * [State.selectedGrowZone] and filter out [Plant] whose [Plant.growZoneNumber] matches
       * the selected grow zone.
       */
      private fun syncPlants(api: PlantRepository) = takeEveryData { api.getPlants() }
        .select<State, List<Plant>, Int, List<Plant>>({ it.selectedGrowZone }, { a, b ->
          if (b == NO_GROW_ZONE) a else a.filter { it.growZoneNumber == b }
        }).put { Action.UpdatePlants(it) }

      /**
       * Every time [Action.SelectGrowZone] is received, sync [Plant] that matches
       * [Action.SelectGrowZone.zone].
       */
      private fun syncPlantsOnGrowZone(api: PlantRepository) =
        takeLatestAction<Action.SelectGrowZone, Int, Any>({ it.zone }) { growZone ->
          if (growZone == NO_GROW_ZONE) {
            takeEveryData { api.getPlants() }
          } else {
            takeEveryData { api.getPlantsWithGrowZoneNumber(growZone) }
          }.put { Action.UpdatePlants(it) }
        }

      fun allSagas(api: PlantRepository) = arrayListOf(
        this.syncPlants(api),
        this.syncPlantsOnGrowZone(api)
      )
    }
  }
}