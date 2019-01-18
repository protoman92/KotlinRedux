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
internal class DoOnValueEffect<R>(
  private val source: IReduxSagaEffect<R>,
  private val performer: (R) -> Unit
) : ReduxSagaEffect<R>() {
  override fun invoke(p1: Input) = this.source.invoke(p1).doOnValue(this.performer)
}

/** Invoke a [DoOnValueEffect] on [this] */
fun <R> ReduxSagaEffect<R>.doOnValue(performer: (R) -> Unit) =
  this.transform(CommonSagaEffects.doOnValue(performer))
