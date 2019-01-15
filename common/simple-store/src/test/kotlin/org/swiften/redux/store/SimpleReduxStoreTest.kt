/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.store

import org.swiften.redux.core.BaseReduxStoreTest

/** Created by haipham on 2018/12/16 */
class SimpleReduxStoreTest : BaseReduxStoreTest() {
  override fun createStore() = SimpleReduxStore(0, this.reducer())
}
