/*
 * Copyright (c) haipham 2018. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.saga

/** Created by haipham on 2019/01/06 */
/**
 * [ReduxSagaEffect] whose [IReduxSagaOutput] performs some side effects on value emissions
 * with [block].
 */
internal class DoOnValueEffect<State, R>(
  private val source: ReduxSagaEffect<State, R>,
  private val block: (R) -> Unit
) : ReduxSagaEffect<State, R> {
  override fun invoke(p1: Input<State>) =
    this.source.invoke(p1).doOnValue(this.block)
}

/** Invoke a [DoOnValueEffect] on [this] */
fun <State, R> ReduxSagaEffect<State, R>.doOnValue(block: (R) -> Unit) =
  this.transform(CommonSagaEffects.doOnValue(block))
