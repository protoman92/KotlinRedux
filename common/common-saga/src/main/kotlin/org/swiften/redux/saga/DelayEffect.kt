/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.saga

/** Created by haipham on 2019/01/07 */
/** [ReduxSagaEffect] whose [IReduxSagaOutput] delays emissions by [delayMillis] */
internal class DelayEffect<State, R>(
  private val source: ReduxSagaEffect<State, R>,
  private val delayMillis: Long
) : ReduxSagaEffect<State, R> {
  override fun invoke(p1: Input<State>) = this.source.invoke(p1).delay(this.delayMillis)
}

/** Invoke a [DelayEffect] on [this] */
fun <State, R> ReduxSagaEffect<State, R>.delay(delayMillis: Long) =
  this.transform(CommonSagaEffects.delay(delayMillis))
