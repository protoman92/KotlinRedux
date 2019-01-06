/*
 * Copyright (c) haipham 2018. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.saga

import kotlinx.coroutines.channels.ReceiveChannel

/** Created by haipham on 2019/01/05 */
/** [ReduxSagaEffect] whose [ReduxSaga.Output] is provided via [channel] */
internal class FromEffect<State, R>(
  private val channel: ReceiveChannel<R>
): ReduxSagaEffect<State, R> {
  override fun invoke(p1: ReduxSaga.Input<State>) =
    ReduxSaga.Output(this, p1.scope, this.channel) { }
}
