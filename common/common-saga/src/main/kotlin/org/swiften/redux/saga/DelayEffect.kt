/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.saga

/** Created by haipham on 2019/01/07 */
/** [IReduxSagaEffect] whose [IReduxSagaOutput] delays emissions by [delayMillis] */
internal class DelayEffect<R>(
  private val source: IReduxSagaEffect<R>,
  private val delayMillis: Long
) : ReduxSagaEffect<R>() {
  override fun invoke(p1: Input) = this.source.invoke(p1).delay(this.delayMillis)
}

/** Invoke a [DelayEffect] on [this] */
fun <R> ReduxSagaEffect<R>.delay(delayMillis: Long) =
  this.transform(CommonSagaEffects.delay(delayMillis))
