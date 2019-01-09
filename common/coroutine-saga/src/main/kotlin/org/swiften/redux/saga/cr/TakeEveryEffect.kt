/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.saga.cr

import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.swiften.redux.core.IReduxAction
import org.swiften.redux.saga.CommonSaga
import org.swiften.redux.saga.ReduxSagaEffect

/** Created by haipham on 2018/12/23 */
/**
 * [TakeEffect] whose [ReduxSaga.Output] takes all [IReduxAction] that pass some conditions, then
 * flattens and emits all values. Contrast this with [TakeLatestEffect].
 */
internal class TakeEveryEffect<State, P, R>(
  extract: Function1<IReduxAction, P?>,
  block: Function1<P, ReduxSagaEffect<State, R>>,
  options: ReduxSaga.TakeOptions
) : TakeEffect<State, P, R>(extract, block, options) {
  @ExperimentalCoroutinesApi
  override fun flattenOutput(
    nestedOutput: CommonSaga.IOutput<CommonSaga.IOutput<R>>
  ) = nestedOutput.flatMap { it }
}
