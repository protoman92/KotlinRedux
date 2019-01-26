/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.saga.rx

import org.swiften.redux.core.IReduxAction
import org.swiften.redux.saga.common.ISagaEffect
import org.swiften.redux.saga.common.SagaInput
import org.swiften.redux.saga.common.SagaEffect
import org.swiften.redux.saga.common.map

/** Created by haipham on 2019/01/10 */
/** [ISagaEffect] whose [SagaOutput] simply dispatches [action] */
internal class JustPutEffect(private val action: IReduxAction) : SagaEffect<Any>() {
  override fun invoke(p1: SagaInput) = SagaEffects.just(Unit)
    .map { p1.dispatch(this@JustPutEffect.action) as Any }
    .invoke(p1)
}
