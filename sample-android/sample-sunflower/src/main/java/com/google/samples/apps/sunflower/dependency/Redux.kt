/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package com.google.samples.apps.sunflower.dependency

import android.content.Context
import android.os.Parcel
import android.os.Parcelable
import androidx.lifecycle.Transformations
import com.google.samples.apps.sunflower.data.GardenPlanting
import com.google.samples.apps.sunflower.data.GardenPlantingRepository
import com.google.samples.apps.sunflower.data.Plant
import com.google.samples.apps.sunflower.data.PlantAndGardenPlantings
import com.google.samples.apps.sunflower.data.PlantRepository
import kotlinx.android.parcel.Parcelize
import org.swiften.kotlinfp.Option
import org.swiften.redux.android.saga.rx.core.CoreAndroidEffects.watchConnectivity
import org.swiften.redux.android.saga.rx.livedata.LiveDataEffects.takeEveryData
import org.swiften.redux.core.IReducer
import org.swiften.redux.core.IReduxAction
import org.swiften.redux.core.IRouterScreen
import org.swiften.redux.saga.common.SagaEffect
import org.swiften.redux.saga.common.SagaEffects.just
import org.swiften.redux.saga.common.SagaEffects.selectFromState
import org.swiften.redux.saga.common.SagaEffects.takeAction
import org.swiften.redux.saga.common.flatMap
import org.swiften.redux.saga.common.map
import org.swiften.redux.saga.common.mapIgnoringNull
import org.swiften.redux.saga.common.mapSuspend
import org.swiften.redux.saga.common.putInStore
import org.swiften.redux.saga.common.selectFromState
import org.swiften.redux.saga.common.switchMap
import org.swiften.redux.thunk.IReduxThunkAction
import org.swiften.redux.thunk.ThunkFunction

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
    data class UpdateConnectivity(val isConnected: Boolean) : Action()

    data class AddPlantToGarden(val plantId: String) : Action()
    data class SelectGrowZone(val zone: Int) : Action()
    data class SelectPlantFromGarden(val index: Int) : Action()
    data class SelectPlantFromPlantList(val index: Int) : Action()
    data class UpdatePlants(val plants: List<Plant>?) : Action()
    data class UpdateGardenPlantings(val gardenPlantings: List<GardenPlanting>?) : Action()
    data class UpdatePlantAndGardenPlantings(val data: List<PlantAndGardenPlantings>?) : Action()
    data class UpdateSelectedPlantStatus(val isPlanted: Boolean) : Action()
  }

  sealed class ThunkAction<GExt, Param> : IReduxThunkAction<State, GExt, Param> {
    object AddSelectedPlantToGarden : ThunkAction<Any, Unit>() {
      override val params get() = Unit

      override val payload: ThunkFunction<State, Any> = { dispatch, getState, _ ->
        getState().selectedPlant?.id?.also { dispatch(Action.AddPlantToGarden(it)) }
      }
    }

    data class ToggleGrowZone(val zone: Int) : ThunkAction<Any, Int>() {
      override val params get() = this.zone

      override val payload: ThunkFunction<State, Any> = { dispatch, getState, _ ->
        val selectedZone = getState().selectedGrowZone
        val newZone = if (selectedZone == NO_GROW_ZONE) this@ToggleGrowZone.zone else NO_GROW_ZONE
        dispatch(Action.SelectGrowZone(newZone))
      }
    }
  }

  sealed class Screen : IRouterScreen {
    class GardenToPlantDetail(val plantId: String) : Screen()
    class PlantListToPlantDetail(val plantId: String) : Screen()
  }

  object Reducer : IReducer<State, IReduxAction> {
    override fun invoke(p1: State, p2: IReduxAction): State {
      return when (p2) {
        is Action -> when (p2) {
          is Action.SelectGrowZone -> p1.copy(selectedGrowZone = p2.zone)
          is Action.UpdateConnectivity -> p1.copy(isConnected = p2.isConnected)
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
  }

  object Saga {
    object CoreSaga {
      fun watchNetworkConnectivity(context: Context): SagaEffect<Any> {
        return watchConnectivity(context).putInStore { Action.UpdateConnectivity(it) }
      }
    }

    object GardenPlantingSaga {
      /**
       * Every time [Action.AddPlantToGarden] is received, call
       * [GardenPlantingRepository.createGardenPlanting] to signal that the user wants to add
       * a [Plant] to the garden.
       */
      private fun addPlantToGarden(api: GardenPlantingRepository): SagaEffect<Any> {
        return takeAction(Action.AddPlantToGarden::class) { it.plantId }.switchMap { plantId ->
          just(plantId)
            .mapSuspend { api.createGardenPlanting(it) }
            .putInStore { Action.UpdateSelectedPlantStatus(true) }
        }
      }

      /**
       * Every time the user navigates to plant detail screen, update planted status for
       * [State.selectedPlant] so show/hide the FAB.
       */
      @Suppress("SENSELESS_COMPARISON")
      fun checkSelectedPlantStatus(api: GardenPlantingRepository): SagaEffect<Any> {
        return takeAction(Screen::class) {
          when (it) {
            is Screen.GardenToPlantDetail -> it.plantId
            is Screen.PlantListToPlantDetail -> it.plantId
          }
        }.switchMap { plantId ->
          takeEveryData { Transformations
            .map(api.getGardenPlantingForPlant(plantId)) { Option.wrap(it) } }
            .map { it.value != null }
            .putInStore { Action.UpdateSelectedPlantStatus(it) }
        }
      }

      private fun selectPlantFromGarden(): SagaEffect<Any> {
        return takeAction(Action.SelectPlantFromGarden::class) { it.index }.flatMap { index ->
          selectFromState(State::class) { Option.wrap(it.plantAndGardenPlantings) }
            .mapIgnoringNull { it.value?.elementAtOrNull(index) }
            .map { it.plant }
            .map { it.plantId }
            .putInStore { Screen.GardenToPlantDetail(it) }
        }
      }

      /** Bridge to sync [GardenPlanting] using [GardenPlantingRepository.getGardenPlantings] */
      private fun syncGardenPlantings(api: GardenPlantingRepository): SagaEffect<Any> {
        return takeEveryData { api.getGardenPlantings() }.putInStore { Action.UpdateGardenPlantings(it) }
      }

      /**
       * Bridge to sync [PlantAndGardenPlantings] using
       * [GardenPlantingRepository.getPlantAndGardenPlantings]
       */
      private fun syncPlantAndGardenPlantings(api: GardenPlantingRepository): SagaEffect<Any> {
        return takeEveryData { api.getPlantAndGardenPlantings() }
          .map { p -> p.filter { it.gardenPlantings.isNotEmpty() } }
          .putInStore { Action.UpdatePlantAndGardenPlantings(it) }
      }

      fun allSagas(api: GardenPlantingRepository) = arrayListOf(
        this.addPlantToGarden(api),
        this.checkSelectedPlantStatus(api),
        this.selectPlantFromGarden(),
        this.syncGardenPlantings(api),
        this.syncPlantAndGardenPlantings(api)
      )
    }

    object PlantSaga {
      private fun selectPlantFromPlantList(): SagaEffect<Any> {
        return takeAction(Action.SelectPlantFromPlantList::class) { it.index }.flatMap { index ->
          selectFromState(State::class) { Option.wrap(it.plants) }
            .mapIgnoringNull { it.value?.elementAtOrNull(index) }
            .map { it.plantId }
            .putInStore { Screen.PlantListToPlantDetail(it) }
        }
      }

      /**
       * Bridge to sync all [Plant] using [PlantRepository.getPlants], but also pay attention to
       * [State.selectedGrowZone] and filter out [Plant] whose [Plant.growZoneNumber] matches
       * the selected grow zone.
       */
      private fun syncPlants(api: PlantRepository): SagaEffect<Any> {
        return takeEveryData { api.getPlants() }
          .selectFromState(State::class, { it.selectedGrowZone }, { a, b ->
            if (b == NO_GROW_ZONE) a else a.filter { it.growZoneNumber == b }
          }).putInStore { Action.UpdatePlants(it) }
      }

      /**
       * Every time [Action.SelectGrowZone] is received, sync [Plant] that matches
       * [Action.SelectGrowZone.zone].
       */
      private fun syncPlantsOnGrowZone(api: PlantRepository): SagaEffect<Any> {
        return takeAction(Action.SelectGrowZone::class) { it.zone }.switchMap { zone ->
          if (zone == NO_GROW_ZONE) {
            takeEveryData { api.getPlants() }
          } else {
            takeEveryData { api.getPlantsWithGrowZoneNumber(zone) }
          }.putInStore { Action.UpdatePlants(it) }
        }
      }

      fun allSagas(api: PlantRepository) = arrayListOf(
        this.selectPlantFromPlantList(),
        this.syncPlants(api),
        this.syncPlantsOnGrowZone(api)
      )
    }
  }
}
