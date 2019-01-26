/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.store

import org.swiften.redux.core.BaseStoreTest

/** Created by haipham on 2018/01/14 */
class AsyncStoreTest : BaseStoreTest() {
  override fun createStore() =
    AsyncStore(
      ThreadSafeStore(
        0,
        this.reducer()
      )
    )
}
