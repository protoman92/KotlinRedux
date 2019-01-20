/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.saga

/** Created by haipham on 2019/01/07 */
/** [IReduxSagaEffect] whose [IReduxSagaOutput] delays emissions by [millis] */
internal class DelayEffect<R>(
  private val source: IReduxSagaEffect<R>,
  private val millis: Long
) : ReduxSagaEffect<R>() {
  override fun invoke(p1: Input) = this.source.invoke(p1).delay(this.millis)
}

/** Invoke a [DelayEffect] on [this] */
fun <R> ReduxSagaEffect<R>.delay(millis: Long) = this.transform(CommonSagaEffects.delay(millis))
