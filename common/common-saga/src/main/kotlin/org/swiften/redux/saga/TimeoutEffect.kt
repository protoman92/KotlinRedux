/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.saga

/** Created by haipham on 2019/01/14 */
/**
 * [IReduxSagaEffect] whose [IReduxSagaOutput] can time out if no value is emitted within
 * [millis]
 */
internal class TimeoutEffect<R>(
  private val source: IReduxSagaEffect<R>,
  private val millis: Long
) : ReduxSagaEffect<R>() {
  override fun invoke(p1: Input) = this.source.invoke(p1).timeout(this.millis)
}

/** Invoke a [TimeoutEffect] on [this] */
fun <R> ReduxSagaEffect<R>.timeout(millis: Long) = this.transform(CommonSagaEffects.timeout(millis))
