/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.android.util

import android.os.Handler
import android.os.Looper

/** Created by haipham on 2019/01/27 */
/** Top level namespace for Android utilities */
object AndroidUtil {
  /** Invoke [runnable] on the main thread */
  @JvmStatic
  internal fun runOnUIThread(runnable: () -> Unit) {
    if (Looper.myLooper() == Looper.getMainLooper()) {
      runnable()
    } else {
      Handler(Looper.getMainLooper()).post { runnable() }
    }
  }

  /** [invoke] some tasks on the main thread. */
  interface IMainThreadRunner {
    operator fun invoke(runnable: () -> Unit)
  }

  /** Default implementation for [IMainThreadRunner] */
  object MainThreadRunner : IMainThreadRunner {
    override fun invoke(runnable: () -> Unit) = runOnUIThread(runnable)
  }
}
