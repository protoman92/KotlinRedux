/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.saga.common

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred

/** Created by haipham on 2019/01/07 */
/** Similar to [MapEffect], but handles async functions */
internal class AsyncMapEffect<P, R>(
  private val source: ISagaEffect<P>,
  private val transformer: suspend CoroutineScope.(P) -> Deferred<R>
) : SagaEffect<R>() {
  override fun invoke(p1: SagaInput) = this.source.invoke(p1).mapAsync(this.transformer)
}
