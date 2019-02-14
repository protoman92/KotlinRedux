/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.saga.rx

import io.reactivex.processors.PublishProcessor
import org.swiften.redux.core.IReduxAction
import org.swiften.redux.saga.common.ISagaEffect
import org.swiften.redux.saga.common.ISagaOutput
import org.swiften.redux.saga.common.SagaEffect
import org.swiften.redux.saga.common.SagaInput

/** Created by haipham on 2018/12/23 */
/**
 * [TakeEffect] instances produces streams that filter [IReduxAction] with [extractor] and pluck out
 * the appropriate ones to perform additional work on with [creator].
 * @param Action The [IReduxAction] type to perform param extraction.
 * @param P The input value extracted from [IReduxAction].
 * @param R The result emission type.
 * @param cls The [Class] for [Action].
 * @param extractor Function that extracts [P] from [IReduxAction].
 * @param options A [TakeEffectOptions] instance.
 * @param creator Function that creates [ISagaEffect] from [P].
 */
internal abstract class TakeEffect<Action, P, R>(
  private val cls: Class<Action>,
  private val extractor: (Action) -> P?,
  private val options: TakeEffectOptions,
  private val creator: (P) -> ISagaEffect<R>
) : SagaEffect<R>() where Action : IReduxAction, P : Any, R : Any {
  /**
   * Flatten an [ISagaOutput] that streams [ISagaOutput] to access the values streamed by
   * the inner [ISagaOutput].
   * @param nested The nested [ISagaOutput] instance that emits [ISagaOutput].
   * @return An [ISagaOutput] instance.
   */
  abstract fun flatten(nested: ISagaOutput<ISagaOutput<R>>): ISagaOutput<R>

  override operator fun invoke(p1: SagaInput): ISagaOutput<R> {
    val subject = PublishProcessor.create<P>().toSerialized()

    val nested = SagaOutput(p1.scope, subject) { p ->
      if (cls.isInstance(p)) {
        this@TakeEffect.extractor(cls.cast(p))?.also { subject.onNext(it) }
      }
    }

    return this.flatten(nested
      .debounce(this@TakeEffect.options.debounceMillis)
      .map { this@TakeEffect.creator(it).invoke(p1) })
  }
}
