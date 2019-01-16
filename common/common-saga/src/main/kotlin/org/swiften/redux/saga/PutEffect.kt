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
internal class PutEffect<P>(
  private val source: IReduxSagaEffect<P>,
  private val actionCreator: (P) -> IReduxAction
) : ReduxSagaEffect<Any>() {
  override fun invoke(p1: Input) =
    source.invoke(p1).map { p1.dispatch(this@PutEffect.actionCreator(it)) as Any }
}

/** Invoke a [PutEffect] on the current [CommonSagaEffects] */
fun <P> ReduxSagaEffect<P>.put(actionCreator: (P) -> IReduxAction) =
  this.transform(CommonSagaEffects.put(actionCreator))
