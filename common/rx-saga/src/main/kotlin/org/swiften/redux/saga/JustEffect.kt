/*
 * Copyright (c) haipham 2018. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.saga

import io.reactivex.Flowable.just

/** Created by haipham on 2018/12/24 */
/** [ReduxSagaEffect] whose [ReduxSaga.Output] simply emits [value] */
internal class JustEffect<State, R>(private val value: R) : ReduxSagaEffect<State, R> {
  override fun invoke(p1: ReduxSaga.Input<State>) =
    ReduxSaga.Output(p1.scope, just(this.value)) {}
}
