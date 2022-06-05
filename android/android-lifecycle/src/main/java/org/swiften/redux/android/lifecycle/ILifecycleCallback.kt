/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.android.lifecycle

import androidx.lifecycle.LifecycleObserver
import org.swiften.redux.core.IReduxSubscription
import org.swiften.redux.ui.IFullPropInjector

/** Callback for use with [LifecycleObserver]. */
interface ILifecycleCallback {
  /**
   * This method will be called when it is safe to perform lifecycle-aware tasks, such as
   * [IFullPropInjector.inject].
   */
  fun onSafeForStartingLifecycleAwareTasks()

  /**
   * This method will be called when it is safe to terminate lifecycle-aware tasks, such as
   * [IReduxSubscription.unsubscribe].
   */
  fun onSafeForEndingLifecycleAwareTasks()
}
