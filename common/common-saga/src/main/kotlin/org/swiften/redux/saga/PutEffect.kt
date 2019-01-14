/*
 * Copyright (c) haipham 2018. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.saga

import org.swiften.redux.core.IReduxAction
import org.swiften.redux.core.IReduxStore

/** Created by haipham on 2018/12/31 */
/**
 * [IReduxSagaEffect] whose [IReduxSagaOutput] deposits some values emitted by [source] into a
 * [IReduxStore] using [actionCreator].
 */
internal class PutEffect<State, P>(
  private val source: IReduxSagaEffect<State, P>,
  private val actionCreator: (P) -> IReduxAction
) : IReduxSagaEffect<State, Any> {
  override fun invoke(p1: Input<State>) =
    source.invoke(p1).map { p1.dispatch(this@PutEffect.actionCreator(it)) as Any }
}

/** Invoke a [PutEffect] on the current [CommonSagaEffects] */
fun <State, P> IReduxSagaEffect<State, P>.put(actionCreator: (P) -> IReduxAction) =
  this.transform(CommonSagaEffects.put(actionCreator))
