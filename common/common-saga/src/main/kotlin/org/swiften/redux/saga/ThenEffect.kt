/*
 * Copyright (c) haipham 2018. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.saga

/** Created by haipham on 2018/12/26 */
/**
 * [ReduxSagaEffect] whose [IReduxSagaOutput] enforces ordering for two [IReduxSagaOutput] created
 * by two other [ReduxSagaEffect].
 */
internal class ThenEffect<State, R, R2, R3>(
  private val source1: ReduxSagaEffect<State, R>,
  private val source2: ReduxSagaEffect<State, R2>,
  private val combiner: Function2<R, R2, R3>
) : ReduxSagaEffect<State, R3> {
  @Suppress("PARAMETER_NAME_CHANGED_ON_OVERRIDE")
  override fun invoke(input: Input<State>) =
    this.source1.invoke(input).flatMap { value1 ->
      this@ThenEffect.source2.invoke(input).map { this@ThenEffect.combiner(value1, it) }
    }
}

/** Invoke a [ThenEffect] on the current [ReduxSagaEffect] */
fun <State, R, R2, R3> ReduxSagaEffect<State, R>.then(
  effect: ReduxSagaEffect<State, R2>,
  combiner: Function2<R, R2, R3>
) = ReduxSagaEffects.then(this, effect, combiner)

/** Invoke a [ThenEffect] but ignore emissions from [this] */
fun <State, R, R2> ReduxSagaEffect<State, R>.then(
  effect: ReduxSagaEffect<State, R2>
) = this.then(effect) { _, b -> b }

/** Invoke [then] with a single [value] */
fun <State, R, R2> ReduxSagaEffect<State, R>.justThen(value: R2) = this.map { value }
