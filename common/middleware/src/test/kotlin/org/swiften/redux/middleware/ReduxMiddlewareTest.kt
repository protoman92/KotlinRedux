/*
 * Copyright (c) haipham 2018. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.middleware

import org.swiften.redux.core.DefaultReduxAction
import org.swiften.redux.core.SimpleReduxStore
import org.testng.Assert
import org.testng.annotations.Test

/** Created by haipham on 2018/12/16 */
class ReduxMiddlewareTest {
  @Test
  @Suppress("NestedLambdaShadowedImplicitParameter")
  fun `Applying middlewares to a store should produce correct order`() {
    // Setup
    val store = SimpleReduxStore(0) { a, _ -> a }
    val ordering = arrayListOf<Int>()

    val wrappedStore = applyReduxMiddlewares<Int>(
      { { wrapper ->
        ReduxDispatchWrapper("${wrapper.id}-$1") {
          wrapper.dispatch(it); ordering.add(1)
        }
      } },
      { { wrapper ->
        ReduxDispatchWrapper("${wrapper.id}-$2") {
          wrapper.dispatch(it); ordering.add(2)
        }
      } },
      { { wrapper ->
        ReduxDispatchWrapper("${wrapper.id}-$3") {
          wrapper.dispatch(it); ordering.add(3)
        }
      } }
    )(store)

    // When
    wrappedStore.dispatch(DefaultReduxAction.Dummy)
    wrappedStore.dispatch(DefaultReduxAction.Dummy)
    wrappedStore.dispatch(DefaultReduxAction.Dummy)

    // Then
    Assert.assertEquals(ordering, arrayListOf(3, 2, 1, 3, 2, 1, 3, 2, 1))
  }

  @Test
  fun `Applying middlewares with empty middleware list`() {
    // Setup
    val store = SimpleReduxStore(0) { a, _ -> a }

    // When
    val wrapper = combineReduxMiddlewares<Int>(arrayListOf())(store)

    // Then
    Assert.assertEquals(wrapper.id, "root")
  }
}
