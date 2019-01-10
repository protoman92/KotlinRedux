/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.saga.rx

import org.swiften.redux.core.IReduxAction
import org.swiften.redux.saga.Input
import org.swiften.redux.saga.ReduxSagaEffect
import org.swiften.redux.saga.map
import org.swiften.redux.saga.rx.ReduxSagaEffects.just

/** Created by haipham on 2019/01/10 */
/** [ReduxSagaEffect] whose [ReduxSagaOutput] simply dispatches [action] */
internal class JustPutEffect<State>(
  private val action: IReduxAction
) : ReduxSagaEffect<State, Any> {
  override fun invoke(p1: Input<State>) = just<State, Unit>(Unit)
    .map { p1.dispatch(this@JustPutEffect.action) as Any }
    .invoke(p1)
}
