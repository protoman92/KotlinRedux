/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.android.lifecycle

import android.os.Bundle

/**
 * Handle saving/restoring [GState] instances.
 * @param GState The global state type.
 */
interface IBundleStateSaver<GState> {
  /**
   * Save [state] to [bundle].
   * @param bundle A [Bundle] instance.
   * @param state A [GState] instance.
   */
  fun saveState(bundle: Bundle, state: GState)

  /**
   * Restore a [GState] from [bundle].
   * @param bundle A [Bundle] instance.
   * @return A [GState] instance.
   */
  fun restoreState(bundle: Bundle): GState?
}
