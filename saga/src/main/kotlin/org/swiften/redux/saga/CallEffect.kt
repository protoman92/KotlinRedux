/*
 * Copyright (c) haipham 2018. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.saga

/**
 * Created by haipham on 2018/12/23.
 */
/**
 * [ReduxSaga.IEffect] whose output performs some asynchronous work with
 * [block], based on the emissions from another [param], and then emit the
 * result.
 */
internal class CallEffect<State, P, R>(
  private val param: ReduxSaga.IEffect<State, P>,
  private val block: suspend (P) -> R) : ReduxSaga.IEffect<State, R>
{
  override operator fun invoke(input: ReduxSaga.Input<State>) =
    this.param.invoke(input).map { block(it) }
}

/** Invoke a [CallEffect] on the current [ReduxSaga.IEffect] */
fun <State, R, R2> ReduxSaga.IEffect<State, R>.call(block: suspend (R) -> R2) =
  ReduxSagaEffect.call(this, block)
