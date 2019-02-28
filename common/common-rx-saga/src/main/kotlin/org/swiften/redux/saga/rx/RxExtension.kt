/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.saga.rx

import io.reactivex.Single
import org.swiften.redux.core.IReduxAction
import org.swiften.redux.saga.common.SagaEffect
import org.swiften.redux.saga.common.TakeActionEffect
import org.swiften.redux.saga.common.thenSwitchTo
import kotlin.reflect.KClass

/** Created by haipham on 2019/01/26 */
/**
 * Invoke a [CallEffect] on [this].
 * @receiver See [CallEffect.source].
 * @param P The source emission type.
 * @param R The result emission type.
 * @param transformer See [CallEffect.transformer].
 * @return A [SagaEffect] instance.
 */
fun <P, R> SagaEffect<P>.mapSingle(transformer: (P) -> Single<R>):
  SagaEffect<R> where P : Any, R : Any {
  return this.transform(SagaEffects.mapSingle(transformer))
}

/**
 * Invoke a [SelectEffect] on [this] and combine the emitted values with [combiner].
 * @receiver A [SagaEffect] instance.
 * @param State The state type to select from.
 * @param R The source emission type.
 * @param R2 The value type to be selected from [State].
 * @param R3 The result emission type.
 * @param cls See [SelectEffect.cls].
 * @param selector See [SelectEffect.selector].
 * @param combiner Function that combines [R] and [R2] to produce [R3].
 * @return A [SagaEffect] instance.
 */
fun <State, R, R2, R3> SagaEffect<R>.selectFromState(
  cls: Class<State>,
  selector: (State) -> R2,
  combiner: (R, R2) -> R3
): SagaEffect<R3> where R : Any, R2 : Any, R3 : Any {
  return this.thenSwitchTo(SagaEffects.selectFromState(cls, selector), combiner)
}

/**
 * Similar to [SagaEffect.selectFromState], but uses [KClass] instead of [Class].
 * @receiver A [SagaEffect] instance.
 * @param State The state type to select from.
 * @param R The source emission type.
 * @param R2 The value type to be selected from [State].
 * @param R3 The result emission type.
 * @param cls See [SelectEffect.cls].
 * @param selector See [SelectEffect.selector].
 * @param combiner Function that combines [R] and [R2] to produce [R3].
 * @return A [SagaEffect] instance.
 */
fun <State, R, R2, R3> SagaEffect<R>.selectFromState(
  cls: KClass<State>,
  selector: (State) -> R2,
  combiner: (R, R2) -> R3
): SagaEffect<R3> where State : Any, R : Any, R2 : Any, R3 : Any {
  return this.selectFromState(cls.java, selector, combiner)
}

/**
 * Invoke a [SelectEffect] but ignore emissions from [this].
 * @receiver A [SagaEffect] instance.
 * @param State The state type to select from.
 * @param R2 The result emission type.
 * @param cls See [SelectEffect.cls].
 * @param selector See [SelectEffect.selector].
 * @return A [SagaEffect] instance.
 */
fun <State, R2> SagaEffect<*>.selectFromState(cls: Class<State>, selector: (State) -> R2):
  SagaEffect<R2> where R2 : Any {
  return this.thenSwitchTo(SagaEffects.selectFromState(cls, selector))
}

/**
 * Similar to [SagaEffect.selectFromState], but uses [KClass] instead of [Class].
 * @receiver A [SagaEffect] instance.
 * @param State The state type to select from.
 * @param R2 The result emission type.
 * @param cls See [SelectEffect.cls].
 * @param selector See [SelectEffect.selector].
 * @return A [SagaEffect] instance.
 */
fun <State, R2> SagaEffect<*>.selectFromState(cls: KClass<State>, selector: (State) -> R2):
  SagaEffect<R2> where State : Any, R2 : Any {
  return this.selectFromState(cls.java, selector)
}

/**
 * Invoke a [DebounceTakeEffect] on [this].
 * @receiver See [DebounceTakeEffect.source].
 * @param Action The [IReduxAction] type to perform param extraction.
 * @param P The input value extracted from [IReduxAction].
 * @param R The result emission type.
 * @param millis See [DebounceTakeEffect.millis].
 */
fun <Action, P, R> TakeActionEffect<Action, P, R>.debounceTake(millis: Long): TakeActionEffect<Action, P, R>
  where Action : IReduxAction, P : Any, R : Any {
  return SagaEffects.debounceTake<Action, P, R>(millis)(this)
}
