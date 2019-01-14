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
 * [TakeEffect] whose output switches to the latest [IReduxAction] every time one arrives. This
 * is best used for cases whereby we are only interested in the latest value, such as in an
 * autocomplete search implementation. Contrast this with [TakeEveryEffect]
 */
internal class TakeLatestEffect<State, P, R>(
  extractor: Function1<IReduxAction, P?>,
  creator: Function1<P, ReduxSagaEffect<State, R>>,
  options: TakeEffectOptions
) : TakeEffect<State, P, R>(extractor, creator, options) {
  @ExperimentalCoroutinesApi
  override fun flattenOutput(nestedOutput: IReduxSagaOutput<IReduxSagaOutput<R>>) =
    nestedOutput.switchMap { it }
}
