/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.store

import org.swiften.redux.core.BaseStoreTest

/** Created by haipham on 2018/12/16 */
class ThreadSafeStoreTest : BaseStoreTest() {
  override fun createStore() = ThreadSafeStore(0, this.reducer())
}
