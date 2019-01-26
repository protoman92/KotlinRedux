/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.android.sample

import kotlinx.coroutines.async
import org.swiften.redux.saga.catchErrorAsync
import org.swiften.redux.saga.justThen
import org.swiften.redux.saga.mapAsync
import org.swiften.redux.saga.put
import org.swiften.redux.saga.rx.SagaEffects.justPut
import org.swiften.redux.saga.rx.SagaEffects.takeLatestAction
import org.swiften.redux.saga.rx.TakeEffectOptions
import org.swiften.redux.saga.then

/** Created by haipham on 2019/01/04 */
object MainSaga {
  fun sagas(api: IMainRepository) = arrayListOf(
    takeLatestAction<MainRedux.Action, String, Any>({ when (it) {
      is MainRedux.Action.UpdateAutocompleteQuery -> it.query
      else -> null
    } }, TakeEffectOptions(500)) { autocompleteSaga(api, it) }
  )

  private fun autocompleteSaga(api: IMainRepository, query: String) =
    justPut(MainRedux.Action.UpdateLoadingResult(true))
      .justThen(query)
      .mapAsync { this.async { api.searchMusicStore(it) } }
      .catchErrorAsync { this.async { api.createFakeResult() } }
      .put { MainRedux.Action.UpdateMusicResult(it) }
      .then(justPut(MainRedux.Action.UpdateLoadingResult(false)))
}
