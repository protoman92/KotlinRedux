/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.android.livedata.saga.rx

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import io.reactivex.BackpressureStrategy
import io.reactivex.Observable
import org.swiften.redux.saga.ISagaOutput
import org.swiften.redux.saga.SagaEffect
import org.swiften.redux.saga.SagaInput
import org.swiften.redux.saga.rx.SagaOutput

/** Created by haipham on 2019/01/17 */
/**
 * [SagaEffect] whose [ISagaOutput] streams all values emitted by the [LiveData] created
 * by [creator].
 */
internal class TakeEveryEffect<R>(private val creator: () -> LiveData<R>) : SagaEffect<R>() {
  override fun invoke(p1: SagaInput): ISagaOutput<R> {
    val stream = Observable.create<R> { emitter ->
      val observer = Observer<R> { emitter.onNext(it) }
      val data = this@TakeEveryEffect.creator()
      data.observeForever(observer)
      emitter.setCancellable { data.removeObserver(observer) }
    }

    return SagaOutput(p1.scope, stream.toFlowable(BackpressureStrategy.BUFFER)) { }
  }
}
