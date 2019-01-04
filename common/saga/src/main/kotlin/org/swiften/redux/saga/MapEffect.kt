/*
 * Copyright (c) haipham 2018. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.saga

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi

/**
 * Created by haipham on 2018/12/23.
 */
/**
 * [ReduxSagaEffect] whose output performs some asynchronous work with
 * [block], based on the emissions from another [param], and then emit the
 * result.
 */
internal class MapEffect<State, P, R>(
  private val param: ReduxSagaEffect<State, P>,
  private val block: suspend CoroutineScope.(P) -> R
): ReduxSagaEffect<State, R> {
  @ExperimentalCoroutinesApi
  override fun invoke(input: ReduxSaga.Input<State>) =
    this.param.invoke(input).map(this.block)
}

/** Invoke a [MapEffect] on the current [ReduxSagaEffect] */
fun <State, R, R2> ReduxSagaEffect<State, R>.map(
  block: suspend CoroutineScope.(R) -> R2
) = ReduxSagaHelper.map(this, block)
