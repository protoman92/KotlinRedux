/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.saga.common

/** Created by haipham on 2019/01/05 */
/**
 * [ISagaEffect] whose [ISagaOutput] filters emissions with [predicate].
 * @param R The result emission type.
 * @param source The source [ISagaEffect].
 * @param predicate Condition checker.
 */
internal class FilterEffect<R>(
  private val source: ISagaEffect<R>,
  private val predicate: (R) -> Boolean
) : SagaEffect<R>() where R : Any {
  override fun invoke(p1: SagaInput) = this.source.invoke(p1).filter(this.predicate)
}
