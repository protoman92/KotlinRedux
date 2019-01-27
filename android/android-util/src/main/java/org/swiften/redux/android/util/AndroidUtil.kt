/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.android.util

import android.os.Handler
import android.os.Looper

/** Created by haipham on 2019/01/27 */
object AndroidUtil {
  /** Invoke [runnable] on the main thread */
  @JvmStatic
  fun runOnUIThread(runnable: () -> Unit) {
    if (Looper.myLooper() == Looper.getMainLooper()) {
      runnable()
    } else {
      Handler(Looper.getMainLooper()).post { runnable() }
    }
  }
}
