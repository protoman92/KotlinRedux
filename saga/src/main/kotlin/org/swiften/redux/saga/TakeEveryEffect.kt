/*
 * Copyright (c) haipham 2018. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.saga

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.swiften.redux.core.Redux

/**
 * Created by haipham on 2018/12/23.
 */
/**
 * [TakeEffect] whose [ReduxSaga.Output] takes all [Redux.IAction] that pass
 * some conditions, then flattens and emits all values. Contrast this with
 * [TakeLatestEffect].
 */
internal class TakeEveryEffect<State, P, R>(
  extract: ReduxSaga.IPayloadExtractor<Redux.IAction, P>,
  block: ReduxSaga.IEffectCreator<State, P, R>
) :
  TakeEffect<State, P, R>(extract, block),
  ReduxSaga.Output.IFlatMapper<ReduxSaga.Output<R>, R>
{
  override suspend operator fun invoke(
    scope: CoroutineScope,
    value: ReduxSaga.Output<R>
  ) = value

  @ExperimentalCoroutinesApi
  override fun flattenOutput(
    nestedOutput: ReduxSaga.Output<ReduxSaga.Output<R>>
  ) = nestedOutput.flatMap(this)
}
