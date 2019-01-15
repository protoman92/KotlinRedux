/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.store

import org.swiften.redux.core.BaseReduxStoreTest

/** Created by haipham on 2018/01/14 */
class AsyncReduxStoreTest : BaseReduxStoreTest() {
  override fun createStore() = AsyncReduxStore(0, this.reducer())
}
