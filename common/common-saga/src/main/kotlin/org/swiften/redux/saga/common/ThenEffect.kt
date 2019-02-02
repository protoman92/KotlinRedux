/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.saga.common

/** Created by haipham on 2018/12/26 */
/**
 * [ISagaEffect] whose [ISagaOutput] enforces ordering for two [ISagaOutput] created by two other
 * [ISagaEffect].
 */
internal class ThenEffect<R, R2, R3>(
  private val source1: ISagaEffect<R>,
  private val source2: ISagaEffect<R2>,
  private val combiner: Function2<R, R2, R3>
) : SagaEffect<R3>() {
  override fun invoke(input: SagaInput): ISagaOutput<R3> {
    return this.source1.invoke(input).flatMap { value1 ->
      this@ThenEffect.source2.invoke(input).map { this@ThenEffect.combiner(value1, it) }
    }
  }
}
