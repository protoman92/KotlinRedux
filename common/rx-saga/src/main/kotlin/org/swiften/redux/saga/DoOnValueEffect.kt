/*
 * Copyright (c) haipham 2018. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.saga

import kotlinx.coroutines.CoroutineScope

/** Created by haipham on 2019/01/06 */
/**
 * [ReduxSagaEffect] whose [ReduxSaga.Output] performs some side effects on
 * value emissions with [block].
 */
internal class DoOnValueEffect<State, R>(
  private val source: ReduxSagaEffect<State, R>,
  private val block: suspend CoroutineScope.(R) -> Unit
) : ReduxSagaEffect<State, R> {
  override fun invoke(p1: ReduxSaga.Input<State>) =
    this.source.invoke(p1).doOnValue(this.block)
}

/** Invoke a [DoOnValueEffect] on [this] */
fun <State, R> ReduxSagaEffect<State, R>.doOnValue(
  block: suspend CoroutineScope.(R) -> Unit
) = ReduxSagaHelper.doOnValue(this, block)
