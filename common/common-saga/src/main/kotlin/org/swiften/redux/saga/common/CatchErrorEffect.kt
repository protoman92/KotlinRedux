/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.saga.common

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred

/** Created by haipham on 2018/12/26 */
/** [ISagaEffect] whose [ISagaOutput] catches [Throwable] from upstream */
internal class CatchErrorEffect<R>(
  private val source: ISagaEffect<R>,
  private val catcher: (Throwable) -> R
) : SagaEffect<R>() {
  override fun invoke(p1: SagaInput) = this.source.invoke(p1).catchError(this.catcher)
}

/** Similar to [CatchErrorEffect], but handles suspending [catcher] */
internal class SuspendCatchErrorEffect<R>(
  private val source: ISagaEffect<R>,
  private val catcher: suspend CoroutineScope.(Throwable) -> R
) : SagaEffect<R>() {
  override fun invoke(p1: SagaInput) = this.source.invoke(p1).catchSuspend(this.catcher)
}

/** Similar to [CatchErrorEffect], but handles async [catcher] */
internal class AsyncCatchErrorEffect<R>(
  private val source: ISagaEffect<R>,
  private val catcher: suspend CoroutineScope.(Throwable) -> Deferred<R>
) : SagaEffect<R>() {
  override fun invoke(p1: SagaInput) = this.source.invoke(p1).catchAsync(this.catcher)
}
