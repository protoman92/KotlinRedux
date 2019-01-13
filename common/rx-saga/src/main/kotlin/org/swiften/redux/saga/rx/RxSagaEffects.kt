/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.saga.rx

import io.reactivex.Flowable
import io.reactivex.Single
import org.swiften.redux.core.IReduxAction
import org.swiften.redux.saga.ReduxSagaEffect
import org.swiften.redux.saga.ReduxSagaEffects

/** Created by haipham on 2019/01/13 */
/** Create a [CallEffect] */
fun <State, P, R> ReduxSagaEffects.call(
  source: ReduxSagaEffect<State, P>,
  block: (P) -> Single<R>
): ReduxSagaEffect<State, R> = CallEffect(source, block)

/** Create a [FromEffect] */
fun <State, R> ReduxSagaEffects.from(stream: Flowable<R>): ReduxSagaEffect<State, R> =
  FromEffect(stream)

/** Create a [JustEffect] */
fun <State, R> ReduxSagaEffects.just(value: R): ReduxSagaEffect<State, R> = JustEffect(value)

/** Call [ReduxSagaEffects.put] with [ReduxSagaEffects.just] */
fun <State, P> ReduxSagaEffects.put(value: P, actionCreator: (P) -> IReduxAction):
  ReduxSagaEffect<State, Any> = ReduxSagaEffects.put(this.just(value), actionCreator)

/** Create a [JustPutEffect] */
fun <State> ReduxSagaEffects.justPut(action: IReduxAction): ReduxSagaEffect<State, Any> =
  JustPutEffect(action)

/** Create a [SelectEffect] */
fun <State, R> ReduxSagaEffects.select(selector: (State) -> R): ReduxSagaEffect<State, R> =
  SelectEffect(selector)

/** Create a [TakeEveryEffect] instance. */
fun <State, P, R> ReduxSagaEffects.takeEvery(
  extract: Function1<IReduxAction, P?>,
  block: Function1<P, ReduxSagaEffect<State, R>>,
  options: TakeEffectOptions = TakeEffectOptions()
): ReduxSagaEffect<State, R> = TakeEveryEffect(extract, block, options)

/** Convenience function to create [TakeEveryEffect] for a specific type of [IReduxAction] */
inline fun <State, reified Action, P, R> ReduxSagaEffects.takeEveryAction(
  crossinline extract: Function1<Action, P?>,
  noinline block: Function1<P, ReduxSagaEffect<State, R>>,
  options: TakeEffectOptions = TakeEffectOptions()
) where Action: IReduxAction = this.takeEvery(
  {
    when (it) {
      is Action -> extract(it); else -> null
    }
  },
  block, options
)

/** Create a [TakeLatestEffect] instance. */
fun <State, P, R> ReduxSagaEffects.takeLatest(
  extract: Function1<IReduxAction, P?>,
  block: Function1<P, ReduxSagaEffect<State, R>>,
  options: TakeEffectOptions = TakeEffectOptions()
): ReduxSagaEffect<State, R> =
  TakeLatestEffect(extract, block, options)

/** Convenience function to create [TakeLatestEffect] for a specific type of [IReduxAction] */
inline fun <State, reified Action, P, R> ReduxSagaEffects.takeLatestAction(
  crossinline extract: Function1<Action, P?>,
  noinline block: Function1<P, ReduxSagaEffect<State, R>>,
  options: TakeEffectOptions = TakeEffectOptions()
) where Action: IReduxAction = this.takeLatest(
  {
    when (it) {
      is Action -> extract(it); else -> null
    }
  },
  block, options
)
