/*
 * Copyright (c) haipham 2018. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.saga

import org.swiften.redux.core.IReduxAction
import org.swiften.redux.core.IReduxStore

/** Created by haipham on 2018/12/31 */
/**
 * [ISagaEffect] whose [ISagaOutput] deposits some values emitted by [source] into a
 * [IReduxStore] using [actionCreator].
 */
internal class PutEffect<P>(
  private val source: ISagaEffect<P>,
  private val actionCreator: (P) -> IReduxAction
) : SagaEffect<Any>() {
  override fun invoke(p1: SagaInput) =
    source.invoke(p1).map { p1.dispatch(this@PutEffect.actionCreator(it)) as Any }
}

/** Invoke a [PutEffect] on the current [CommonEffects] */
fun <P> SagaEffect<P>.put(actionCreator: (P) -> IReduxAction) =
  this.transform(CommonEffects.put(actionCreator))
