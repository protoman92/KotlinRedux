/*
 * Copyright (c) haipham 2018. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.saga

/** Created by haipham on 2018/12/26 */
/**
 * [ISagaEffect] whose [ISagaOutput] enforces ordering for two [ISagaOutput] created
 * by two other [ISagaEffect].
 */
internal class ThenEffect<R, R2, R3>(
  private val source1: ISagaEffect<R>,
  private val source2: ISagaEffect<R2>,
  private val combiner: Function2<R, R2, R3>
) : SagaEffect<R3>() {
  @Suppress("PARAMETER_NAME_CHANGED_ON_OVERRIDE")
  override fun invoke(input: SagaInput) =
    this.source1.invoke(input).flatMap { value1 ->
      this@ThenEffect.source2.invoke(input).map { this@ThenEffect.combiner(value1, it) }
    }
}

/** Invoke a [ThenEffect] on the current [ISagaEffect] */
fun <R, R2, R3> SagaEffect<R>.then(
  effect: ISagaEffect<R2>,
  combiner: Function2<R, R2, R3>
) = CommonEffects.then(this, effect, combiner)

/** Invoke a [ThenEffect] but ignore emissions from [this] */
fun <R, R2> SagaEffect<R>.then(
  effect: ISagaEffect<R2>
) = this.then(effect) { _, b -> b }

/** Invoke [then] with a single [value] */
fun <R, R2> SagaEffect<R>.justThen(value: R2) = this.map { value }
