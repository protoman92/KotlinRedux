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
 * [RxTakeEffect] whose output switches to the latest [IReduxAction] every time one arrives. This is
 * best used for cases whereby we are only interested in the latest value, such as in an
 * autocomplete search implementation. Contrast this with [TakeEveryEffect].
 * @param Action The [IReduxAction] type to perform param extraction.
 * @param P The input value extracted from [IReduxAction].
 * @param R The result emission type.
 * @param cls The [Class] for [Action].
 * @param extractor Function that extracts [P] from [IReduxAction].
 * @param creator Function that creates [ISagaEffect] from [P].
 */
internal class TakeLatestEffect<Action, P, R>(
  cls: Class<Action>,
  extractor: (Action) -> P?,
  creator: Function1<P, ISagaEffect<R>>
) : RxTakeEffect<Action, P, R>(cls, extractor, creator)
  where Action : IReduxAction, P : Any, R : Any {
  override fun flatten(nested: ISagaOutput<ISagaOutput<R>>) = nested.switchMap { it }
}
