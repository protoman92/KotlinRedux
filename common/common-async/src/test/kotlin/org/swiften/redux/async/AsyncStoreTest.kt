/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.async

import org.swiften.redux.core.BaseStoreTest
import org.swiften.redux.core.IReduxStore
import org.swiften.redux.core.ThreadSafeStore
import org.swiften.redux.core.applyMiddlewares

/** Created by haipham on 2018/01/14 */
class AsyncStoreTest : BaseStoreTest() {
  override fun createStore(): IReduxStore<Int> {
    return applyMiddlewares<Int>(createAsyncMiddleware())(ThreadSafeStore(0, this.reducer()))
  }
}
