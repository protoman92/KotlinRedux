/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.saga.common

import io.reactivex.Flowable

/** Created by haipham on 2019/01/05 */
/**
 * [SagaEffect] whose [SagaOutput] is provided via [stream].
 * @param R The result emission type.
 * @param stream A [Flowable] instance.
 */
internal class FromEffect<R>(
  private val stream: Flowable<R>
) : SagaEffect<R>() where R : Any {
  override fun invoke(p1: SagaInput): ISagaOutput<R> {
    return SagaOutput(p1.scope, p1.monitor, this.stream)
  }
}
