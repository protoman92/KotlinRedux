/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.saga.common

import org.swiften.redux.core.IReduxAction

/** Created by haipham on 2018/12/23 */
/**
 * [TakeActionEffect] instances produces streams that filter [IReduxAction] with [extractor] and pluck out
 * the appropriate ones to perform additional work on with [creator].
 * @param Action The [IReduxAction] type to perform param extraction.
 * @param P The input value extracted from [IReduxAction].
 * @param R The result emission type.
 * @param cls The [Class] for [Action].
 * @param extractor Function that extracts [P] from [IReduxAction].
 * @param creator Function that creates [ISagaEffect] from [P].
 */
abstract class TakeActionEffect<Action, P, R>(
  val cls: Class<Action>,
  val extractor: (Action) -> P?,
  val creator: (P) -> ISagaEffect<R>
) : SagaEffect<R>() where Action : IReduxAction, P : Any, R : Any {
  /**
   * Flatten an [ISagaOutput] that streams [ISagaOutput] to access the values streamed by the inner
   * [ISagaOutput].
   * @param nested The nested [ISagaOutput] instance that emits [ISagaOutput].
   * @return An [ISagaOutput] instance.
   */
  abstract fun flatten(nested: ISagaOutput<ISagaOutput<R>>): ISagaOutput<R>
}
