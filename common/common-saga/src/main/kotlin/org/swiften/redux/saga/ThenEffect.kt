/*
 * Copyright (c) haipham 2018. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.saga

/** Created by haipham on 2018/12/26 */
/**
 * [IReduxSagaEffect] whose [IReduxSagaOutput] enforces ordering for two [IReduxSagaOutput] created
 * by two other [IReduxSagaEffect].
 */
internal class ThenEffect<R, R2, R3>(
  private val source1: IReduxSagaEffect<R>,
  private val source2: IReduxSagaEffect<R2>,
  private val combiner: Function2<R, R2, R3>
) : ReduxSagaEffect<R3>() {
  @Suppress("PARAMETER_NAME_CHANGED_ON_OVERRIDE")
  override fun invoke(input: Input) =
    this.source1.invoke(input).flatMap { value1 ->
      this@ThenEffect.source2.invoke(input).map { this@ThenEffect.combiner(value1, it) }
    }
}

/** Invoke a [ThenEffect] on the current [IReduxSagaEffect] */
fun <R, R2, R3> ReduxSagaEffect<R>.then(
  effect: IReduxSagaEffect<R2>,
  combiner: Function2<R, R2, R3>
) = CommonSagaEffects.then(this, effect, combiner)

/** Invoke a [ThenEffect] but ignore emissions from [this] */
fun <R, R2> ReduxSagaEffect<R>.then(
  effect: IReduxSagaEffect<R2>
) = this.then(effect) { _, b -> b }

/** Invoke [then] with a single [value] */
fun <R, R2> ReduxSagaEffect<R>.justThen(value: R2) = this.map { value }
