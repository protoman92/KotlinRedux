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
import org.swiften.redux.saga.common.TakeActionEffect

/** Created by viethai.pham on 2019/02/14 */
/**
 * [PublishProcessor]-based implementation of [TakeActionEffect].
 * @param Action The [IReduxAction] type to perform param extraction.
 * @param P The input value extracted from [IReduxAction].
 * @param R The result emission type.
 * @param cls See [TakeActionEffect.cls].
 * @param extractor See [TakeActionEffect.extractor].
 * @param creator See [TakeActionEffect.creator].
 */
internal abstract class RxTakeActionEffect<Action, P, R>(
  cls: Class<Action>,
  extractor: (Action) -> P?,
  creator: (P) -> ISagaEffect<R>
) : TakeActionEffect<Action, P, R>(cls, extractor, creator) where Action : IReduxAction, P : Any, R : Any {
  constructor(source: TakeActionEffect<Action, P, R>) : this(source.cls, source.extractor, source.creator)

  override fun invoke(p1: SagaInput): ISagaOutput<R> {
    val subject = PublishProcessor.create<P>().toSerialized()

    val nested = SagaOutput(p1.scope, p1.monitor, subject.onBackpressureBuffer(), { subject.onComplete() }) { a ->
      if (cls.isInstance(a)) {
        this@RxTakeActionEffect.extractor(cls.cast(a))?.also { subject.onNext(it) }
      }

      EmptyJob
    }

    return this.flatten(nested.map { this@RxTakeActionEffect.creator(it).invoke(p1) })
  }
}

/**
 * [RxTakeActionEffect] whose [SagaOutput] takes all [IReduxAction] that pass some conditions, then
 * flattens and emits all values. Contrast this with [TakeLatestActionEffect].
 * @param Action The [IReduxAction] type to perform param extraction.
 * @param P The input value extracted from [IReduxAction].
 * @param R The result emission type.
 * @param cls The [Class] for [Action].
 * @param extractor Function that extracts [P] from [IReduxAction].
 * @param creator Function that creates [ISagaEffect] from [P].
 */
internal class TakeEveryActionEffect<Action, P, R>(
  cls: Class<Action>,
  extractor: (Action) -> P?,
  creator: (P) -> ISagaEffect<R>
) : RxTakeActionEffect<Action, P, R>(cls, extractor, creator) where Action : IReduxAction, P : Any, R : Any {
  override fun flatten(nested: ISagaOutput<ISagaOutput<R>>) = nested.flatMap { it }
}

/**
 * [RxTakeActionEffect] whose output switches to the latest [IReduxAction] every time one arrives. This is
 * best used for cases whereby we are only interested in the latest value, such as in an
 * autocomplete search implementation. Contrast this with [TakeEveryActionEffect].
 * @param Action The [IReduxAction] type to perform param extraction.
 * @param P The input value extracted from [IReduxAction].
 * @param R The result emission type.
 * @param cls The [Class] for [Action].
 * @param extractor Function that extracts [P] from [IReduxAction].
 * @param creator Function that creates [ISagaEffect] from [P].
 */
internal class TakeLatestActionEffect<Action, P, R>(
  cls: Class<Action>,
  extractor: (Action) -> P?,
  creator: Function1<P, ISagaEffect<R>>
) : RxTakeActionEffect<Action, P, R>(cls, extractor, creator) where Action : IReduxAction, P : Any, R : Any {
  override fun flatten(nested: ISagaOutput<ISagaOutput<R>>) = nested.switchMap { it }
}
