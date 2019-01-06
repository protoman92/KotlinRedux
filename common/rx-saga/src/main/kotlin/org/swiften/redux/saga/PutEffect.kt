/*
 * Copyright (c) haipham 2018. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.saga

import org.swiften.redux.core.Redux

/** Created by haipham on 2018/12/31 */
/**
 * [ReduxSagaEffect] whose [ReduxSaga.Output] deposits some values emitted by
 * [source] into a store using [actionCreator].
 */
internal class PutEffect<State, P>(
  private val source: ReduxSagaEffect<State, P>,
  private val actionCreator: (P) -> Redux.IAction
): ReduxSagaEffect<State, Any> {
  override fun invoke(p1: ReduxSaga.Input<State>) =
    source.invoke(p1).map { p1.dispatch(this@PutEffect.actionCreator(it)) as Any }
}

/** Invoke a [PutEffect] on the current [ReduxSagaHelper] */
fun <State, P> ReduxSagaEffect<State, P>.put(
  actionCreator: (P) -> Redux.IAction
) = ReduxSagaHelper.put(this, actionCreator)
