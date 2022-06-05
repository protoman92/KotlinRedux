/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.android.lifecycle

object NoopLifecycleCallback : ILifecycleCallback {
  override fun onSafeForStartingLifecycleAwareTasks() {}

  override fun onSafeForEndingLifecycleAwareTasks() {}
}
