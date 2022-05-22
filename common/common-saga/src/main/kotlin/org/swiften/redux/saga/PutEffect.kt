/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.saga

import io.reactivex.Single
import org.swiften.redux.core.IReduxAction

/** Created by haipham on 2018/12/31 */
/**
 * [ISagaEffect] whose [ISagaOutput] dispatches some [IReduxAction].
 * @param action The [IReduxAction] to be dispatched.
 */
class PutEffect(private val action: IReduxAction) : SingleEffect<Any>() {
  override fun invoke(p1: SagaInput): ISagaOutput<Any> {
    return SagaOutput(p1.monitor, Single.create<Any> {
      p1.dispatch(this@PutEffect.action).await()
      it.onSuccess(Unit)
    }.toFlowable())
  }

  /**
   * Since the result type of this [SingleEffect] is [Any], we can have an [await] method that
   * does not require default value.
   * @param input A [SagaInput] instance.
   * @return [Any] value.
   */
  fun await(input: SagaInput): Any = this.await(input, {})
}
