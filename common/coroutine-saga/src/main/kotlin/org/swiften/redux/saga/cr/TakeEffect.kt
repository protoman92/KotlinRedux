/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.saga.cr

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.launch
import org.swiften.redux.core.IReduxAction
import org.swiften.redux.saga.IReduxSagaOutput
import org.swiften.redux.saga.Input
import org.swiften.redux.saga.ReduxSagaEffect

/** Created by haipham on 2018/12/23 */
/**
 * [TakeEffect] instances produces streams that filter [IReduxAction] with [extract] and pluck
 * out the appropriate ones to perform additional work on with [block].
 */
internal abstract class TakeEffect<State, P, R>(
  private val extract: Function1<IReduxAction, P?>,
  private val block: Function1<P, ReduxSagaEffect<State, R>>,
  private val options: ReduxSaga.TakeOptions
) : ReduxSagaEffect<State, R> {
  /**
   * Flatten an [ReduxSaga.Output] that streams [ReduxSaga.Output] to access the values streamed
   * by the inner [ReduxSaga.Output].
   */
  abstract fun flattenOutput(nestedOutput: IReduxSagaOutput<IReduxSagaOutput<R>>): IReduxSagaOutput<R>

  @ExperimentalCoroutinesApi
  override operator fun invoke(p1: Input<State>): IReduxSagaOutput<R> {
    val actionChannel = Channel<IReduxAction>()

    val nested = ReduxSaga.Output(this, p1.scope,
      p1.scope.produce {
        for (action in actionChannel) {
          val param = this@TakeEffect.extract(action)

          if (param != null) {
            this@produce.send(this@TakeEffect.block(param).invoke(p1))
          }
        }
      }) { action -> p1.scope.launch { actionChannel.send(action) } }

    return this.flattenOutput(nested.debounce(this.options.debounceMillis))
  }
}
