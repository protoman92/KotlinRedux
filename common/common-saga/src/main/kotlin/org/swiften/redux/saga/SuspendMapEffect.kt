/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.saga

import kotlinx.coroutines.CoroutineScope

/** Created by haipham on 2019/01/07 */
/** Similar to [MapEffect], but handles suspend functions */
internal class SuspendMapEffect<State, P, R>(
  private val source: IReduxSagaEffect<State, P>,
  private val block: suspend CoroutineScope.(P) -> R
) : IReduxSagaEffect<State, R> {
  override fun invoke(p1: Input<State>) =
    this.source.invoke(p1).mapSuspend(this.block)
}

/** Invoke a [SuspendMapEffect] on [this] */
fun <State, P, R> IReduxSagaEffect<State, P>.mapSuspend(
  block: suspend CoroutineScope.(P) -> R
) = this.transform(CommonSagaEffects.mapSuspend(block))
