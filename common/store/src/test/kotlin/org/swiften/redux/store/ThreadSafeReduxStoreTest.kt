/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.store

import org.swiften.redux.core.BaseReduxStoreTest

/** Created by haipham on 2018/12/16 */
class ThreadSafeReduxStoreTest : BaseReduxStoreTest() {
  override fun createStore() = ThreadSafeReduxStore(0, this.reducer())
}
