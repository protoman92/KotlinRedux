/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.saga.common

import io.reactivex.processors.PublishProcessor
import org.swiften.redux.core.EmptyJob
import org.swiften.redux.core.IReduxAction

/** Created by haipham on 2018/12/23 */
/**
 * [TakeActionEffect] instances produces streams that filter [IReduxAction] with [extractor] and
 * pluck out the appropriate ones to perform additional work on with [creator].
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

  override fun invoke(p1: SagaInput): ISagaOutput<R> {
    val subject = PublishProcessor.create<P>().toSerialized()

    val nested = SagaOutput(
      p1.scope,
      p1.monitor,
      subject.onBackpressureBuffer(),
      { subject.onComplete() }) { a ->
      if (cls.isInstance(a)) {
        this@TakeActionEffect.extractor(cls.cast(a))?.also { subject.onNext(it) }
      }

      EmptyJob
    }

    return this.flatten(nested.map { this@TakeActionEffect.creator(it).invoke(p1) })
  }
}

/**
 * [TakeActionEffect] whose [SagaOutput] takes all [IReduxAction] that pass some conditions, then
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
) : TakeActionEffect<Action, P, R>(cls, extractor, creator) where Action : IReduxAction, P : Any, R : Any {
  override fun flatten(nested: ISagaOutput<ISagaOutput<R>>) = nested.flatMap { it }
}

/**
 * [TakeActionEffect] whose output switches to the latest [IReduxAction] every time one arrives. This is
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
) : TakeActionEffect<Action, P, R>(cls, extractor, creator) where Action : IReduxAction, P : Any, R : Any {
  override fun flatten(nested: ISagaOutput<ISagaOutput<R>>) = nested.switchMap { it }
}
