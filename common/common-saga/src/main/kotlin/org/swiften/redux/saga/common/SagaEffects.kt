/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.saga.common

import io.reactivex.Flowable
import io.reactivex.Single
import org.swiften.redux.core.IReduxAction
import org.swiften.redux.core.IReduxActionWithKey
import kotlin.reflect.KClass

/** Created by haipham on 2019/01/13 */
/** Top-level namespace for Rx-based [ISagaEffect] */
object SagaEffects {
  /**
   * Create an [AwaitEffect] instance.
   * @param R The result emission type.
   * @param creator See [AwaitEffect.creator].
   * @return A [SingleSagaEffect] instance.
   */
  fun <R> await(creator: IAwaitCreator<R>): SingleSagaEffect<R> where R : Any {
    return AwaitEffect(creator)
  }

  /**
   * Create a [NothingEffect] instance.
   * @param R The result emission type.
   * @return A [SagaEffect] instance.
   */
  fun <R> doNothing(): SagaEffect<R> where R : Any = NothingEffect()

  /**
   * Create a [CallEffect] instance.
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
  fun <R> from(stream: Flowable<R>): SagaEffect<R> where R : Any =
    FromEffect(stream)

  /**
   * Create a [JustEffect].
   * @param R The result emission type.
   * @param value See [JustEffect.value].
   * @return A [SingleSagaEffect] instance.
   */
  @JvmStatic
  fun <R> just(value: R): SingleSagaEffect<R> where R : Any =
    JustEffect(value)

  /**
   * Create an [AllEffect] instance.
   * @param R The result emission type.
   * @param sources See [AllEffect.sources].
   * @return An [AllEffect] instance.
   */
  fun <R> mergeAll(sources: Collection<SagaEffect<R>>): SagaEffect<R> where R : Any {
    return AllEffect(sources)
  }

  /**
   * Create an [AllEffect] instance.
   * @param R The result emission type.
   * @param sources See [AllEffect.sources].
   * @return An [AllEffect] instance.
   */
  fun <R> mergeAll(vararg sources: SagaEffect<R>): SagaEffect<R> where R : Any {
    return mergeAll(sources.asList())
  }

  /**
   * Call [CommonEffects.putInStore] with [SagaEffects.just].
   * @param P The source emission type.
   * @param value See [JustEffect.value].
   * @param actionCreator See [CommonEffects.putInStore].
   * @return A [PutEffect] instance.
   */
  @JvmStatic
  fun <P> putInStore(value: P, actionCreator: (P) -> IReduxAction): PutEffect<P> where P : Any {
    return CommonEffects.putInStore(actionCreator)(
      just(
        value
      )
    )
  }

  /**
   * Call [putInStore] with [action].
   * @param action An [IReduxAction] instance.
   * @return A [PutEffect] instance.
   */
  @JvmStatic
  fun putInStore(action: IReduxAction): PutEffect<Unit> =
    putInStore(Unit) { action }

  /**
   * Create a [SelectEffect].
   * @param State The state type to select from.
   * @param R The result emission type.
   * @param cls See [SelectEffect.cls].
   * @param selector See [SelectEffect.selector].
   * @return A [SelectEffect] instance.
   */
  @JvmStatic
  fun <State, R> selectFromState(cls: Class<State>, selector: (State) -> R): SelectEffect<State, R> where R : Any {
    return SelectEffect(cls, selector)
  }

  /**
   * Similar to [selectFromState], but uses a [KClass] instead of [Class].
   * @param State The state type to select from.
   * @param R The result emission type.
   * @param cls See [SelectEffect.cls].
   * @param selector See [SelectEffect.selector].
   * @return A [SelectEffect] instance.
   */
  @JvmStatic
  fun <State, R> selectFromState(cls: KClass<State>, selector: (State) -> R): SelectEffect<State, R> where State : Any, R : Any {
    return selectFromState(cls.java, selector)
  }

  /**
   * Convenience method for [CommonEffects.thenNoMatterWhat] to switch to [defaultValue].
   * @param R The source emission type.
   * @param R2 The result emission type.
   * @param defaultValue A [R2] instance.
   * @return A [ISagaEffectTransformer] instance.
   */
  fun <R, R2> thenNoMatterWhat(defaultValue: R2): ISagaEffectTransformer<R, R2> where R : Any, R2 : Any {
    return CommonEffects.thenNoMatterWhat(
      just(
        defaultValue
      )
    )
  }

  /**
   * Create a [TakeEveryActionEffect] instance.
   * @param Action The [IReduxAction] type to perform extraction.
   * @param P The source emission type.
   * @param R The result emission type.
   * @param cls See [TakeActionEffect.cls].
   * @param extractor See [TakeActionEffect.extractor].
   * @param creator See [TakeActionEffect.creator].
   * @return A [SagaEffect] instance.
   */
  @JvmStatic
  fun <Action, P, R> takeEveryAction(
    cls: Class<Action>,
    extractor: (Action) -> P?,
    creator: (P) -> ISagaEffect<R>
  ): TakeActionEffect<Action, P, R> where Action : IReduxAction, P : Any, R : Any {
    return TakeEveryActionEffect(cls, extractor, creator)
  }

  /**
   * Convenience function to invoke [takeEveryActionForKeys] using [KClass] instead of [Class].
   * @param Action The [IReduxAction] type to perform param extraction.
   * @param P The input value extracted from [IReduxAction].
   * @param R The result emission type.
   * @param cls The [Class] for [Action].
   * @param extractor Function that extracts [P] from [IReduxAction].
   * @param creator Function that creates [ISagaEffect] from [P].
   */
  @JvmStatic
  fun <Action, P, R> takeEveryAction(
    cls: KClass<Action>,
    extractor: (Action) -> P?,
    creator: (P) -> ISagaEffect<R>
  ): TakeActionEffect<Action, P, R> where Action : IReduxAction, P : Any, R : Any {
    return takeEveryAction(cls.java, extractor, creator)
  }

  /**
   * Instead of specifying the action type, check if [IReduxAction] instances that pass through
   * the pipeline conform to [IReduxActionWithKey], and whether their [IReduxActionWithKey.key]
   * values are part of the specified [actionKeys].
   *
   * This way, we can catch multiple [IReduxActionWithKey] without having to implement a manual
   * [TakeActionEffect.extractor] that returns [Unit].
   * @param actionKeys A [Set] of [IReduxActionWithKey.key].
   * @param creator See [TakeActionEffect.creator].
   * @return A [SagaEffect] instance.
   */
  @JvmStatic
  fun <R> takeEveryActionForKeys(
    actionKeys: Set<String>,
    creator: (IReduxActionWithKey) -> ISagaEffect<R>
  ): TakeActionEffect<IReduxActionWithKey, IReduxActionWithKey, R> where R : Any {
    return takeEveryAction(
      IReduxActionWithKey::class,
      { action ->
        if (actionKeys.contains(action.key)) action else null
      },
      creator
    )
  }

  /**
   * Create a [TakeLatestActionEffect] instance.
   * @param Action The [IReduxAction] type to perform param extraction.
   * @param P The input value extracted from [IReduxAction].
   * @param R The result emission type.
   * @param cls The [Class] for [Action].
   * @param extractor Function that extracts [P] from [IReduxAction].
   * @param creator Function that creates [ISagaEffect] from [P].
   */
  @JvmStatic
  fun <Action, P, R> takeLatestAction(
    cls: Class<Action>,
    extractor: (Action) -> P?,
    creator: Function1<P, ISagaEffect<R>>
  ): TakeActionEffect<Action, P, R> where Action : IReduxAction, P : Any, R : Any {
    return TakeLatestActionEffect(cls, extractor, creator)
  }

  /**
   * Convenience function to invoke [takeLatestActionForKeys] using [KClass] instead of [Class].
   * @param Action The [IReduxAction] type to check for.
   * @param P The source emission type.
   * @param R The result emission type.
   * @param extractor See [TakeActionEffect.extractor].
   * @param creator See [TakeActionEffect.creator].
   * @return A [SagaEffect] instance.
   */
  @JvmStatic
  fun <Action, P, R> takeLatestAction(
    cls: KClass<Action>,
    extractor: (Action) -> P?,
    creator: (P) -> ISagaEffect<R>
  ): TakeActionEffect<Action, P, R> where Action : IReduxAction, P : Any, R : Any {
    return takeLatestAction(cls.java, extractor, creator)
  }

  /**
   * Instead of specifying the action type, check if [IReduxAction] instances that pass through
   * the pipeline conform to [IReduxActionWithKey], and whether their [IReduxActionWithKey.key]
   * values are part of the specified [actionKeys].
   *
   * This way, we can catch multiple [IReduxActionWithKey] without having to implement a manual
   * [TakeActionEffect.extractor] that returns [Unit].
   * @param actionKeys A [Set] of [IReduxActionWithKey.key].
   * @param creator See [TakeActionEffect.creator].
   * @return A [SagaEffect] instance.
   */
  @JvmStatic
  fun <R> takeLatestActionForKeys(
    actionKeys: Set<String>,
    creator: (IReduxActionWithKey) -> ISagaEffect<R>
  ): TakeActionEffect<IReduxActionWithKey, IReduxActionWithKey, R> where R : Any {
    return takeLatestAction(
      IReduxActionWithKey::class,
      { action ->
        if (actionKeys.contains(action.key)) action else null
      },
      creator
    )
  }

  /**
   * Create a [TakeEveryStateEffect] instance.
   * @param cls See [TakeStateEffect.cls].
   * @param creator See [TakeStateEffect.creator].
   * @return A [TakeStateEffect] instance.
   */
  @JvmStatic
  fun <State, R> takeEveryState(
    cls: Class<State>,
    creator: (State) -> ISagaEffect<R>
  ): TakeStateEffect<State, R> where State : Any, R : Any {
    return TakeEveryStateEffect(cls, creator)
  }

  /**
   * Same as [takeEveryState], but use [KClass] instead of [Class].
   * @param cls See [TakeStateEffect.cls].
   * @param creator See [TakeStateEffect.creator].
   * @return A [TakeStateEffect] instance.
   */
  @JvmStatic
  fun <State, R> takeEveryState(
    cls: KClass<State>,
    creator: (State) -> ISagaEffect<R>
  ): TakeStateEffect<State, R> where State : Any, R : Any {
    return takeEveryState(cls.java, creator)
  }

  /**
   * Create a [TakeLatestStateEffect] instance.
   * @param cls See [TakeEveryActionEffect.cls].
   * @param creator See [TakeEveryActionEffect.creator].
   * @return A [TakeStateEffect] instance.
   */
  @JvmStatic
  fun <State, R> takeLatestState(
    cls: Class<State>,
    creator: (State) -> ISagaEffect<R>
  ): TakeStateEffect<State, R> where State : Any, R : Any {
    return TakeLatestStateEffect(cls, creator)
  }

  /**
   * Same as [takeLatestState], but use [KClass] instead of [Class].
   * @param cls See [TakeStateEffect.cls].
   * @param creator See [TakeStateEffect.creator].
   * @return A [TakeStateEffect] instance.
   */
  @JvmStatic
  fun <State, R> takeLatestState(
    cls: KClass<State>,
    creator: (State) -> ISagaEffect<R>
  ): TakeStateEffect<State, R> where State : Any, R : Any {
    return takeLatestState(cls.java, creator)
  }

  /**
   * Create a [DebounceTakeEffect] instance to perform debounce for a [TakeActionEffect].
   * @param Action The [IReduxAction] type to perform param extraction.
   * @param P The input value extracted from [IReduxAction].
   * @param R The result emission type.
   * @param millis See [DebounceTakeEffect.millis].
   */
  @JvmStatic
  fun <Action, P, R> debounceTake(millis: Long): ITakeEffectTransformer<Action, P, R>
    where Action : IReduxAction, P : Any, R : Any {
    return { DebounceTakeEffect(it, millis) }
  }
}
