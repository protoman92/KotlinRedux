/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.saga.rx

import io.reactivex.processors.PublishProcessor
import org.swiften.redux.core.IReduxAction
import org.swiften.redux.saga.IReduxSagaEffect
import org.swiften.redux.saga.IReduxSagaOutput
import org.swiften.redux.saga.Input
import org.swiften.redux.saga.ReduxSagaEffect

/** Created by haipham on 2018/12/23 */
/**
 * [TakeEffect] instances produces streams that filter [IReduxAction] with [extractor] and pluck out
 * the appropriate ones to perform additional work on with [creator].
 */
internal abstract class TakeEffect<P, R>(
  private val extractor: Function1<IReduxAction, P?>,
  private val creator: Function1<P, IReduxSagaEffect<R>>,
  private val options: TakeEffectOptions
) : ReduxSagaEffect<R>() {
  /**
   * Flatten an [IReduxSagaOutput] that streams [IReduxSagaOutput] to access the values streamed by
   * the inner [IReduxSagaOutput].
   */
  abstract fun flatten(nestedOutput: IReduxSagaOutput<IReduxSagaOutput<R>>): IReduxSagaOutput<R>

  @Suppress("MoveLambdaOutsideParentheses")
  override operator fun invoke(p1: Input): IReduxSagaOutput<R> {
    val subject = PublishProcessor.create<P>()

    val nested = ReduxSagaOutput(p1.scope, subject,
      { p -> this@TakeEffect.extractor(p)?.also { subject.offer(it) } })
      .debounce(this@TakeEffect.options.debounceMillis)
      .map { this@TakeEffect.creator(it).invoke(p1) }

    return this.flatten(nested)
  }
}
