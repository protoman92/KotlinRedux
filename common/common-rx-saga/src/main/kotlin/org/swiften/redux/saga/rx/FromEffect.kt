/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.saga.rx

import io.reactivex.Flowable
import org.swiften.redux.saga.common.ISagaEffect
import org.swiften.redux.saga.common.SagaEffect
import org.swiften.redux.saga.common.SagaInput

/** Created by haipham on 2019/01/05 */
/**
 * [ISagaEffect] whose [SagaOutput] is provided via [stream].
 * @param R The result emission type.
 * @param stream A [Flowable] instance.
 */
internal class FromEffect<R>(
  private val stream: Flowable<R>
) : SagaEffect<R>() where R : Any {
  override fun invoke(p1: SagaInput) = SagaOutput(p1.scope, this.stream) { }
}
