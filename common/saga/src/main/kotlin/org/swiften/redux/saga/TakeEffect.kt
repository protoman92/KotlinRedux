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

/**
 * Created by haipham on 2018/12/23.
 */
/**
 * [TakeEffect] instances produces streams that filter [Redux.IAction] with
 * [extract] and pluck out the appropriate ones to perform additional work on
 * with [block].
 */
internal abstract class TakeEffect<State, P, R>(
  private val extract: Function1<Redux.IAction, P?>,
  private val block: Function1<P, ReduxSagaEffect<State, R>>
) : ReduxSagaEffect<State, R> {
  /**
   * Flatten an [ReduxSaga.Output] that streams [ReduxSaga.Output] to access
   * the values streamed by the inner [ReduxSaga.Output].
   */
  abstract fun flattenOutput(
    nestedOutput: ReduxSaga.Output<ReduxSaga.Output<R>>
  ): ReduxSaga.Output<R>

  @ExperimentalCoroutinesApi
  override operator fun invoke(input: ReduxSaga.Input<State>): ReduxSaga.Output<R> {
    val actionChannel = Channel<Redux.IAction>()

    val nestedOutput = ReduxSaga.Output(this, input.scope, input.scope.produce {
      for (action in actionChannel) {
        val param = this@TakeEffect.extract(action)

        if (param != null) {
          this.send(this@TakeEffect.block(param).invoke(input))
        }
      }
    }) { action -> input.scope.launch { actionChannel.send(action) } }

    return this.flattenOutput(nestedOutput)
  }
}
