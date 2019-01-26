/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.saga.rx

import org.swiften.redux.saga.ISagaEffect
import org.swiften.redux.saga.ISagaOutput
import org.swiften.redux.saga.SagaInput
import org.swiften.redux.saga.SagaEffect
import org.swiften.redux.saga.then

/** Created by haipham on 2019/01/01 */
/**
 * [ISagaEffect] whose [SagaOutput] selects some value from an internal [State] using
 * [selector].
 */
internal class SelectEffect<State, R>(
  private val cls: Class<State>,
  private val selector: (State) -> R
) : SagaEffect<R>() {
  override fun invoke(p1: SagaInput): ISagaOutput<R> {
    val lastState = p1.stateGetter()
    require(this.cls.isInstance(lastState))
    return SagaEffects.just(this.selector(this.cls.cast(lastState))).invoke(p1)
  }
}

/**
 * Invoke a [SelectEffect] on the current [ISagaEffect] and combine the emitted values with
 * [selector].
 */
inline fun <reified State, R, R2, R3> SagaEffect<R>.select(
  noinline selector: (State) -> R2,
  noinline combiner: (R, R2) -> R3
) = this.then(SagaEffects.select(selector), combiner)

/** Invoke a [SelectEffect] but ignore emissions from [this] */
inline fun <reified State, R2> SagaEffect<*>.select(noinline selector: (State) -> R2) =
  this.then(SagaEffects.select(selector))
