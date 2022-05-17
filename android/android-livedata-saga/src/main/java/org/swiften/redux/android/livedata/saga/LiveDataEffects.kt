/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.android.livedata.saga

import androidx.lifecycle.LiveData
import org.swiften.redux.saga.SagaEffect

/** Created by haipham on 2019/01/19 */
/** Top level namespace for [LiveData] related [SagaEffect]. */
object LiveDataEffects {
  /**
   * Create a [TakeLiveData] for [creator].
   * @param R The result emission type.
   * @param creator See [TakeLiveData.creator].
   * @return A [SagaEffect] instance.
   */
  fun <R> takeLiveData(creator: () -> LiveData<R>): TakeLiveData<R> where R : Any {
    return TakeLiveData(creator)
  }
}
