/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.saga

import kotlinx.coroutines.CoroutineScope

/** Created by haipham on 2019/01/05 */
/**
 * [ReduxSagaEffect] whose [ReduxSaga.Output] filters emissions with
 * [selector]
 */
internal class FilterEffect<State, R>(
  private val source: ReduxSagaEffect<State, R>,
  private val selector: suspend CoroutineScope.(R) -> Boolean
) : ReduxSagaEffect<State, R> {
  override fun invoke(p1: ReduxSaga.Input<State>) =
    this.source.invoke(p1).filter(this.selector)
}

/** Invoke a [FilterEffect] on the current [ReduxSagaEffect] */
fun <State, R> ReduxSagaEffect<State, R>.filter(
  selector: suspend CoroutineScope.(R) -> Boolean
) = ReduxSagaHelper.filter(this, selector)