/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.saga.cr

import org.swiften.redux.core.IReduxAction
import org.swiften.redux.saga.ReduxSagaEffect

/** Created by haipham on 2018/12/24 */
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
  extractor: Function1<IReduxAction, P?>,
  creator: Function1<P, ReduxSagaEffect<State, R>>,
  options: TakeEffectOptions = TakeEffectOptions()
): ReduxSagaEffect<State, R> = TakeEveryEffect(extractor, creator, options)

/** Convenience function to create [TakeEveryEffect] for a specific type of [IReduxAction] */
inline fun <State, reified Action, P, R> ReduxSagaEffects.takeEveryAction(
  crossinline extractor: Function1<Action, P?>,
  noinline creator: Function1<P, ReduxSagaEffect<State, R>>,
  options: TakeEffectOptions = TakeEffectOptions()
) where Action: IReduxAction = this.takeEvery(
  {
    when (it) {
      is Action -> extractor(it); else -> null
    }
  },
  creator, options
)

/** Create a [TakeLatestEffect] instance. */
fun <State, P, R> ReduxSagaEffects.takeLatest(
  extractor: Function1<IReduxAction, P?>,
  creator: Function1<P, ReduxSagaEffect<State, R>>,
  options: TakeEffectOptions = TakeEffectOptions()
): ReduxSagaEffect<State, R> =
  TakeLatestEffect(extractor, creator, options)

/** Convenience function to create [TakeLatestEffect] for a specific type of [IReduxAction] */
inline fun <State, reified Action, P, R> ReduxSagaEffects.takeLatestAction(
  crossinline extractor: Function1<Action, P?>,
  noinline creator: Function1<P, ReduxSagaEffect<State, R>>,
  options: TakeEffectOptions = TakeEffectOptions()
) where Action: IReduxAction = this.takeLatest(
  { when (it) { is Action -> extractor(it); else -> null } },
  creator, options
)
