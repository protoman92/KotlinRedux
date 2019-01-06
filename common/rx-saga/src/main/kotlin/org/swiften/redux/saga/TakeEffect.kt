/*
 * Copyright (c) haipham 2018. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.saga

import io.reactivex.processors.PublishProcessor
import org.swiften.redux.core.Redux
import java.util.concurrent.TimeUnit

/** Created by haipham on 2018/12/23 */
/**
 * [TakeEffect] instances produces streams that filter [Redux.IAction] with
 * [extract] and pluck out the appropriate ones to perform additional work on
 * with [block].
 */
internal abstract class TakeEffect<State, P, R>(
  private val extract: Function1<Redux.IAction, P?>,
  private val block: Function1<P, ReduxSagaEffect<State, R>>,
  private val options: ReduxSaga.TakeOptions
) : ReduxSagaEffect<State, R> {
  /**
   * Flatten an [ReduxSaga.Output] that streams [ReduxSaga.Output] to access
   * the values streamed by the inner [ReduxSaga.Output].
   */
  abstract fun flattenOutput(
    nestedOutput: ReduxSaga.Output<ReduxSaga.Output<R>>
  ): ReduxSaga.Output<R>

  override operator fun invoke(p1: ReduxSaga.Input<State>): ReduxSaga.Output<R> {
    val subject = PublishProcessor.create<Redux.IAction>()

    val nested = ReduxSaga.Output(p1.scope,
      subject
        .map(this.extract)
        .filter { it != null }
        .map { this@TakeEffect.block(it).invoke(p1) }
        .debounce(this.options.debounceMillis, TimeUnit.MILLISECONDS)
    ) { action -> subject.offer(action) }

    return this.flattenOutput(nested)
  }
}