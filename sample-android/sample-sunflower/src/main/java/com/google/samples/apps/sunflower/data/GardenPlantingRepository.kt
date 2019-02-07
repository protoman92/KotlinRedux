/*
 * Copyright 2018 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.samples.apps.sunflower.data

import androidx.lifecycle.LiveData
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext

class GardenPlantingRepository private constructor(
  private val gardenPlantingDao: GardenPlantingDao
) {

  suspend fun createGardenPlanting(plantId: String) {
    withContext(IO) {
      val gardenPlanting = GardenPlanting(plantId)
      gardenPlantingDao.insertGardenPlanting(gardenPlanting)
    }
  }

  suspend fun removeGardenPlanting(gardenPlanting: GardenPlanting) {
    withContext(IO) {
      gardenPlantingDao.deleteGardenPlanting(gardenPlanting)
    }
  }

  fun getGardenPlantingForPlant(plantId: String): LiveData<GardenPlanting?> {
    return this.gardenPlantingDao.getGardenPlantingForPlant(plantId)
  }

  fun getGardenPlantings() = this.gardenPlantingDao.getGardenPlantings()
  fun getPlantAndGardenPlantings() = this.gardenPlantingDao.getPlantAndGardenPlantings()

  companion object {

    // For Singleton instantiation
    @Volatile private var instance: GardenPlantingRepository? = null

    fun getInstance(gardenPlantingDao: GardenPlantingDao): GardenPlantingRepository {
      return this.instance ?: synchronized(this) {
        this.instance ?: GardenPlantingRepository(gardenPlantingDao).also { this.instance = it }
      }
    }
  }
}
