/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.android.sample

import kotlinx.coroutines.async
import org.swiften.redux.saga.cast
import org.swiften.redux.saga.catchError
import org.swiften.redux.saga.mapAsync
import org.swiften.redux.saga.rx.ReduxSagaHelper.just
import org.swiften.redux.saga.rx.ReduxSagaHelper.takeLatestAction
import org.swiften.redux.saga.rx.TakeEffectOptions

/** Created by haipham on 2019/01/04 */
object MainSaga {
  fun sagas(api: IMainRepository) = arrayListOf(
    takeLatestAction<State, MainRedux.Action, String, Any>(
      { when (it) {
        is MainRedux.Action.UpdateAutocompleteQuery -> it.query
      } },
      { autocompleteSaga(api, it) },
      TakeEffectOptions(1000)
    )
  )

  private fun autocompleteSaga(api: IMainRepository, query: String) =
    just<State, String>(query)
      .mapAsync { this.async { api.searchMusicStore(it) } }
      .cast<State, MusicResult, Any>()
      .catchError { }
}
