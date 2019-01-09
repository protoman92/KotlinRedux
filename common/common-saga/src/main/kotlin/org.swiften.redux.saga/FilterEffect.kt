/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.saga

/** Created by haipham on 2019/01/05 */
/** [ReduxSagaEffect] whose [CommonSaga.IOutput] filters emissions with [selector] */
internal class FilterEffect<State, R>(
  private val source: ReduxSagaEffect<State, R>,
  private val selector: (R) -> Boolean
) : ReduxSagaEffect<State, R> {
  override fun invoke(p1: CommonSaga.Input<State>) =
    this.source.invoke(p1).filter(this.selector)
}

/** Invoke a [FilterEffect] on the current [ReduxSagaEffect] */
fun <State, R> ReduxSagaEffect<State, R>.filter(selector: (R) -> Boolean) =
  CommonSagaHelper.filter(this, selector)
