/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.android.livedata.rx

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import io.reactivex.BackpressureStrategy
import io.reactivex.Observable
import org.swiften.redux.saga.IReduxSagaOutput
import org.swiften.redux.saga.Input
import org.swiften.redux.saga.ReduxSagaEffect
import org.swiften.redux.saga.rx.ReduxSagaOutput

/** Created by haipham on 2019/01/17 */
/**
 * [ReduxSagaEffect] whose [IReduxSagaOutput] streams all values emitted by the [LiveData] created
 * by [creator].
 */
internal class TakeEveryEffect<R>(private val creator: () -> LiveData<R>): ReduxSagaEffect<R>() {
  override fun invoke(p1: Input): IReduxSagaOutput<R> {
    val stream = Observable.create<R> { emitter ->
      val observer = Observer<R> { emitter.onNext(it) }
      val data = this@TakeEveryEffect.creator()
      data.observeForever(observer)
      emitter.setCancellable { data.removeObserver(observer) }
    }

    return ReduxSagaOutput(p1.scope, stream.toFlowable(BackpressureStrategy.BUFFER)) { }
  }
}
