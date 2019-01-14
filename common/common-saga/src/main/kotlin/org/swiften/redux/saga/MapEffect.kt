/*
 * Copyright (c) haipham 2018. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.saga

/** Created by haipham on 2018/12/23 */
/**
 * [IReduxSagaEffect] whose output performs some asynchronous work with [block], based on the
 * emissions from another [param], and then emit the result.
 */
internal class MapEffect<State, P, R>(
  private val param: IReduxSagaEffect<State, P>,
  private val block: (P) -> R
) : IReduxSagaEffect<State, R> {
  override fun invoke(input: Input<State>) =
    this.param.invoke(input).map(this.block)
}

/** Invoke a [MapEffect] on the current [IReduxSagaEffect] */
fun <State, R, R2> IReduxSagaEffect<State, R>.map(block: (R) -> R2) =
  this.transform(CommonSagaEffects.map(block))
