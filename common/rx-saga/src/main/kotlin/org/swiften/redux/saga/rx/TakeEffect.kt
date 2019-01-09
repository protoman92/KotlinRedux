/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.saga.rx

import io.reactivex.processors.PublishProcessor
import org.swiften.redux.core.IReduxAction
import org.swiften.redux.saga.IReduxSagaOutput
import org.swiften.redux.saga.Input
import org.swiften.redux.saga.ReduxSagaEffect

/** Created by haipham on 2018/12/23 */
/**
 * [TakeEffect] instances produces streams that filter [IReduxAction] with [extract] and pluck out
 * the appropriate ones to perform additional work on with [block].
 */
internal abstract class TakeEffect<State, P, R>(
  private val extract: Function1<IReduxAction, P?>,
  private val block: Function1<P, ReduxSagaEffect<State, R>>,
  private val options: TakeEffectOptions
) : ReduxSagaEffect<State, R> {
  /**
   * Flatten an [ReduxSagaOutput] that streams [ReduxSagaOutput] to access the values streamed by
   * the inner [ReduxSagaOutput].
   */
  abstract fun flattenOutput(nestedOutput: IReduxSagaOutput<IReduxSagaOutput<R>>): IReduxSagaOutput<R>

  @Suppress("MoveLambdaOutsideParentheses")
  override operator fun invoke(p1: Input<State>): IReduxSagaOutput<R> {
    val subject = PublishProcessor.create<P>()

    val nested = ReduxSagaOutput(
      p1.scope, subject,
      { this@TakeEffect.extract(it)?.also { subject.offer(it) } })
      .debounce(this@TakeEffect.options.debounceMillis)
      .map { this@TakeEffect.block(it).invoke(p1) }

    return this.flattenOutput(nested)
  }
}
