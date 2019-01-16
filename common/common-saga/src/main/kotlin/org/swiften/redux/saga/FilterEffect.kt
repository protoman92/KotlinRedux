/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.saga

/** Created by haipham on 2019/01/05 */
/** [IReduxSagaEffect] whose [IReduxSagaOutput] filters emissions with [selector] */
internal class FilterEffect<R>(
  private val source: IReduxSagaEffect<R>,
  private val selector: (R) -> Boolean
) : ReduxSagaEffect<R>() {
  override fun invoke(p1: Input) = this.source.invoke(p1).filter(this.selector)
}

/** Invoke a [FilterEffect] on the current [IReduxSagaEffect] */
fun <R> ReduxSagaEffect<R>.filter(predicate: (R) -> Boolean) =
  this.transform(CommonSagaEffects.filter(predicate))
