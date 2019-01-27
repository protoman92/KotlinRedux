/*
 * Copyright (c) haipham 2018. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.core

import org.junit.Assert
import org.junit.Test
import org.swiften.redux.core.DefaultReduxAction
import org.swiften.redux.core.DispatchWrapper
import org.swiften.redux.core.ThreadSafeStore
import org.swiften.redux.core.applyMiddlewares
import org.swiften.redux.core.combineMiddlewares

/** Created by haipham on 2018/12/16 */
class MiddlewareTest: BaseMiddlewareTest() {
  @Test
  @Suppress("NestedLambdaShadowedImplicitParameter")
  fun `Applying middlewares to a store should produce correct order`() {
    // Setup
    val store = ThreadSafeStore(0) { a, _ -> a }
    val ordering = arrayListOf<Int>()

    val wrappedStore = applyMiddlewares<Int>(
      {
        { wrapper ->
          DispatchWrapper("${wrapper.id}-$1") {
            wrapper.dispatch(it); ordering.add(1)
          }
        }
      },
      {
        { wrapper ->
          DispatchWrapper("${wrapper.id}-$2") {
            wrapper.dispatch(it); ordering.add(2)
          }
        }
      },
      {
        { wrapper ->
          DispatchWrapper("${wrapper.id}-$3") {
            wrapper.dispatch(it); ordering.add(3)
          }
        }
      }
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
    val store = ThreadSafeStore(0) { a, _ -> a }

    // When
    val wrapper = combineMiddlewares<Int>(arrayListOf())(store)

    // Then
    Assert.assertEquals(wrapper.id, DispatchWrapper.ROOT_WRAPPER)
  }
}
