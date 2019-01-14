/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.saga.rx

import io.reactivex.Flowable
import io.reactivex.Single
import org.swiften.redux.core.IReduxAction
import org.swiften.redux.saga.CommonSagaEffects
import org.swiften.redux.saga.IReduxSagaEffect
import org.swiften.redux.saga.ReduxSagaEffect

/** Created by haipham on 2019/01/13 */
/** Top-level namespace for Rx-based [IReduxSagaEffect] */
object ReduxSagaEffects {
  /** Create a [CallEffect] */
  @JvmStatic
  fun <State, P, R> call(source: IReduxSagaEffect<State, P>, block: (P) -> Single<R>):
    IReduxSagaEffect<State, R> = CallEffect(source, block)

  /** Create a [FromEffect] */
  @JvmStatic
  fun <State, R> from(stream: Flowable<R>): ReduxSagaEffect<State, R> = FromEffect(stream)

  /** Create a [JustEffect] */
  @JvmStatic
  fun <State, R> just(value: R): ReduxSagaEffect<State, R> = JustEffect(value)

  /** Call [CommonSagaEffects.put] with [CommonSagaEffects.just] */
  @JvmStatic
  fun <State, P> put(value: P, actionCreator: (P) -> IReduxAction):
    ReduxSagaEffect<State, Any> = CommonSagaEffects.put<State, P>(actionCreator)(this.just(value))

  /** Create a [JustPutEffect] */
  @JvmStatic
  fun <State> justPut(action: IReduxAction): ReduxSagaEffect<State, Any> =
    JustPutEffect(action)

  /** Create a [SelectEffect] */
  @JvmStatic
  fun <State, R> select(selector: (State) -> R): ReduxSagaEffect<State, R> =
    SelectEffect(selector)

  /** Create a [TakeEveryEffect] instance. */
  @JvmStatic
  fun <State, P, R> takeEvery(
    extract: Function1<IReduxAction, P?>,
    block: Function1<P, IReduxSagaEffect<State, R>>,
    options: TakeEffectOptions = TakeEffectOptions()
  ): ReduxSagaEffect<State, R> = TakeEveryEffect(extract, block, options)

  /** Convenience function to create [TakeEveryEffect] for a specific type of [IReduxAction] */
  @JvmStatic
  inline fun <State, reified Action, P, R> takeEveryAction(
    crossinline extract: Function1<Action, P?>,
    noinline block: Function1<P, IReduxSagaEffect<State, R>>,
    options: TakeEffectOptions = TakeEffectOptions()
  ) where Action : IReduxAction = this.takeEvery(
    { when (it) {is Action -> extract(it); else -> null } },
    block, options
  )

  /** Create a [TakeLatestEffect] instance. */
  @JvmStatic
  fun <State, P, R> takeLatest(
    extract: Function1<IReduxAction, P?>,
    block: Function1<P, IReduxSagaEffect<State, R>>,
    options: TakeEffectOptions = TakeEffectOptions()
  ): ReduxSagaEffect<State, R> = TakeLatestEffect(extract, block, options)

  /** Convenience function to create [TakeLatestEffect] for a specific type of [IReduxAction] */
  @JvmStatic
  inline fun <State, reified Action, P, R> takeLatestAction(
    crossinline extract: Function1<Action, P?>,
    noinline block: Function1<P, IReduxSagaEffect<State, R>>,
    options: TakeEffectOptions = TakeEffectOptions()
  ) where Action : IReduxAction = this.takeLatest(
    { when (it) {is Action -> extract(it); else -> null } }, block, options
  )
}
