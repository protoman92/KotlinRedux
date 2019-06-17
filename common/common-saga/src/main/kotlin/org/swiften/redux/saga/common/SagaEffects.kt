/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.saga.common

import io.reactivex.Flowable
import io.reactivex.Single
import org.swiften.redux.core.IReduxAction
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
   * Create a [DebounceEffect] instance to perform debounce for a [SagaEffect].
   * @param R The result emission type.
   * @param millis See [DebounceEffect.millis].
   * @return An [ISagaEffectTransformer] instance.
   */
  @JvmStatic
  fun <R> debounce(millis: Long): ISagaEffectTransformer<R, R> where R : Any {
    return { DebounceEffect(it, millis) }
  }

  /**
   * Create a [TakeActionEffect] instance.
   * @param Action The [IReduxAction] type to perform param extraction.
   * @param R The result emission type.
   * @param cls See [TakeActionEffect.cls].
   * @param extractor See [TakeActionEffect.extractor].
   * @return A [SagaEffect] instance.
   */
  @JvmStatic
  fun <Action, R> takeAction(cls: Class<Action>, extractor: (Action) -> R?): SagaEffect<R>
    where Action : IReduxAction, R : Any {
    return TakeActionEffect(cls, extractor)
  }

  /**
   * Create a [TakeActionEffect] instance.
   * @param Action The [IReduxAction] type to perform param extraction.
   * @param R The result emission type.
   * @param cls See [TakeActionEffect.cls].
   * @param extractor See [TakeActionEffect.extractor].
   * @return A [SagaEffect] instance.
   */
  @JvmStatic
  fun <Action, R> takeAction(cls: KClass<Action>, extractor: (Action) -> R?): SagaEffect<R>
    where Action : IReduxAction, R : Any {
    return this.takeAction(cls.java, extractor)
  }
}
