/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.saga.rx

import io.reactivex.Flowable
import org.swiften.redux.saga.IReduxSagaEffect
import org.swiften.redux.saga.Input
import org.swiften.redux.saga.ReduxSagaEffect

/** Created by haipham on 2019/01/05 */
/** [IReduxSagaEffect] whose [ReduxSagaOutput] is provided via [stream] */
internal class FromEffect<R>(
  private val stream: Flowable<R>
) : ReduxSagaEffect<R>() {
  override fun invoke(p1: Input) = ReduxSagaOutput(p1.scope, this.stream) { }
}
