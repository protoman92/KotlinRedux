/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.saga

/** Created by haipham on 2019/01/13 */
/** [IReduxSagaEffect] whose [IReduxSagaOutput] retries [times] if a [Throwable] is encountered */
internal class RetryEffect<R>(
  private val source: IReduxSagaEffect<R>,
  private val times: Long
) : ReduxSagaEffect<R>() {
  override fun invoke(p1: Input) = this.source.invoke(p1).retry(this.times)
}

/** Invoke a [RetryEffect] on [this] */
fun <R> ReduxSagaEffect<R>.retry(times: Long) = this.transform(CommonSagaEffects.retry(times))
fun <R> ReduxSagaEffect<R>.retry(times: Int) = this.retry(times.toLong())
