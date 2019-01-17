/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.android.livedata.rx

import androidx.lifecycle.LiveData
import org.swiften.redux.saga.ReduxSagaEffect

/** Created by haipham on 2019/01/19 */
/** Top level namespace for [LiveData] related [ReduxSagaEffect] */
object LiveDataEffects {
  /** Create a [TakeEveryEffect] for [creator] */
  fun <R> takeEveryData(creator: () -> LiveData<R>): ReduxSagaEffect<R> = TakeEveryEffect(creator)
}
