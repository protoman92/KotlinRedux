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
import org.swiften.redux.saga.IReduxSagaEffectTransformer
import org.swiften.redux.saga.ReduxSagaEffect

/** Created by haipham on 2019/01/13 */
/** Top-level namespace for Rx-based [IReduxSagaEffect] */
object ReduxSagaEffects {
  /** Create a [CallEffect] */
  @JvmStatic
  fun <P, R> call(transformer: (P) -> Single<R>): IReduxSagaEffectTransformer<P, R> =
    { CallEffect(it, transformer) }

  /** Create a [FromEffect] */
  @JvmStatic
  fun <R> from(stream: Flowable<R>): ReduxSagaEffect<R> = FromEffect(stream)

  /** Create a [JustEffect] */
  @JvmStatic
  fun <R> just(value: R): ReduxSagaEffect<R> = JustEffect(value)

  /** Call [CommonSagaEffects.put] with [ReduxSagaEffects.just] */
  @JvmStatic
  fun <P> put(value: P, actionCreator: (P) -> IReduxAction): ReduxSagaEffect<Any> =
    CommonSagaEffects.put(actionCreator)(this.just(value))

  /** Create a [JustPutEffect] */
  @JvmStatic
  fun justPut(action: IReduxAction): ReduxSagaEffect<Any> = JustPutEffect(action)

  /** Create a [SelectEffect] */
  @JvmStatic
  fun <State, R> select(cls: Class<State>, selector: (State) -> R): ReduxSagaEffect<R> =
    SelectEffect(cls, selector)

  inline fun <reified State, R> select(noinline selector: (State) -> R) =
    this.select(State::class.java, selector)

  /** Create a [TakeEveryEffect] instance. */
  @JvmStatic
  fun <P, R> takeEvery(
    extractor: Function1<IReduxAction, P?>,
    options: TakeEffectOptions = TakeEffectOptions(),
    creator: Function1<P, IReduxSagaEffect<R>>
  ): ReduxSagaEffect<R> = TakeEveryEffect(extractor, options, creator)

  /** Convenience function to create [TakeEveryEffect] for a specific type of [IReduxAction] */
  @JvmStatic
  inline fun <reified Action, P, R> takeEveryAction(
    crossinline extractor: Function1<Action, P?>,
    options: TakeEffectOptions = TakeEffectOptions(),
    noinline creator: Function1<P, IReduxSagaEffect<R>>
  ) where Action : IReduxAction = this.takeEvery(
    { when (it) { is Action -> extractor(it); else -> null } },
    options, creator
  )

  /** Create a [TakeLatestEffect] instance. */
  @JvmStatic
  fun <P, R> takeLatest(
    extractor: Function1<IReduxAction, P?>,
    options: TakeEffectOptions = TakeEffectOptions(),
    creator: Function1<P, IReduxSagaEffect<R>>
  ): ReduxSagaEffect<R> = TakeLatestEffect(extractor, options, creator)

  /** Convenience function to create [TakeLatestEffect] for a specific type of [IReduxAction] */
  @JvmStatic
  inline fun <reified Action, P, R> takeLatestAction(
    crossinline extractor: Function1<Action, P?>,
    options: TakeEffectOptions = TakeEffectOptions(),
    noinline creator: Function1<P, IReduxSagaEffect<R>>
  ) where Action : IReduxAction = this.takeLatest(
    { when (it) { is Action -> extractor(it); else -> null } }, options, creator
  )
}
