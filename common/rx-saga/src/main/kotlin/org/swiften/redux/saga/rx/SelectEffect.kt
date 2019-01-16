/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.saga.rx

import org.swiften.redux.saga.IReduxSagaEffect
import org.swiften.redux.saga.IReduxSagaOutput
import org.swiften.redux.saga.Input
import org.swiften.redux.saga.ReduxSagaEffect
import org.swiften.redux.saga.then

/** Created by haipham on 2019/01/01 */
/**
 * [IReduxSagaEffect] whose [ReduxSagaOutput] selects some value from an internal [State] using
 * [selector].
 */
internal class SelectEffect<State, R>(
  private val cls: Class<State>,
  private val selector: (State) -> R
) : ReduxSagaEffect<R>() {
  override fun invoke(p1: Input): IReduxSagaOutput<R> {
    val lastState = p1.stateGetter()
    require(this.cls.isInstance(lastState))
    return ReduxSagaEffects.just(this.selector(this.cls.cast(lastState))).invoke(p1)
  }
}

/**
 * Invoke a [SelectEffect] on the current [IReduxSagaEffect] and combine the emitted values with
 * [selector].
 */
inline fun <reified State, R, R2, R3> ReduxSagaEffect<R>.select(
  noinline selector: (State) -> R2,
  noinline combiner: (R, R2) -> R3
) = this.then(ReduxSagaEffects.select(selector), combiner)

/** Invoke a [SelectEffect] but ignore emissions from [this] */
inline fun <reified State, R, R2> ReduxSagaEffect<R>.select(noinline selector: (State) -> R2) =
  this.then(ReduxSagaEffects.select(selector))
