/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.saga

/** Created by haipham on 2019/01/05 */
/** [ISagaEffect] whose [ISagaOutput] filters emissions with [selector] */
internal class FilterEffect<R>(
  private val source: ISagaEffect<R>,
  private val selector: (R) -> Boolean
) : SagaEffect<R>() {
  override fun invoke(p1: SagaInput) = this.source.invoke(p1).filter(this.selector)
}

/** Invoke a [FilterEffect] on the current [ISagaEffect] */
fun <R> SagaEffect<R>.filter(predicate: (R) -> Boolean) =
  this.transform(CommonEffects.filter(predicate))
