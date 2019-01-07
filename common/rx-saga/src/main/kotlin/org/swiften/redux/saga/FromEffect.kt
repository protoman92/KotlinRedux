/*
 * Copyright (c) haipham 2018. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.saga

import io.reactivex.Flowable

/** Created by haipham on 2019/01/05 */
/** [ReduxSagaEffect] whose [ReduxSaga.Output] is provided via [stream] */
internal class FromEffect<State, R>(
  private val stream: Flowable<R>
): ReduxSagaEffect<State, R> {
  override fun invoke(p1: CommonSaga.Input<State>) =
    ReduxSaga.Output(p1.scope, this.stream) { }
}
