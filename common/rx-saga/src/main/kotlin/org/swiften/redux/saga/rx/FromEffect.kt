/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.saga.rx

import io.reactivex.Flowable
import org.swiften.redux.saga.Input
import org.swiften.redux.saga.IReduxSagaEffect

/** Created by haipham on 2019/01/05 */
/** [IReduxSagaEffect] whose [ReduxSagaOutput] is provided via [stream] */
internal class FromEffect<State, R>(
  private val stream: Flowable<R>
) : IReduxSagaEffect<State, R> {
  override fun invoke(p1: Input<State>) = ReduxSagaOutput(p1.scope, this.stream) { }
}
