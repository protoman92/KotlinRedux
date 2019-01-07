/*
 * Copyright (c) haipham 2018. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.saga

/** Created by haipham on 2018/12/23 */
/**
 * [ReduxSagaEffect] whose output performs some asynchronous work with
 * [block], based on the emissions from another [param], and then emit the
 * result.
 */
internal class MapEffect<State, P, R>(
  private val param: ReduxSagaEffect<State, P>,
  private val block: (P) -> R
): ReduxSagaEffect<State, R> {
  override fun invoke(input: CommonSaga.Input<State>) =
    this.param.invoke(input).map(this.block)
}

/** Invoke a [MapEffect] on the current [ReduxSagaEffect] */
fun <State, R, R2> ReduxSagaEffect<State, R>.map(block: (R) -> R2) =
  ReduxSagaHelper.map(this, block)
