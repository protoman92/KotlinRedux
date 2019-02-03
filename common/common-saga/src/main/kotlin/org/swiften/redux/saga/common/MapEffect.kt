/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.saga.common

/** Created by haipham on 2018/12/23 */
/**
 * [ISagaEffect] whose output performs some asynchronous work with [transformer], based on the
 * emissions from another [source], and then emit the result.
 * @param P The source emission type.
 * @param R The result emission type.
 * @param source The source [ISagaEffect].
 * @param transformer Function that transforms [P] to [R].
 */
internal class MapEffect<P, R>(
  private val source: ISagaEffect<P>,
  private val transformer: (P) -> R
) : SagaEffect<R>() {
  override fun invoke(input: SagaInput) = this.source.invoke(input).map(this.transformer)
}
