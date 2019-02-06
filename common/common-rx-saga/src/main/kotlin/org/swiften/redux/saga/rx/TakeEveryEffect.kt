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
 * [TakeEffect] whose [SagaOutput] takes all [IReduxAction] that pass some conditions, then
 * flattens and emits all values. Contrast this with [TakeLatestEffect].
 * @param P The input value extracted from [IReduxAction].
 * @param R The result emission type.
 * @param extractor Function that extracts [P] from [IReduxAction].
 * @param options A [TakeEffectOptions] instance.
 * @param creator Function that creates [ISagaEffect] from [P].
 */
internal class TakeEveryEffect<P, R>(
  extractor: Function1<IReduxAction, P?>,
  options: TakeEffectOptions,
  creator: Function1<P, ISagaEffect<R>>
) : TakeEffect<P, R>(extractor, options, creator) {
  override fun flatten(nested: ISagaOutput<ISagaOutput<R>>) = nested.flatMap { it }
}
