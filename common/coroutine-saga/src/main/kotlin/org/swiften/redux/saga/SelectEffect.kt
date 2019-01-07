/*
 * Copyright (c) haipham 2018. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.saga

import org.swiften.redux.saga.ReduxSagaHelper.just

/** Created by haipham on 2019/01/01 */
/**
 * [ReduxSagaEffect] whose [ReduxSaga.Output] selects some value from an
 * internal state using [selector].
 */
internal class SelectEffect<State, R>(
  private val selector: (State) -> R
) : ReduxSagaEffect<State, R> {
  override fun invoke(p1: ReduxSaga.Input<State>) =
    just<State, R>(this.selector(p1.stateGetter())).invoke(p1)
}

/**
 * Invoke a [SelectEffect] on the current [ReduxSagaEffect] and combine the
 * emitted values with [selector].
 */
fun <State, R, R2, R3> ReduxSagaEffect<State, R>.select(
  selector: (State) -> R2,
  combiner: (R, R2) -> R3
) = this.then(ReduxSagaHelper.select(selector), combiner)

/** Invoke a [SelectEffect] but ignore emissions from [this] */
fun <State, R, R2> ReduxSagaEffect<State, R>.select(selector: (State) -> R2) =
  this.then(ReduxSagaHelper.select(selector))
