/*
 * Copyright (c) haipham 2018. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.saga

import org.swiften.redux.core.Redux

/**
 * Created by haipham on 2018/12/24.
 */
/** Top-level namespace for [ReduxSaga.IEffect] helpers */
object ReduxSagaHelper {
  /** Create a [CallEffect] */
  fun <State, P, R> call(
    param: ReduxSagaEffect<State, P>,
    block: ReduxSaga.Output.IMapper<P, R>
  ): ReduxSagaEffect<State, R> = CallEffect(param, block)

  /** Create a [CallEffect] with [param] */
  fun <State, P, R> call(param: P, block: ReduxSaga.Output.IMapper<P, R>) =
    this.call(this.just<State, P>(param), block)

  /** Create a [CatchErrorEffect] instance. */
  fun <State, R> catchError(
    source: ReduxSagaEffect<State, R>,
    catcher: ReduxSaga.Output.IErrorCatcher<R>
  ): ReduxSagaEffect<State, R> = CatchErrorEffect(source, catcher)

  /** Create a [JustEffect] */
  fun <State, R> just(value: R): ReduxSagaEffect<State, R> = JustEffect(value)

  /** Create a [TakeEveryEffect] instance. */
  fun <State, P, R> takeEvery(
    extract: Function1<Redux.IAction, P?>,
    block: Function1<P, ReduxSagaEffect<State, R>>
  ): ReduxSagaEffect<State, R> = TakeEveryEffect(extract, block)

  /**
   * Convenience function to create [TakeEveryEffect] for a specific type of
   * [Redux.IAction].
   */
  inline fun <State, reified Action, P, R> takeEveryAction(
    crossinline extract: Function1<Action, P?>,
    noinline block: Function1<P, ReduxSagaEffect<State, R>>
  ) where Action: Redux.IAction = this.takeEvery(
    { when (it) {is Action -> extract(it); else -> null } },
    block
  )

  /** Create a [TakeLatestEffect] instance. */
  fun <State, P, R> takeLatest(
    extract: Function1<Redux.IAction, P?>,
    block: Function1<P, ReduxSagaEffect<State, R>>
  ): ReduxSagaEffect<State, R> = TakeLatestEffect(extract, block)

  /**
   * Convenience function to create [TakeLatestEffect] for a specific type of
   * [Redux.IAction].
   */
  inline fun <State, reified Action, P, R> takeLatestAction(
    crossinline extract: Function1<Action, P?>,
    noinline block: Function1<P, ReduxSagaEffect<State, R>>
  ) where Action: Redux.IAction = this.takeLatest(
    { when (it) {is Action -> extract(it); else -> null } },
    block
  )

  /** Create a [ThenEffect] on [source1] and [source2] */
  fun <State, R, R2, R3> then(
    source1: ReduxSagaEffect<State, R>,
    source2: ReduxSagaEffect<State, R2>,
    selector: Function2<R, R2, R3>
  ) : ReduxSagaEffect<State, R3> = ThenEffect(source1, source2, selector)
}
