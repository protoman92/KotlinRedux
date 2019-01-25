/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.saga.rx

import io.reactivex.processors.PublishProcessor
import org.swiften.redux.core.IReduxAction
import org.swiften.redux.saga.ISagaEffect
import org.swiften.redux.saga.ISagaOutput
import org.swiften.redux.saga.SagaInput
import org.swiften.redux.saga.SagaEffect

/** Created by haipham on 2018/12/23 */
/**
 * [TakeEffect] instances produces streams that filter [IReduxAction] with [extractor] and pluck out
 * the appropriate ones to perform additional work on with [creator].
 */
internal abstract class TakeEffect<P, R>(
  private val extractor: Function1<IReduxAction, P?>,
  private val options: TakeEffectOptions,
  private val creator: Function1<P, ISagaEffect<R>>
) : SagaEffect<R>() {
  /**
   * Flatten an [ISagaOutput] that streams [ISagaOutput] to access the values streamed by
   * the inner [ISagaOutput].
   */
  abstract fun flatten(nestedOutput: ISagaOutput<ISagaOutput<R>>): ISagaOutput<R>

  @Suppress("MoveLambdaOutsideParentheses")
  override operator fun invoke(p1: SagaInput): ISagaOutput<R> {
    val subject = PublishProcessor.create<P>()

    val nested = SagaOutput(p1.scope, subject,
      { p -> this@TakeEffect.extractor(p)?.also { subject.offer(it) } })
      .debounce(this@TakeEffect.options.debounceMillis)
      .map { this@TakeEffect.creator(it).invoke(p1) }

    return this.flatten(nested)
  }
}
