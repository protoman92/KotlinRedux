/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.saga.rx

import org.swiften.redux.saga.common.ISagaEffect
import org.swiften.redux.saga.common.ISagaOutput
import org.swiften.redux.saga.common.SagaEffect
import org.swiften.redux.saga.common.SagaInput

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
    val lastState = p1.lastState()
    require(this.cls.isInstance(lastState))
    return SagaEffects.just(this.selector(this.cls.cast(lastState))).invoke(p1)
  }
}
