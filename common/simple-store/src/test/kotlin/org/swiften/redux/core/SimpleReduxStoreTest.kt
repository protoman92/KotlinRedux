/*
 * Copyright (c) haipham 2018. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.core

import org.testng.annotations.Test

/** Created by haipham on 2018/12/16 */
class SimpleReduxStoreTest : BaseReduxStoreTest() {
  @Test
  fun `Dispatching actions with simple store should ensure thread safety`() {
    val store = SimpleReduxStore(0, this.reducer())
    this.dispatchingAction_shouldResultInCorrectState(store)
  }
}
