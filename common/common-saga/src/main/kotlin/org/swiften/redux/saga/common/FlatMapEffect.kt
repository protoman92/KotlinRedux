/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.saga.common

/** Created by viethai.pham on 2019/06/17 */
/**
 * [SagaEffect] whose [ISagaOutput] flattens other [ISagaOutput] produced by [transformer]. There
 * are a few [FlatMapEffect.Mode]:
 * - [FlatMapEffect.Mode.EVERY] will ensure every emitted value is processed.
 * - [FlatMapEffect.Mode.LATEST] will ensure only the latest value is processed.
 */
class FlatMapEffect<R, R2>(
  private val source: SagaEffect<R>,
  private val mode: Mode,
  private val transformer: (R) -> SagaEffect<R2>
) : SagaEffect<R2>() where R : Any, R2 : Any {
  enum class Mode {
    EVERY,
    LATEST
  }

  override fun invoke(p1: SagaInput): ISagaOutput<R2> {
    return when (this.mode) {
      Mode.EVERY -> this.source.invoke(p1).flatMap { this.transformer(it).invoke(p1) }
      Mode.LATEST -> this.source.invoke(p1).switchMap { this.transformer(it).invoke(p1) }
    }
  }
}
