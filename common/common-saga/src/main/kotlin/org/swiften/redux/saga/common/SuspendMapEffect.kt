/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.saga.common

import kotlinx.coroutines.CoroutineScope

/** Created by haipham on 2019/01/07 */
/**
 * Similar to [MapEffect], but handles suspend functions.
 * @param P The source emission type.
 * @param R The result emission type.
 * @param source The source [ISagaEffect].
 * @param transformer Function that transforms [P] to [R].
 */
internal class SuspendMapEffect<P, R>(
  private val source: ISagaEffect<P>,
  private val transformer: suspend CoroutineScope.(P) -> R
) : SagaEffect<R>() {
  override fun invoke(p1: SagaInput) = this.source.invoke(p1).mapSuspend(this.transformer)
}
