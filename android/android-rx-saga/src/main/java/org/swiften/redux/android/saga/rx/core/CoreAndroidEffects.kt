/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.android.saga.rx.core

import android.content.Context
import org.swiften.redux.saga.common.ISagaEffect
import org.swiften.redux.saga.common.SagaEffect

/** Created by haipham on 2019/01/21 */
/** Core [ISagaEffect] for Android */
object CoreAndroidEffects {
  /** Create a [WatchConnectivityEffect] */
  fun watchConnectivity(context: Context): SagaEffect<Boolean> =
    WatchConnectivityEffect(context)
}
