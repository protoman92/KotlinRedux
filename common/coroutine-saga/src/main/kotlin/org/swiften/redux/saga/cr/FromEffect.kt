/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.saga.cr

import kotlinx.coroutines.channels.ReceiveChannel
import org.swiften.redux.saga.CommonSaga
import org.swiften.redux.saga.ReduxSagaEffect

/** Created by haipham on 2019/01/05 */
/** [ReduxSagaEffect] whose [ReduxSaga.Output] is provided via [channel] */
internal class FromEffect<State, R>(
  private val channel: ReceiveChannel<R>
) : ReduxSagaEffect<State, R> {
  override fun invoke(p1: CommonSaga.Input<State>) =
    ReduxSaga.Output(this, p1.scope, this.channel) { }
}
