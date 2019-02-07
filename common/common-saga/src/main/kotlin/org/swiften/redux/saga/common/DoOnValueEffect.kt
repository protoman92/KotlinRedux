/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.saga.common

/** Created by haipham on 2019/01/06 */
/**
 * [ISagaEffect] whose [ISagaOutput] performs some side effects on value emissions with [performer].
 * @param R The result emission type.
 * @param source The source [ISagaEffect].
 * @param performer Function that performs side effects.
 */
internal class DoOnValueEffect<R>(
  private val source: ISagaEffect<R>,
  private val performer: (R) -> Unit
) : SagaEffect<R>() where R : Any {
  override fun invoke(p1: SagaInput) = this.source.invoke(p1).doOnValue(this.performer)
}
