/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.android.saga.rx.livedata

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import io.reactivex.BackpressureStrategy
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import org.swiften.redux.saga.common.ISagaOutput
import org.swiften.redux.saga.common.SagaEffect
import org.swiften.redux.saga.common.SagaInput
import org.swiften.redux.saga.common.SagaOutput

/** Created by haipham on 2019/01/17 */
/**
 * [SagaEffect] whose [ISagaOutput] streams all values emitted by the [LiveData] created by
 * [creator]. Beware that [LiveData.observeForever] and [LiveData.removeObserver] must happen on
 * the main thread, so we use [AndroidSchedulers.mainThread] to ensure subscription and
 * unsubscription are done on the correct thread.
 * @param R The result emission type.
 * @param creator Function that create [LiveData].
 */
class TakeLiveData<R>(private val creator: () -> LiveData<R>) : SagaEffect<R>() where R : Any {
  override fun invoke(p1: SagaInput): ISagaOutput<R> {
    val stream = Observable
      .create<R> { emitter ->
        val observer = Observer<R> { emitter.onNext(it) }
        val data = this@TakeLiveData.creator()
        data.observeForever(observer)
        emitter.setCancellable { data.removeObserver(observer) }
      }
      .subscribeOn(AndroidSchedulers.mainThread())
      .unsubscribeOn(AndroidSchedulers.mainThread())
      .toFlowable(BackpressureStrategy.BUFFER)
      .serialize()

    return SagaOutput(p1.monitor, stream)
  }
}
