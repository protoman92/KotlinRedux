/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.saga.cr

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.produce
import org.swiften.redux.saga.CommonSaga
import org.swiften.redux.saga.ReduxSagaEffect

/** Created by haipham on 2018/12/24 */
/** [ReduxSagaEffect] whose [ReduxSaga.Output] simply emits [value] */
internal class JustEffect<State, R>(private val value: R) :
  ReduxSagaEffect<State, R> {
  @ExperimentalCoroutinesApi
  override fun invoke(input: CommonSaga.Input<State>): ReduxSaga.Output<R> {
    return ReduxSaga.Output(this, input.scope, input.scope.produce {
      this.send(value)
    }) {}
  }
}
