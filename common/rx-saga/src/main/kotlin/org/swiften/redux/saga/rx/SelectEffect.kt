/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.saga.rx

import org.swiften.redux.saga.IReduxSagaEffect
import org.swiften.redux.saga.Input
import org.swiften.redux.saga.ReduxSagaEffect
import org.swiften.redux.saga.then

/** Created by haipham on 2019/01/01 */
/**
 * [IReduxSagaEffect] whose [ReduxSagaOutput] selects some value from an internal [State] using
 * [selector].
 */
internal class SelectEffect<State, R>(
  private val selector: (State) -> R
) : ReduxSagaEffect<State, R>() {
  override fun invoke(p1: Input<State>) =
    ReduxSagaEffects.just<State, R>(this.selector(p1.stateGetter())).invoke(p1)
}

/**
 * Invoke a [SelectEffect] on the current [IReduxSagaEffect] and combine the emitted values with
 * [selector].
 */
fun <State, R, R2, R3> ReduxSagaEffect<State, R>.select(
  selector: (State) -> R2,
  combiner: (R, R2) -> R3
) = this.then(ReduxSagaEffects.select(selector), combiner)

/** Invoke a [SelectEffect] but ignore emissions from [this] */
fun <State, R, R2> ReduxSagaEffect<State, R>.select(selector: (State) -> R2) =
  this.then(ReduxSagaEffects.select(selector))
