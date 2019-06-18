/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.saga.common

import io.reactivex.processors.PublishProcessor
import org.swiften.redux.core.EmptyJob

/** Created by viethai.pham on 2019/02/28 */
/**
 * [TakeStateEffect] instances produces streams that emits [State].
 * @param State The [State] type to be emitted.
 * @param cls The [Class] of [State].
 */
class TakeStateEffect<State>(private val cls: Class<State>) : SagaEffect<State>() where State : Any {
  override fun invoke(p1: SagaInput): ISagaOutput<State> {
    val subject = PublishProcessor.create<State>().toSerialized()

    return SagaOutput(p1.monitor, subject.onBackpressureBuffer().observeOn(p1.scheduler)) {
      /**
       * By the time [ISagaOutput.onAction] is called, the store would have reduced a new [State]
       * so [SagaInput.lastState] here will produce the latest [State].
       */
      subject.onNext(this@TakeStateEffect.cls.cast(p1.lastState())); EmptyJob
    }
  }
}
