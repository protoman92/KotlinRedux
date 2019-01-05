/*
 * Copyright (c) haipham 2018. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.saga

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.channels.ReceiveChannel
import org.swiften.redux.core.Redux

/** Created by haipham on 2018/12/24 */
/** Top-level namespace for [ReduxSagaEffect] helpers */
object ReduxSagaHelper {
  /** Create a [CallEffect] */
  fun <State, P, R> call(
    source: ReduxSagaEffect<State, P>,
    block: suspend CoroutineScope.(P) -> Deferred<R>
  ): ReduxSagaEffect<State, R> = CallEffect(source, block)

  /** Create a [MapEffect] */
  fun <State, P, R> map(
    param: ReduxSagaEffect<State, P>,
    block: suspend CoroutineScope.(P) -> R
  ): ReduxSagaEffect<State, R> = MapEffect(param, block)

  /** Create a [MapEffect] with [param] */
  fun <State, P, R> map(param: P, block: suspend CoroutineScope.(P) -> R) =
    this.map(this.just<State, P>(param), block)

  /** Create a [CatchErrorEffect] instance. */
  fun <State, R> catchError(
    source: ReduxSagaEffect<State, R>,
    catcher: suspend CoroutineScope.(Throwable) -> R
  ): ReduxSagaEffect<State, R> = CatchErrorEffect(source, catcher)

  /** Create a [FilterEffect] */
  fun <State, R> filter(
    source: ReduxSagaEffect<State, R>,
    selector: suspend CoroutineScope.(R) -> Boolean
  ): ReduxSagaEffect<State, R> = FilterEffect(source, selector)

  /** Create a [FromEffect] */
  fun <State, R> from(channel: ReceiveChannel<R>): ReduxSagaEffect<State, R> =
    FromEffect(channel)

  /** Create a [JustEffect] */
  fun <State, R> just(value: R): ReduxSagaEffect<State, R> = JustEffect(value)

  /** Create a [PutEffect] */
  fun <State, P> put(
    source: ReduxSagaEffect<State, P>,
    actionCreator: (P) -> Redux.IAction
  ): ReduxSagaEffect<State, Any> = PutEffect(source, actionCreator)

  /** Create a [SelectEffect] */
  fun <State, R> select(selector: (State) -> R): ReduxSagaEffect<State, R>
    = SelectEffect(selector)

  /** Create a [TakeEveryEffect] instance. */
  fun <State, P, R> takeEvery(
    extract: Function1<Redux.IAction, P?>,
    block: Function1<P, ReduxSagaEffect<State, R>>,
    options: ReduxSaga.TakeOptions = ReduxSaga.TakeOptions()
  ): ReduxSagaEffect<State, R> = TakeEveryEffect(extract, block, options)

  /**
   * Convenience function to create [TakeEveryEffect] for a specific type of
   * [Redux.IAction].
   */
  inline fun <State, reified Action, P, R> takeEveryAction(
    crossinline extract: Function1<Action, P?>,
    noinline block: Function1<P, ReduxSagaEffect<State, R>>,
    options: ReduxSaga.TakeOptions = ReduxSaga.TakeOptions()
  ) where Action: Redux.IAction = this.takeEvery(
    { when (it) {is Action -> extract(it); else -> null } },
    block, options
  )

  /** Create a [TakeLatestEffect] instance. */
  fun <State, P, R> takeLatest(
    extract: Function1<Redux.IAction, P?>,
    block: Function1<P, ReduxSagaEffect<State, R>>,
    options: ReduxSaga.TakeOptions = ReduxSaga.TakeOptions()
  ): ReduxSagaEffect<State, R> = TakeLatestEffect(extract, block, options)

  /**
   * Convenience function to create [TakeLatestEffect] for a specific type of
   * [Redux.IAction].
   */
  inline fun <State, reified Action, P, R> takeLatestAction(
    crossinline extract: Function1<Action, P?>,
    noinline block: Function1<P, ReduxSagaEffect<State, R>>,
    options: ReduxSaga.TakeOptions = ReduxSaga.TakeOptions()
  ) where Action: Redux.IAction = this.takeLatest(
    { when (it) {is Action -> extract(it); else -> null } },
    block, options
  )

  /** Create a [ThenEffect] on [source1] and [source2] */
  fun <State, R, R2, R3> then(
    source1: ReduxSagaEffect<State, R>,
    source2: ReduxSagaEffect<State, R2>,
    selector: Function2<R, R2, R3>
  ) : ReduxSagaEffect<State, R3> = ThenEffect(source1, source2, selector)
}
