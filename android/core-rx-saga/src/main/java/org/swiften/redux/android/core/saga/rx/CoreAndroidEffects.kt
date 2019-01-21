/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.android.core.saga.rx

import android.app.Application
import android.content.Context
import org.swiften.redux.saga.IReduxSagaEffect
import org.swiften.redux.saga.ReduxSagaEffect

/** Created by haipham on 2019/01/21 */
/** Core [IReduxSagaEffect] for Android */
object CoreAndroidEffects {
  /** Create a [WatchConnectivityEffect] */
  fun watchConnectivity(context: Context): ReduxSagaEffect<Boolean> =
    WatchConnectivityEffect(context)
}
