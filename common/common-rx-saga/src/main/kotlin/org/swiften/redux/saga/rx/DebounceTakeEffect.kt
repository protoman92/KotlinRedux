/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.saga.rx

import org.swiften.redux.core.IReduxAction
import org.swiften.redux.saga.common.ISagaOutput
import org.swiften.redux.saga.common.TakeActionEffect

/** Created by viethai.pham on 2019/02/14/2 */
/**
 * Perform debounce for a [TakeActionEffect] stream.
 * @param Action The [IReduxAction] type to perform param extraction.
 * @param P The input value extracted from [IReduxAction].
 * @param R The result emission type.
 * @param source The source [TakeActionEffect] instance.
 * @param millis The time to debounce by, in milliseconds.
 */
internal class DebounceTakeEffect<Action, P, R>(
  private val source: TakeActionEffect<Action, P, R>,
  private val millis: Long
) : RxTakeActionEffect<Action, P, R>(source) where Action : IReduxAction, P : Any, R : Any {
  override fun flatten(nested: ISagaOutput<ISagaOutput<R>>): ISagaOutput<R> {
    return this.source.flatten(nested.debounce(this.millis))
  }
}
