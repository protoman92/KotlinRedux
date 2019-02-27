/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.saga.rx

import io.reactivex.processors.BehaviorProcessor
import org.swiften.redux.core.EmptyJob
import org.swiften.redux.saga.common.ISagaEffect
import org.swiften.redux.saga.common.ISagaOutput
import org.swiften.redux.saga.common.SagaInput
import org.swiften.redux.saga.common.TakeStateEffect

/** Created by viethai.pham on 28/2/19 */
/**
 * [BehaviorProcessor]-based implementation of [TakeStateEffect].
 * @param State The [State] type to be emitted.
 * @param R The result emission type.
 * @param cls See [TakeStateEffect.cls].
 * @param creator See [TakeStateEffect.creator].
 */
internal abstract class RxTakeStateEffect<State, R>(
  cls: Class<State>,
  creator: (State) -> ISagaEffect<R>
) : TakeStateEffect<State, R>(cls, creator) where State : Any, R : Any {
  override fun invoke(p1: SagaInput): ISagaOutput<R> {
    val subject = BehaviorProcessor.createDefault(this.cls.cast(p1.lastState())).toSerialized()

    val nested = SagaOutput(p1.scope, p1.monitor, subject, { subject.onComplete() }) { _ ->
      /**
       * By the time [ISagaOutput.onAction] is called, the store would have reduced a new [State]
       * so [SagaInput.lastState] here will produce the latest [State].
       */
      subject.onNext(this@RxTakeStateEffect.cls.cast(p1.lastState())); EmptyJob
    }

    return this.flatten(nested.map { this@RxTakeStateEffect.creator(it).invoke(p1) })
  }
}
