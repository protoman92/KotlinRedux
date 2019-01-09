/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.saga.rx

import io.reactivex.Flowable
import io.reactivex.Single
import org.swiften.redux.core.IReduxAction
import org.swiften.redux.saga.ReduxSagaEffect

/** Created by haipham on 2018/12/24 */
/** Top-level namespace for [ReduxSagaEffect] helpers */
object ReduxSagaEffects {
  /** Create a [CallEffect] */
  fun <State, P, R> call(
    source: ReduxSagaEffect<State, P>,
    block: (P) -> Single<R>
  ): ReduxSagaEffect<State, R> =
    CallEffect(source, block)

  /** Create a [FromEffect] */
  fun <State, R> from(stream: Flowable<R>): ReduxSagaEffect<State, R> =
    FromEffect(stream)

  /** Create a [JustEffect] */
  fun <State, R> just(value: R): ReduxSagaEffect<State, R> =
    JustEffect(value)

  /** Create a [SelectEffect] */
  fun <State, R> select(selector: (State) -> R): ReduxSagaEffect<State, R> =
    SelectEffect(selector)

  /** Create a [TakeEveryEffect] instance. */
  fun <State, P, R> takeEvery(
    extract: Function1<IReduxAction, P?>,
    block: Function1<P, ReduxSagaEffect<State, R>>,
    options: TakeEffectOptions = TakeEffectOptions()
  ): ReduxSagaEffect<State, R> =
    TakeEveryEffect(extract, block, options)

  /** Convenience function to create [TakeEveryEffect] for a specific type of [IReduxAction] */
  inline fun <State, reified Action, P, R> takeEveryAction(
    crossinline extract: Function1<Action, P?>,
    noinline block: Function1<P, ReduxSagaEffect<State, R>>,
    options: TakeEffectOptions = TakeEffectOptions()
  ) where Action: IReduxAction = takeEvery(
    {
      when (it) {
        is Action -> extract(it); else -> null
      }
    },
    block, options
  )

  /** Create a [TakeLatestEffect] instance. */
  fun <State, P, R> takeLatest(
    extract: Function1<IReduxAction, P?>,
    block: Function1<P, ReduxSagaEffect<State, R>>,
    options: TakeEffectOptions = TakeEffectOptions()
  ): ReduxSagaEffect<State, R> =
    TakeLatestEffect(extract, block, options)

  /** Convenience function to create [TakeLatestEffect] for a specific type of [IReduxAction] */
  inline fun <State, reified Action, P, R> takeLatestAction(
    crossinline extract: Function1<Action, P?>,
    noinline block: Function1<P, ReduxSagaEffect<State, R>>,
    options: TakeEffectOptions = TakeEffectOptions()
  ) where Action: IReduxAction = takeLatest(
    {
      when (it) {
        is Action -> extract(it); else -> null
      }
    },
    block, options
  )
}
