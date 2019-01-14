/*
 * Copyright (c) haipham 2018. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.saga

/** Created by haipham on 2019/01/06 */
/**
 * [IReduxSagaEffect] whose [IReduxSagaOutput] performs some side effects on value emissions
 * with [performer].
 */
internal class DoOnValueEffect<State, R>(
  private val source: IReduxSagaEffect<State, R>,
  private val performer: (R) -> Unit
) : ReduxSagaEffect<State, R>() {
  override fun invoke(p1: Input<State>) = this.source.invoke(p1).doOnValue(this.performer)
}

/** Invoke a [DoOnValueEffect] on [this] */
fun <State, R> ReduxSagaEffect<State, R>.doOnValue(performer: (R) -> Unit) =
  this.transform(CommonSagaEffects.doOnValue(performer))
