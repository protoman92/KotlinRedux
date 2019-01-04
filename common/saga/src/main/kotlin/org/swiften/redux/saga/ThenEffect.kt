/*
 * Copyright (c) haipham 2018. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.saga

import kotlinx.coroutines.ExperimentalCoroutinesApi

/**
 * Created by haipham on 2018/12/26.
 */
/**
 * [ReduxSagaEffect] whose [ReduxSaga.Output] enforces ordering for two
 * [ReduxSaga.Output] created by two other [ReduxSagaEffect].
 */
internal class ThenEffect<State, R, R2, R3>(
  private val source1: ReduxSagaEffect<State, R>,
  private val source2: ReduxSagaEffect<State, R2>,
  private val combiner: Function2<R, R2, R3>
) : ReduxSagaEffect<State, R3> {
  @ExperimentalCoroutinesApi
  @Suppress("PARAMETER_NAME_CHANGED_ON_OVERRIDE")
  override fun invoke(input: ReduxSaga.Input<State>): ReduxSaga.Output<R3> {
    val o2 = this@ThenEffect.source2.invoke(input)

    return this.source1.invoke(input).flatMap { value1 ->
      o2.map { this@ThenEffect.combiner(value1, it) }
    }
  }
}

/** Invoke a [ThenEffect] on the current [ReduxSagaEffect] */
fun <State, R, R2, R3> ReduxSagaEffect<State, R>.then(
  effect: ReduxSagaEffect<State, R2>,
  combiner: Function2<R, R2, R3>
) = ReduxSagaHelper.then(this, effect, combiner)

/** Invoke a [ThenEffect] but ignore emissions from [this] */
fun <State, R, R2> ReduxSagaEffect<State, R>.then(
  effect: ReduxSagaEffect<State, R2>
) = this.then(effect) { _, b -> b }
