/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.saga.common

import org.swiften.redux.core.IReduxAction
import org.swiften.redux.core.IReduxStore

/** Created by haipham on 2018/12/31 */
/**
 * [ISagaEffect] whose [ISagaOutput] deposits some values emitted by [source] into a [IReduxStore]
 * using [actionCreator].
 * @param P The source emission type.
 * @param source The source [ISagaEffect].
 * @param actionCreator Function that creates [IReduxAction] from [P].
 */
internal class PutEffect<P>(
  private val source: ISagaEffect<P>,
  private val actionCreator: (P) -> IReduxAction
) : SagaEffect<Any>() where P : Any {
  override fun invoke(p1: SagaInput): ISagaOutput<Any> {
    return this.source.invoke(p1).map { p1.dispatch(this@PutEffect.actionCreator(it)) as Any }
  }
}
