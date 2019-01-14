/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.saga.cr

import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.swiften.redux.core.IReduxAction
import org.swiften.redux.saga.IReduxSagaOutput
import org.swiften.redux.saga.ReduxSagaEffect

/** Created by haipham on 2018/12/23 */
/**
 * [TakeEffect] whose [ReduxSagaOutput] takes all [IReduxAction] that pass some conditions, then
 * flattens and emits all values. Contrast this with [TakeLatestEffect].
 */
internal class TakeEveryEffect<State, P, R>(
  extractor: Function1<IReduxAction, P?>,
  transformer: Function1<P, ReduxSagaEffect<State, R>>,
  options: TakeEffectOptions
) : TakeEffect<State, P, R>(extractor, transformer, options) {
  @ExperimentalCoroutinesApi
  override fun flattenOutput(nestedOutput: IReduxSagaOutput<IReduxSagaOutput<R>>) =
    nestedOutput.flatMap { it }
}
