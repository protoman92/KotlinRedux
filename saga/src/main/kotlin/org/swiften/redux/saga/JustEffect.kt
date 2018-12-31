/*
 * Copyright (c) haipham 2018. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.saga

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.launch
import org.swiften.redux.core.Redux
import org.swiften.redux.core.ReduxDispatcher

/**
 * Created by haipham on 2018/12/24.
 */
/** [ReduxSagaEffect] whose [ReduxSaga.Output] simply emits [value] */
internal class JustEffect<State, R>(private val value: R) : ReduxSagaEffect<State, R> {
  @ExperimentalCoroutinesApi
  override fun invoke(input: ReduxSaga.Input<State>): ReduxSaga.Output<R> {
    return ReduxSaga.Output(this, input.scope, input.scope.produce {
      this.send(value)
    }) {}
  }
}
