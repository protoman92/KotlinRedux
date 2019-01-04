/*
 * Copyright (c) haipham 2018. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.saga

import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.swiften.redux.core.Redux

/** Created by haipham on 2018/12/23 */
/**
 * [TakeEffect] whose [ReduxSaga.Output] takes all [Redux.IAction] that pass
 * some conditions, then flattens and emits all values. Contrast this with
 * [TakeLatestEffect].
 */
internal class TakeEveryEffect<State, P, R>(
  extract: Function1<Redux.IAction, P?>,
  block: Function1<P, ReduxSagaEffect<State, R>>
) : TakeEffect<State, P, R>(extract, block) {
  @ExperimentalCoroutinesApi
  override fun flattenOutput(
    nestedOutput: ReduxSaga.Output<ReduxSaga.Output<R>>
  ) = nestedOutput.flatMap { it }
}
