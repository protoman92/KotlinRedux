/*
 * Copyright (c) haipham 2018. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.saga

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import org.swiften.redux.core.Redux
import org.swiften.redux.core.ReduxDispatcher

/**
 * Created by haipham on 2018/12/24.
 */
/** [ReduxSaga.IEffect] whose [ReduxSaga.Output] simply emits [value] */
internal class JustEffect<State, R>(private val value: R) : ReduxSagaEffect<State, R> {
  @ExperimentalCoroutinesApi
  override fun invoke(input: ReduxSaga.Input<State>): ReduxSaga.Output<R> {
    /** Do not use produce to avoid closing this channel */
    val channel = Channel<R>()
    input.scope.launch { channel.send(value) }

    return ReduxSaga.Output(input.scope, channel, object : ReduxDispatcher {
      override fun invoke(action: Redux.IAction) {}
    })
  }
}
