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
 * [TakeEffect] whose output switches to the latest [Redux.IAction] every time
 * one arrives. This is best used for cases whereby we are only interested in
 * the latest value, such as in an autocomplete search implementation. Contrast
 * this with [TakeEveryEffect]
 */
internal class TakeLatestEffect<State, P, R>(
  extract: suspend CoroutineScope.(Redux.IAction) -> P?,
  block: suspend CoroutineScope.(P) -> ReduxSaga.IEffect<State, R>
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
  ): ReduxSaga.Output<R> = nestedOutput.switchMap(this)
}
