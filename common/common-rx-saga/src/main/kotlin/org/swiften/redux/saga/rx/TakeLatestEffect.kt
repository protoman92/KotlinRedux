/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.saga.rx

import org.swiften.redux.core.IReduxAction
import org.swiften.redux.saga.common.ISagaEffect
import org.swiften.redux.saga.common.ISagaOutput

/** Created by haipham on 2018/12/23 */
/**
 * [TakeEffect] whose output switches to the latest [IReduxAction] every time one arrives. This is
 * best used for cases whereby we are only interested in the latest value, such as in an
 * autocomplete search implementation. Contrast this with [TakeEveryEffect].
 */
internal class TakeLatestEffect<P, R>(
  extractor: Function1<IReduxAction, P?>,
  options: TakeEffectOptions,
  creator: Function1<P, ISagaEffect<R>>
) : TakeEffect<P, R>(extractor, options, creator) {
  override fun flatten(nestedOutput: ISagaOutput<ISagaOutput<R>>) = nestedOutput.switchMap { it }
}