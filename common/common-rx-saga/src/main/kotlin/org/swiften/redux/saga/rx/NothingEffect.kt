/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.saga.rx

import io.reactivex.Flowable
import org.swiften.redux.core.EmptyJob
import org.swiften.redux.saga.common.ISagaOutput
import org.swiften.redux.saga.common.SagaEffect
import org.swiften.redux.saga.common.SagaInput

/** Created by viethai.pham on 2019/02/22 */
/**
 * [SagaOutput] whose [ISagaOutput] does not emit anything. This can be used to perform clean-up
 * for streams such as [RxTakeEffect].
 * @param R The result emission type.
 */
internal class NothingEffect<R> : SagaEffect<R>() where R : Any {
  override fun invoke(p1: SagaInput): ISagaOutput<R> {
    return SagaOutput(p1.scope, p1.monitor, Flowable.empty()) { EmptyJob }
  }
}
