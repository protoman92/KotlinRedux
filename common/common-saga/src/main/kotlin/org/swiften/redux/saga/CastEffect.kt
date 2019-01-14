/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.saga

/** Created by haipham on 2019/01/05 */
/** Cast the emission from the current [IReduxSagaEffect] to [R2] if possible */
fun <State, R, R2> IReduxSagaEffect<State, R>.cast(cls: Class<R2>) =
  this.filter { cls.isInstance(it) }.map { cls.cast(it) }

/** Cast the emission from the current [IReduxSagaEffect] to [R2] if possible */
inline fun <State, R, reified R2> IReduxSagaEffect<State, R>.cast() =
  this.filter { it is R2 }.map { it as R2 }
