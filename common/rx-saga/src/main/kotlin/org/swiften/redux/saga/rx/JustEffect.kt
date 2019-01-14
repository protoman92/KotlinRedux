/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.saga.rx

import io.reactivex.Flowable.just
import org.swiften.redux.saga.Input
import org.swiften.redux.saga.IReduxSagaEffect

/** Created by haipham on 2018/12/24 */
/** [IReduxSagaEffect] whose [ReduxSagaOutput] simply emits [value] */
internal class JustEffect<State, R>(private val value: R) : IReduxSagaEffect<State, R> {
  override fun invoke(p1: Input<State>) = ReduxSagaOutput(p1.scope, just(this.value)) {}
}
