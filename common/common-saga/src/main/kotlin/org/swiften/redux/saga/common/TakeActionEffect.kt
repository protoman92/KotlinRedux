/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.saga.common

import io.reactivex.processors.PublishProcessor
import org.swiften.redux.core.EmptyAwaitable
import org.swiften.redux.core.IReduxAction

/** Created by haipham on 2018/12/23 */
/**
 * [TakeActionEffect] instances produces streams that filter [IReduxAction] with [extractor] and
 * pluck out the appropriate ones to perform additional work.
 * @param Action The [IReduxAction] type to perform param extraction.
 * @param R The result emission type.
 * @param cls The [Class] for [Action].
 * @param extractor Function that extracts [P] from [IReduxAction].
 */
class TakeActionEffect<Action, R>(
  val cls: Class<Action>,
  val extractor: (Action) -> R?
) : SagaEffect<R>() where Action : IReduxAction, R : Any {
  override fun invoke(p1: SagaInput): ISagaOutput<R> {
    val subject = PublishProcessor.create<R>().toSerialized()

    return SagaOutput(p1.monitor, subject.onBackpressureBuffer().observeOn(p1.scheduler)) { a ->
      if (cls.isInstance(a)) {
        this@TakeActionEffect.extractor(cls.cast(a))?.also { subject.onNext(it) }
      }

      EmptyAwaitable
    }
  }
}
