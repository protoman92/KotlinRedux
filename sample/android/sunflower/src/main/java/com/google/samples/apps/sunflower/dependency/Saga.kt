/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package com.google.samples.apps.sunflower.dependency

import com.google.samples.apps.sunflower.data.PlantDao
import com.google.samples.apps.sunflower.data.PlantRepository
import org.swiften.redux.android.livedata.rx.LiveDataEffects.takeEveryData
import org.swiften.redux.saga.put

/** Created by haipham on 2019/01/17 */
object Saga {
  object Plant {
    @Suppress("MemberVisibilityCanBePrivate")
    fun plantSyncSaga(api: PlantDao) = takeEveryData { api.getPlants() }
      .put { Redux.Action.UpdatePlants(it) }

    fun allSagas(api: PlantRepository) = arrayListOf(this.plantSyncSaga(api))
  }
}
