/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.android.sample

import kotlinx.coroutines.async
import org.swiften.redux.saga.catchError
import org.swiften.redux.saga.justThen
import org.swiften.redux.saga.mapAsync
import org.swiften.redux.saga.put
import org.swiften.redux.saga.rx.ReduxSagaEffects.justPut
import org.swiften.redux.saga.rx.ReduxSagaEffects.takeLatestAction
import org.swiften.redux.saga.rx.TakeEffectOptions
import org.swiften.redux.saga.then

/** Created by haipham on 2019/01/04 */
object MainSaga {
  fun sagas(api: IMainRepository) = arrayListOf(
    takeLatestAction<State, MainRedux.Action, String, Any>(
      { when (it) {
        is MainRedux.Action.UpdateAutocompleteQuery -> it.query
        else -> null
      } },
      { autocompleteSaga(api, it) },
      TakeEffectOptions(500)
    )
  )

  private fun autocompleteSaga(api: IMainRepository, query: String) =
    justPut<State>(MainRedux.Action.UpdateLoadingResult(true))
      .justThen(query)
      .mapAsync { this.async { api.searchMusicStore(it) } }
      .put { MainRedux.Action.UpdateMusicResult(it) }
      .catchError { }
      .then(justPut(MainRedux.Action.UpdateLoadingResult(false)))
}
