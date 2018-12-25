/*
 * Copyright (c) haipham 2018. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.saga

import kotlinx.coroutines.CoroutineScope
import org.swiften.redux.core.Redux

/**
 * Created by haipham on 2018/12/24.
 */
/** Top-level namespace for [ReduxSaga.IEffect] helpers */
object ReduxSagaEffect {
  /** Create a [CallEffect] */
  fun <State, P, R> call(
    param: ReduxSaga.IEffect<State, P>,
    block: ReduxSaga.Output.IMapper<P, R>
  ): ReduxSaga.IEffect<State, R> = CallEffect(param, block)

  /** Create a [CallEffect] with [param] */
  fun <State, P, R> call(param: P, block: ReduxSaga.Output.IMapper<P, R>) =
    this.call(this.just<State, P>(param), block)

  /** Create a [JustEffect] */
  fun <State, R> just(value: R): ReduxSaga.IEffect<State, R> = JustEffect(value)

  /** Create a [TakeEveryEffect] instance. */
  fun <State, P, R> takeEvery(
    extract: ReduxSaga.IPayloadExtractor<Redux.IAction, P>,
    block: ReduxSaga.IEffectCreator<State, P, R>
  ): ReduxSaga.IEffect<State, R> = TakeEveryEffect(extract, block)

  /**
   * Convenience function to create [TakeEveryEffect] for a specific type of
   * [Redux.IAction].
   */
  inline fun <State, reified Action, P, R> takeEveryAction(
    extract: ReduxSaga.IPayloadExtractor<Action, P>,
    block: ReduxSaga.IEffectCreator<State, P, R>
  ) where Action: Redux.IAction = this.takeEvery(
    object : ReduxSaga.IPayloadExtractor<Redux.IAction, P> {
      override suspend operator fun invoke(
        scope: CoroutineScope,
        action: Redux.IAction
      ) = when (action) {is Action -> extract(scope, action); else -> null }
    },
    block
  )

  /** Create a [TakeLatestEffect] instance. */
  fun <State, P, R> takeLatest(
    extract: ReduxSaga.IPayloadExtractor<Redux.IAction, P>,
    block: ReduxSaga.IEffectCreator<State, P, R>
  ): ReduxSaga.IEffect<State, R> = TakeLatestEffect(extract, block)

  /**
   * Convenience function to create [TakeLatestEffect] for a specific type of
   * [Redux.IAction].
   */
  inline fun <State, reified Action, P, R> takeLatestAction(
    extract: ReduxSaga.IPayloadExtractor<Action, P>,
    block: ReduxSaga.IEffectCreator<State, P, R>
  ) where Action: Redux.IAction = this.takeLatest(
    object : ReduxSaga.IPayloadExtractor<Redux.IAction, P> {
      override suspend operator fun invoke(
        scope: CoroutineScope,
        action: Redux.IAction
      ) = when (action) {is Action -> extract(scope, action); else -> null }
    },
    block
  )
}
