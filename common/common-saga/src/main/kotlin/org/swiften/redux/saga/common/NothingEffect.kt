/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.saga.common

import io.reactivex.Flowable

/** Created by viethai.pham on 2019/02/22 */
/**
 * [SagaOutput] whose [ISagaOutput] does not emit anything. This can be used to perform clean-up
 * for streams such as [RxTakeActionEffect].
 * @param R The result emission type.
 */
internal class NothingEffect<R> : SagaEffect<R>() where R : Any {
  override fun invoke(p1: SagaInput): ISagaOutput<R> {
    return SagaOutput(p1.context, p1.monitor, Flowable.empty())
  }
}
