package org.swiften.redux

import org.testng.annotations.Test

/**
 * Created by haipham on 2018-12-16.
 */
class SimpleReduxStoreTest: BaseStoreTest() {
  @Test
  fun `dispatching actions with simple store should work`() {
    val store = SimpleReduxStore(0, this.reducer())
    this.dispatchingAction_shouldResultInCorrectState(store)
  }
}
