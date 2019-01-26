/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.saga.rx

import io.reactivex.Single
import org.swiften.redux.saga.common.SagaEffect
import org.swiften.redux.saga.common.then

/** Created by haipham on 2019/01/26 */
/** Invoke a [CallEffect] on [this] */
fun <P, R> SagaEffect<P>.call(transformer: (P) -> Single<R>) =
  this.transform(SagaEffects.call(transformer))

/** Invoke a [SelectEffect] on [this] and combine the emitted values with [combiner] */
inline fun <reified State, R, R2, R3> SagaEffect<R>.select(
  noinline selector: (State) -> R2,
  noinline combiner: (R, R2) -> R3
) = this.then(SagaEffects.select(selector), combiner)

/** Invoke a [SelectEffect] but ignore emissions from [this] */
inline fun <reified State, R2> SagaEffect<*>.select(noinline selector: (State) -> R2) =
  this.then(SagaEffects.select(selector))
