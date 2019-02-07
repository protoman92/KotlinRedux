/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.android.saga.rx.livedata

import androidx.lifecycle.LiveData
import org.swiften.redux.saga.common.SagaEffect

/** Created by haipham on 2019/01/19 */
/** Top level namespace for [LiveData] related [SagaEffect] */
object LiveDataEffects {
  /**
   * Create a [TakeEveryEffect] for [creator].
   * @param R The result emission type.
   * @param creator See [TakeEveryEffect.creator].
   * @return A [SagaEffect] instance.
   */
  fun <R> takeEveryData(creator: () -> LiveData<R>): SagaEffect<R> {
    return TakeEveryEffect(creator)
  }
}
