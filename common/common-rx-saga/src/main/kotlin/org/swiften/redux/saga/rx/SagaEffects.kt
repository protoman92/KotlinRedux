/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.saga.rx

import io.reactivex.Flowable
import io.reactivex.Single
import org.swiften.redux.core.IReduxAction
import org.swiften.redux.core.IReduxActionWithKey
import org.swiften.redux.saga.common.CommonEffects
import org.swiften.redux.saga.common.ISagaEffect
import org.swiften.redux.saga.common.ISagaEffectTransformer
import org.swiften.redux.saga.common.ITakeEffectTransformer
import org.swiften.redux.saga.common.SagaEffect
import org.swiften.redux.saga.common.TakeEffect
import kotlin.reflect.KClass

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
  fun <P, R> mapSingle(transformer: (P) -> Single<R>): ISagaEffectTransformer<P, R>
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
   * Call [CommonEffects.putInStore] with [SagaEffects.just].
   * @param P The source emission type.
   * @param value See [JustEffect.value].
   * @param actionCreator See [CommonEffects.putInStore].
   * @return A [SagaEffect] instance.
   */
  @JvmStatic
  fun <P> putInStore(value: P, actionCreator: (P) -> IReduxAction): SagaEffect<Any> where P : Any {
    return CommonEffects.putInStore(actionCreator)(this.just(value))
  }

  /**
   * Call [putInStore] with [action].
   * @param action An [IReduxAction] instance.
   * @return A [SagaEffect] instance.
   */
  @JvmStatic
  fun putInStore(action: IReduxAction): SagaEffect<Any> {
    return this.putInStore(Unit) { action }
  }

  /**
   * Create a [SelectEffect].
   * @param State The state type to select from.
   * @param R The result emission type.
   * @param cls See [SelectEffect.cls].
   * @param selector See [SelectEffect.selector].
   * @return A [SagaEffect] instance.
   */
  @JvmStatic
  fun <State, R> selectFromState(cls: Class<State>, selector: (State) -> R):
    SagaEffect<R> where R : Any {
    return SelectEffect(cls, selector)
  }

  /**
   * Similar to [selectFromState], but uses a [KClass] instead of [Class].
   * @param State The state type to select from.
   * @param R The result emission type.
   * @param cls See [SelectEffect.cls].
   * @param selector See [SelectEffect.selector].
   * @return A [SagaEffect] instance.
   */
  @JvmStatic
  fun <State, R> selectFromState(cls: KClass<State>, selector: (State) -> R):
    SagaEffect<R> where State : Any, R : Any {
    return this.selectFromState(cls.java, selector)
  }

  /**
   * Convenience method for [CommonEffects.thenNoMatterWhat] to switch to [defaultValue].
   * @param R The source emission type.
   * @param R2 The result emission type.
   * @param defaultValue A [R2] instance.
   * @return A [ISagaEffectTransformer] instance.
   */
  fun <R, R2> thenNoMatterWhat(defaultValue: R2): ISagaEffectTransformer<R, R2> where R : Any, R2 : Any {
    return CommonEffects.thenNoMatterWhat(this.just(defaultValue))
  }

  /**
   * Create a [TakeEveryEffect] instance.
   * @param Action The [IReduxAction] type to perform extraction.
   * @param P The source emission type.
   * @param R The result emission type.
   * @param cls See [TakeEffect.cls].
   * @param extractor See [TakeEffect.extractor].
   * @param creator See [TakeEffect.creator].
   * @return A [SagaEffect] instance.
   */
  @JvmStatic
  fun <Action, P, R> takeEvery(
    cls: Class<Action>,
    extractor: (Action) -> P?,
    creator: (P) -> ISagaEffect<R>
  ): TakeEffect<Action, P, R> where Action : IReduxAction, P : Any, R : Any {
    return TakeEveryEffect(cls, extractor, creator)
  }

  /**
   * Convenience function to invoke [takeEveryForKeys] using [KClass] instead of [Class].
   * @param Action The [IReduxAction] type to perform param extraction.
   * @param P The input value extracted from [IReduxAction].
   * @param R The result emission type.
   * @param cls The [Class] for [Action].
   * @param extractor Function that extracts [P] from [IReduxAction].
   * @param creator Function that creates [ISagaEffect] from [P].
   */
  @JvmStatic
  fun <Action, P, R> takeEvery(
    cls: KClass<Action>,
    extractor: (Action) -> P?,
    creator: (P) -> ISagaEffect<R>
  ): TakeEffect<Action, P, R> where Action : IReduxAction, P : Any, R : Any {
    return this.takeEvery(cls.java, extractor, creator)
  }

  /**
   * Instead of specifying the action type, check if [IReduxAction] instances that pass through
   * the pipeline conform to [IReduxActionWithKey], and whether their [IReduxActionWithKey.key]
   * values are part of the specified [actionKeys].
   *
   * This way, we can catch multiple [IReduxActionWithKey] without having to implement a manual
   * [TakeEffect.extractor] that returns [Unit].
   * @param actionKeys A [Set] of [IReduxActionWithKey.key].
   * @param creator See [TakeEffect.creator].
   * @return A [SagaEffect] instance.
   */
  @JvmStatic
  fun <R> takeEveryForKeys(
    actionKeys: Set<String>,
    creator: (IReduxActionWithKey) -> ISagaEffect<R>
  ): TakeEffect<IReduxActionWithKey, IReduxActionWithKey, R> where R : Any {
    return this.takeEvery(IReduxActionWithKey::class, { action ->
      if (actionKeys.contains(action.key)) action else null
    }, creator)
  }

  /**
   * Create a [TakeLatestEffect] instance.
   * @param Action The [IReduxAction] type to perform param extraction.
   * @param P The input value extracted from [IReduxAction].
   * @param R The result emission type.
   * @param cls The [Class] for [Action].
   * @param extractor Function that extracts [P] from [IReduxAction].
   * @param creator Function that creates [ISagaEffect] from [P].
   */
  @JvmStatic
  fun <Action, P, R> takeLatest(
    cls: Class<Action>,
    extractor: (Action) -> P?,
    creator: Function1<P, ISagaEffect<R>>
  ): TakeEffect<Action, P, R> where Action : IReduxAction, P : Any, R : Any {
    return TakeLatestEffect(cls, extractor, creator)
  }

  /**
   * Convenience function to invoke [takeLatestForKeys] using [KClass] instead of [Class].
   * @param Action The [IReduxAction] type to check for.
   * @param P The source emission type.
   * @param R The result emission type.
   * @param extractor See [TakeEffect.extractor].
   * @param creator See [TakeEffect.creator].
   * @return A [SagaEffect] instance.
   */
  @JvmStatic
  fun <Action, P, R> takeLatest(
    cls: KClass<Action>,
    extractor: (Action) -> P?,
    creator: (P) -> ISagaEffect<R>
  ): TakeEffect<Action, P, R> where Action : IReduxAction, P : Any, R : Any {
    return this.takeLatest(cls.java, extractor, creator)
  }

  /**
   * Instead of specifying the action type, check if [IReduxAction] instances that pass through
   * the pipeline conform to [IReduxActionWithKey], and whether their [IReduxActionWithKey.key]
   * values are part of the specified [actionKeys].
   *
   * This way, we can catch multiple [IReduxActionWithKey] without having to implement a manual
   * [TakeEffect.extractor] that returns [Unit].
   * @param actionKeys A [Set] of [IReduxActionWithKey.key].
   * @param creator See [TakeEffect.creator].
   * @return A [SagaEffect] instance.
   */
  @JvmStatic
  fun <R> takeLatestForKeys(
    actionKeys: Set<String>,
    creator: (IReduxActionWithKey) -> ISagaEffect<R>
  ): TakeEffect<IReduxActionWithKey, IReduxActionWithKey, R> where R : Any {
    return this.takeLatest(IReduxActionWithKey::class, { action ->
      if (actionKeys.contains(action.key)) action else null
    }, creator)
  }

  /**
   * Create a [DebounceTakeEffect] instance to perform debounce for a [TakeEffect].
   * @param Action The [IReduxAction] type to perform param extraction.
   * @param P The input value extracted from [IReduxAction].
   * @param R The result emission type.
   * @param millis See [DebounceTakeEffect.millis].
   */
  fun <Action, P, R> debounceTake(millis: Long): ITakeEffectTransformer<Action, P, R>
    where Action : IReduxAction, P : Any, R : Any {
    return { DebounceTakeEffect(it, millis) }
  }
}
