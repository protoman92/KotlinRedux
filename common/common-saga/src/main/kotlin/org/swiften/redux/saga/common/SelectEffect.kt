/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.saga.common

import io.reactivex.Single

/** Created by haipham on 2019/01/01 */
/**
 * [SagaEffect] whose [SagaOutput] selects some value from an internal [State] using [selector].
 * @param State The state type to select from.
 * @param R The result emission type.
 * @param cls The [Class] to [State].
 * @param selector Function that selects [R] from [State].
 */
class SelectEffect<State, R>(
  private val cls: Class<State>,
  private val selector: (State) -> R
) : SingleSagaEffect<R>() where R : Any {
  override fun invoke(p1: SagaInput): ISagaOutput<R> {
    return SagaOutput(p1.context, p1.monitor, Single.create<R> {
      val lastState = p1.lastState()
      require(this.cls.isInstance(lastState))
      val value = this@SelectEffect.selector(this@SelectEffect.cls.cast(lastState))
      it.onSuccess(value)
    }.toFlowable())
  }

  /**
   * Since we will always select from an available [State], there will not be a situation whereby
   * the resulting [ISagaOutput] is empty. We can forgo the default value in this [await].
   * @param input A [SagaInput] instance.
   * @return A [R] instance.
   */
  fun await(input: SagaInput) = this.invoke(input).await()
}
