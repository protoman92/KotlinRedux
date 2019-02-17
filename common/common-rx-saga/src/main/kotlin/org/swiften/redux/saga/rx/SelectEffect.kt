/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.saga.rx

import org.swiften.redux.saga.common.ISagaOutput
import org.swiften.redux.saga.common.SagaEffect
import org.swiften.redux.saga.common.SagaInput
import org.swiften.redux.saga.common.SingleSagaEffect

/** Created by haipham on 2019/01/01 */
/**
 * [SagaEffect] whose [SagaOutput] selects some value from an internal [State] using [selector].
 * @param State The state type to select from.
 * @param R The result emission type.
 * @param cls The [Class] to [State].
 * @param selector Function that selects [R] from [State].
 */
internal class SelectEffect<State, R>(
  private val cls: Class<State>,
  private val selector: (State) -> R
) : SingleSagaEffect<R>() where R : Any {
  override fun invoke(p1: SagaInput): ISagaOutput<R> {
    val lastState = p1.lastState()
    require(this.cls.isInstance(lastState))
    return SagaEffects.just(this.selector(this.cls.cast(lastState))).invoke(p1)
  }
}
