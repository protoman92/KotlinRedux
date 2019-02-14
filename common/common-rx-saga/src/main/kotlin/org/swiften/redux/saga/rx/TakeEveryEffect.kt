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
 * @param Action The [IReduxAction] type to perform param extraction.
 * @param P The input value extracted from [IReduxAction].
 * @param R The result emission type.
 * @param cls The [Class] for [Action].
 * @param extractor Function that extracts [P] from [IReduxAction].
 * @param options A [TakeEffectOptions] instance.
 * @param creator Function that creates [ISagaEffect] from [P].
 */
internal class TakeEveryEffect<Action, P, R>(
  cls: Class<Action>,
  extractor: (Action) -> P?,
  options: TakeEffectOptions,
  creator: (P) -> ISagaEffect<R>
) : TakeEffect<Action, P, R>(cls, extractor, options, creator) where
  Action : IReduxAction, P : Any, R : Any {
  override fun flatten(nested: ISagaOutput<ISagaOutput<R>>) = nested.flatMap { it }
}
