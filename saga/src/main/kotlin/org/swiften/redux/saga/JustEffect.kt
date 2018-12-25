/*
 * Copyright (c) haipham 2018. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.saga

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.produce
import org.swiften.redux.core.Redux

/**
 * Created by haipham on 2018/12/24.
 */
/** [ReduxSaga.IEffect] whose [ReduxSaga.Output] simply emits [value] */
internal class JustEffect<State, R>(private val value: R) : ReduxSaga.IEffect<State, R> {
  @ExperimentalCoroutinesApi
  override fun invoke(input: ReduxSaga.Input<State>) =
    ReduxSaga.Output(input.scope,
      input.scope.produce { this.send(value) },
      object : Redux.IDispatcher {
        override operator fun invoke(action: Redux.IAction) {}
      })
}
