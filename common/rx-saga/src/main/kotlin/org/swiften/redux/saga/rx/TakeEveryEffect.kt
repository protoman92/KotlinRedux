/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.saga.rx

import org.swiften.redux.core.IReduxAction
import org.swiften.redux.saga.IReduxSagaEffect
import org.swiften.redux.saga.IReduxSagaOutput

/** Created by haipham on 2018/12/23 */
/**
 * [TakeEffect] whose [ReduxSagaOutput] takes all [IReduxAction] that pass some conditions, then
 * flattens and emits all values. Contrast this with [TakeLatestEffect].
 */
internal class TakeEveryEffect<P, R>(
  extractor: Function1<IReduxAction, P?>,
  creator: Function1<P, IReduxSagaEffect<R>>,
  options: TakeEffectOptions
) : TakeEffect<P, R>(extractor, creator, options) {
  override fun flatten(nestedOutput: IReduxSagaOutput<IReduxSagaOutput<R>>) =
    nestedOutput.flatMap { it }
}
