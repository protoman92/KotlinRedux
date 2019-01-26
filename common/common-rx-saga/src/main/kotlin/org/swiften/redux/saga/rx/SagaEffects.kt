/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.saga.rx

import io.reactivex.Flowable
import io.reactivex.Single
import org.swiften.redux.core.IReduxAction
import org.swiften.redux.saga.common.CommonEffects
import org.swiften.redux.saga.common.ISagaEffect
import org.swiften.redux.saga.common.ISagaEffectTransformer
import org.swiften.redux.saga.common.SagaEffect

/** Created by haipham on 2019/01/13 */
/** Top-level namespace for Rx-based [ISagaEffect] */
object SagaEffects {
  /** Create a [CallEffect] */
  @JvmStatic
  fun <P, R> call(transformer: (P) -> Single<R>): ISagaEffectTransformer<P, R> =
    { CallEffect(it, transformer) }

  /** Create a [FromEffect] */
  @JvmStatic
  fun <R> from(stream: Flowable<R>): SagaEffect<R> = FromEffect(stream)

  /** Create a [JustEffect] */
  @JvmStatic
  fun <R> just(value: R): SagaEffect<R> = JustEffect(value)

  /** Call [CommonEffects.put] with [SagaEffects.just] */
  @JvmStatic
  fun <P> put(value: P, actionCreator: (P) -> IReduxAction): SagaEffect<Any> =
    CommonEffects.put(actionCreator)(this.just(value))

  /** Create a [JustPutEffect] */
  @JvmStatic
  fun justPut(action: IReduxAction): SagaEffect<Any> = JustPutEffect(action)

  /** Create a [SelectEffect] */
  @JvmStatic
  fun <State, R> select(cls: Class<State>, selector: (State) -> R): SagaEffect<R> =
    SelectEffect(cls, selector)

  inline fun <reified State, R> select(noinline selector: (State) -> R) =
    this.select(State::class.java, selector)

  /** Create a [TakeEveryEffect] instance. */
  @JvmStatic
  fun <P, R> takeEvery(
    extractor: Function1<IReduxAction, P?>,
    options: TakeEffectOptions = TakeEffectOptions(),
    creator: Function1<P, ISagaEffect<R>>
  ): SagaEffect<R> = TakeEveryEffect(extractor, options, creator)

  /** Convenience function to create [TakeEveryEffect] for a specific type of [IReduxAction] */
  @JvmStatic
  inline fun <reified Action, P, R> takeEveryAction(
    crossinline extractor: Function1<Action, P?>,
    options: TakeEffectOptions = TakeEffectOptions(),
    noinline creator: Function1<P, ISagaEffect<R>>
  ) where Action : IReduxAction = this.takeEvery(
    { when (it) { is Action -> extractor(it); else -> null } },
    options, creator
  )

  /** Create a [TakeLatestEffect] instance. */
  @JvmStatic
  fun <P, R> takeLatest(
    extractor: Function1<IReduxAction, P?>,
    options: TakeEffectOptions = TakeEffectOptions(),
    creator: Function1<P, ISagaEffect<R>>
  ): SagaEffect<R> = TakeLatestEffect(extractor, options, creator)

  /** Convenience function to create [TakeLatestEffect] for a specific type of [IReduxAction] */
  @JvmStatic
  inline fun <reified Action, P, R> takeLatestAction(
    crossinline extractor: Function1<Action, P?>,
    options: TakeEffectOptions = TakeEffectOptions(),
    noinline creator: Function1<P, ISagaEffect<R>>
  ) where Action : IReduxAction = this.takeLatest(
    { when (it) { is Action -> extractor(it); else -> null } }, options, creator
  )
}
