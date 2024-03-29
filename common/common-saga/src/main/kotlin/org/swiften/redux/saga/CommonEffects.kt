/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.saga

import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Single
import org.swiften.redux.core.IReduxAction
import kotlin.reflect.KClass

/** Created by haipham on 2019/01/13 */
/** Top-level namespace for Rx-based [ISagaEffect] */
object CommonEffects {
  /**
   * Create an [AwaitEffect] instance.
   * @param R The result emission type.
   * @param creator See [AwaitEffect.creator].
   * @return A [SingleEffect] instance.
   */
  @JvmStatic
  fun <R> await(creator: IAwaitCreator<R>): AwaitEffect<R> where R : Any {
    return AwaitEffect(creator)
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
   * Create a [DelayEffect].
   * @param millis See [DelayEffect.millis].
   * @return A [SagaEffect] instance.
   */
  @JvmStatic
  fun delay(millis: Long): DelayEffect {
    return DelayEffect(millis)
  }

  /**
   * Create a [NothingEffect] instance.
   * @param R The result emission type.
   * @return A [SagaEffect] instance.
   */
  @JvmStatic
  fun <R> doNothing(): SagaEffect<R> where R : Any = NothingEffect()

  /**
   * Create a [FlatMapEffect] instance with [FlatMapEffect.Mode.EVERY].
   * @param P The source emission type.
   * @param R The result emission type.
   * @param transformer See [FlatMapEffect.transformer].
   * @return An [ISagaEffectTransformer] instance.
   */
  @JvmStatic
  fun <P, R> flatMap(transformer: (P) -> SagaEffect<R>): ISagaEffectTransformer<P, R> where P : Any, R : Any {
    return { FlatMapEffect(it, FlatMapEffect.Mode.EVERY, transformer) }
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
   * Create a [FromEffect].
   * @param R The result emission type.
   * @param stream See [FromEffect.stream].
   * @return A [SagaEffect] instance.
   */
  fun <R> from(stream: Single<R>): SagaEffect<R> where R : Any = this.from(stream.toFlowable())

  /**
   * Create a [FromEffect].
   * @param R The result emission type.
   * @param stream See [FromEffect.stream].
   * @return A [SagaEffect] instance.
   */
  fun <R> from(stream: Maybe<R>): SagaEffect<R> where R : Any = this.from(stream.toFlowable())

  /**
   * Create an [AllEffect] instance.
   * @param R The result emission type.
   * @param sources See [AllEffect.sources].
   * @return An [AllEffect] instance.
   */
  @JvmStatic
  fun <R> mergeAll(sources: Collection<SagaEffect<R>>): SagaEffect<R> where R : Any {
    return AllEffect(sources)
  }

  /**
   * Create an [AllEffect] instance.
   * @param R The result emission type.
   * @param sources See [AllEffect.sources].
   * @return An [AllEffect] instance.
   */
  @JvmStatic
  fun <R> mergeAll(vararg sources: SagaEffect<R>): SagaEffect<R> where R : Any {
    return mergeAll(sources.asList())
  }

  /**
   * Create a [PutEffect].
   * @param action See [PutEffect.action].
   * @return A [PutEffect] instance.
   */
  @JvmStatic
  fun put(action: IReduxAction): PutEffect {
    return PutEffect(action)
  }

  /**
   * Create a [SelectEffect].
   * @param State The state type to select from.
   * @param R The result emission type.
   * @param cls See [SelectEffect.cls].
   * @param selector See [SelectEffect.selector].
   * @return A [SelectEffect] instance.
   */
  @JvmStatic
  fun <State, R> select(cls: Class<State>, selector: (State) -> R): SelectEffect<State, R> where State : Any, R : Any {
    return SelectEffect(cls, selector)
  }

  /**
   * Similar to [select], but uses a [KClass] instead of [Class].
   * @param State The state type to select from.
   * @param R The result emission type.
   * @param cls See [SelectEffect.cls].
   * @param selector See [SelectEffect.selector].
   * @return A [SelectEffect] instance.
   */
  @JvmStatic
  fun <State, R> select(cls: KClass<State>, selector: (State) -> R): SelectEffect<State, R> where State : Any, R : Any {
    return select(cls.java, selector)
  }

  /**
   * Create a [FlatMapEffect] instance with [FlatMapEffect.Mode.LATEST].
   * @param P The source emission type.
   * @param R The result emission type.
   * @param transformer See [FlatMapEffect.transformer].
   * @return An [ISagaEffectTransformer] instance.
   */
  @JvmStatic
  fun <P, R> switchMap(transformer: (P) -> SagaEffect<R>): ISagaEffectTransformer<P, R> where P : Any, R : Any {
    return { FlatMapEffect(it, FlatMapEffect.Mode.LATEST, transformer) }
  }

  /**
   * Create a [TakeStateEffect] instance.
   * @param cls See [TakeStateEffect.cls].
   * @return A [TakeStateEffect] instance.
   */
  @JvmStatic
  fun <State> takeState(cls: Class<State>): TakeStateEffect<State> where State : Any {
    return TakeStateEffect(cls)
  }

  /**
   * Create a [TakeStateEffect] instance.
   * @param cls See [TakeStateEffect.cls].
   * @return A [TakeStateEffect] instance.
   */
  @JvmStatic
  fun <State> takeState(cls: KClass<State>): TakeStateEffect<State> where State : Any {
    return this.takeState(cls.java)
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
