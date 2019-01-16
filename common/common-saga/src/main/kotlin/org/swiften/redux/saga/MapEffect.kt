/*
 * Copyright (c) haipham 2018. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.saga

/** Created by haipham on 2018/12/23 */
/**
 * [IReduxSagaEffect] whose output performs some asynchronous work with [transformer], based on the
 * emissions from another [source], and then emit the result.
 */
internal class MapEffect<P, R>(
  private val source: IReduxSagaEffect<P>,
  private val transformer: (P) -> R
) : ReduxSagaEffect<R>() {
  override fun invoke(input: Input) = this.source.invoke(input).map(this.transformer)
}

/** Invoke a [MapEffect] on the current [IReduxSagaEffect] */
fun <R, R2> ReduxSagaEffect<R>.map(transformer: (R) -> R2) =
  this.transform(CommonSagaEffects.map(transformer))
