/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.android.sample

import kotlinx.coroutines.async
import org.swiften.redux.saga.common.SagaEffect
import org.swiften.redux.saga.common.catchAsync
import org.swiften.redux.saga.common.justThen
import org.swiften.redux.saga.common.mapAsync
import org.swiften.redux.saga.common.put
import org.swiften.redux.saga.common.then
import org.swiften.redux.saga.rx.SagaEffects.justPut
import org.swiften.redux.saga.rx.SagaEffects.takeLatestAction
import org.swiften.redux.saga.rx.TakeEffectOptions

/** Created by haipham on 2019/01/04 */
object MainSaga {
  fun sagas(api: IMainRepository) = arrayListOf(
    takeLatestAction<MainRedux.Action, String, Any>({ when (it) {
      is MainRedux.Action.UpdateAutocompleteQuery -> it.query
      else -> null
    } }, TakeEffectOptions(500)) { autocompleteSaga(api, it) }
  )

  private fun autocompleteSaga(api: IMainRepository, query: String): SagaEffect<Any> {
    return justPut(MainRedux.Action.UpdateLoadingResult(true))
      .justThen(query)
      .mapAsync { this.async { api.searchMusicStore(it) } }
      .catchAsync { this.async { api.createFakeResult() } }
      .put { MainRedux.Action.UpdateMusicResult(it) }
      .then(justPut(MainRedux.Action.UpdateLoadingResult(false)))
  }
}
