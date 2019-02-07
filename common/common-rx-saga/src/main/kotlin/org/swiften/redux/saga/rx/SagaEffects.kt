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
  /**
   * Create a [CallEffect].
   * @param P The source emission type.
   * @param R The result emission type.
   * @param transformer See [CallEffect.transformer].
   * @return An [ISagaEffectTransformer] instance.
   */
  @JvmStatic
  fun <P, R> call(transformer: (P) -> Single<R>): ISagaEffectTransformer<P, R>
    where P : Any, R : Any {
    return { CallEffect(it, transformer) }
  }

  /**
   * Create a [FromEffect].
   * @param R The result emission type.
   * @param stream See [FromEffect.stream].
   * @return A [SagaEffect] instance.
   */
  @JvmStatic
  fun <R> from(stream: Flowable<R>): SagaEffect<R> where R : Any = FromEffect(stream)

  /**
   * Create a [JustEffect].
   * @param R The result emission type.
   * @param value See [JustEffect.value].
   * @return A [SagaEffect] instance.
   */
  @JvmStatic
  fun <R> just(value: R): SagaEffect<R> where R : Any = JustEffect(value)

  /**
   * Call [CommonEffects.put] with [SagaEffects.just].
   * @param P The source emission type.
   * @param value See [JustEffect.value].
   * @param actionCreator See [CommonEffects.put].
   * @return A [SagaEffect] instance.
   */
  @JvmStatic
  fun <P> put(value: P, actionCreator: (P) -> IReduxAction): SagaEffect<Any> where P : Any {
    return CommonEffects.put(actionCreator)(this.just(value))
  }

  /**
   * Create a [JustPutEffect].
   * @param action See [JustPutEffect.action].
   * @return A [SagaEffect] instance.
   */
  @JvmStatic
  fun justPut(action: IReduxAction): SagaEffect<Any> = JustPutEffect(action)

  /**
   * Create a [SelectEffect].
   * @param State The state type to select from.
   * @param R The result emission type.
   * @param cls See [SelectEffect.cls].
   * @param selector See [SelectEffect.selector].
   * @return A [SagaEffect] instance.
   */
  @JvmStatic
  fun <State, R> select(cls: Class<State>, selector: (State) -> R): SagaEffect<R> where R : Any {
    return SelectEffect(cls, selector)
  }

  /**
   * Create a [SelectEffect].
   * @param State The state type to select from.
   * @param R The result emission type.
   * @param selector See [SelectEffect.selector].
   * @return A [SagaEffect] instance.
   */
  inline fun <reified State, R> select(noinline selector: (State) -> R):
    SagaEffect<R> where R : Any {
    return this.select(State::class.java, selector)
  }

  /**
   * Create a [TakeEveryEffect] instance.
   * @param P The source emission type.
   * @param R The result emission type.
   * @param extractor See [TakeEffect.extractor].
   * @param options See [TakeEffect.options].
   * @param creator See [TakeEffect.creator].
   * @return A [SagaEffect] instance.
   */
  @JvmStatic
  fun <P, R> takeEvery(
    extractor: (IReduxAction) -> P?,
    options: TakeEffectOptions = TakeEffectOptions(),
    creator: (P) -> ISagaEffect<R>
  ): SagaEffect<R> where P : Any, R : Any {
    return TakeEveryEffect(extractor, options, creator)
  }

  /**
   * Convenience function to create [TakeEveryEffect] for a specific type of [IReduxAction].
   * @param Action The [IReduxAction] type to check for.
   * @param P The source emission type.
   * @param R The result emission type.
   * @param extractor See [TakeEffect.extractor].
   * @param options See [TakeEffect.options].
   * @param creator See [TakeEffect.creator].
   * @return A [SagaEffect] instance.
   */
  @JvmStatic
  inline fun <reified Action, P, R> takeEveryAction(
    crossinline extractor: Function1<Action, P?>,
    options: TakeEffectOptions = TakeEffectOptions(),
    noinline creator: Function1<P, ISagaEffect<R>>
  ): SagaEffect<R> where Action : IReduxAction, P : Any, R : Any {
    return this.takeEvery(
      { when (it) { is Action -> extractor(it); else -> null } },
      options, creator
    )
  }

  /**
   * Create a [TakeLatestEffect] instance.
   * @param P The source emission type.
   * @param R The result emission type.
   * @param extractor See [TakeEffect.extractor].
   * @param options See [TakeEffect.options].
   * @param creator See [TakeEffect.creator].
   * @return A [SagaEffect] instance.
   */
  @JvmStatic
  fun <P, R> takeLatest(
    extractor: Function1<IReduxAction, P?>,
    options: TakeEffectOptions = TakeEffectOptions(),
    creator: Function1<P, ISagaEffect<R>>
  ): SagaEffect<R> where P : Any, R : Any {
    return TakeLatestEffect(extractor, options, creator)
  }

  /**
   * Convenience function to create [TakeLatestEffect] for a specific type of [IReduxAction].
   * @param Action The [IReduxAction] type to check for.
   * @param P The source emission type.
   * @param R The result emission type.
   * @param extractor See [TakeEffect.extractor].
   * @param options See [TakeEffect.options].
   * @param creator See [TakeEffect.creator].
   * @return A [SagaEffect] instance.
   */
  @JvmStatic
  inline fun <reified Action, P, R> takeLatestAction(
    crossinline extractor: Function1<Action, P?>,
    options: TakeEffectOptions = TakeEffectOptions(),
    noinline creator: Function1<P, ISagaEffect<R>>
  ): SagaEffect<R> where Action : IReduxAction, P : Any, R : Any {
    return this.takeLatest(
      { when (it) { is Action -> extractor(it); else -> null } }, options, creator
    )
  }
}
