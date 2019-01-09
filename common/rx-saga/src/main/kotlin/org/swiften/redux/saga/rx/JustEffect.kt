/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.saga.rx

import io.reactivex.Flowable.just
import org.swiften.redux.saga.Input
import org.swiften.redux.saga.ReduxSagaEffect

/** Created by haipham on 2018/12/24 */
/** [ReduxSagaEffect] whose [ReduxSaga.Output] simply emits [value] */
internal class JustEffect<State, R>(private val value: R) :
  ReduxSagaEffect<State, R> {
  override fun invoke(p1: Input<State>) =
    ReduxSaga.Output(p1.scope, just(this.value)) {}
}
