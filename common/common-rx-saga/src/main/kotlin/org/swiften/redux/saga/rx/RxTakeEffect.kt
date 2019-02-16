/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.saga.rx

import io.reactivex.processors.PublishProcessor
import org.swiften.redux.core.EmptyJob
import org.swiften.redux.core.IReduxAction
import org.swiften.redux.saga.common.ISagaEffect
import org.swiften.redux.saga.common.ISagaOutput
import org.swiften.redux.saga.common.SagaInput
import org.swiften.redux.saga.common.TakeEffect

/** Created by viethai.pham on 2019/02/14 */
/**
 * [PublishProcessor]-based implementation of [TakeEffect].
 * @param Action The [IReduxAction] type to perform param extraction.
 * @param P The input value extracted from [IReduxAction].
 * @param R The result emission type.
 * @param cls See [TakeEffect.cls].
 * @param extractor See [TakeEffect.extractor].
 * @param creator See [TakeEffect.creator].
 */
internal abstract class RxTakeEffect<Action, P, R>(
  cls: Class<Action>,
  extractor: (Action) -> P?,
  creator: (P) -> ISagaEffect<R>
) : TakeEffect<Action, P, R>(cls, extractor, creator) where Action : IReduxAction, P : Any, R : Any {
  constructor(source: TakeEffect<Action, P, R>) : this(source.cls, source.extractor, source.creator)

  override operator fun invoke(p1: SagaInput): ISagaOutput<R> {
    val subject = PublishProcessor.create<P>().toSerialized()

    val nested = SagaOutput(p1.scope, subject) { p ->
      if (cls.isInstance(p)) {
        this@RxTakeEffect.extractor(cls.cast(p))?.also { subject.onNext(it) }
      }

      EmptyJob
    }

    return this.flatten(nested.map { this@RxTakeEffect.creator(it).invoke(p1) })
  }
}
