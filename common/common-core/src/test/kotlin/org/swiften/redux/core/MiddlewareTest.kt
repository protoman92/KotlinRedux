/*
 * Copyright (c) haipham 2018. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.core

import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.concurrent.atomic.AtomicInteger

/** Created by haipham on 2018/12/16 */
class MiddlewareTest : BaseMiddlewareTest() {
  @Test
  fun `Applying middlewares to a store should produce correct order`() {
    // Setup
    val store = ThreadSafeStore(0) { a, _ -> a }
    val ordering = arrayListOf<Int>()

    val wrappedStore = applyMiddlewares<Int>(
      { { w -> DispatchWrapper.wrap(w, "1") { w.dispatch(it); ordering.add(1) } } },
      { { w -> DispatchWrapper.wrap(w, "2") { w.dispatch(it); ordering.add(2) } } },
      { { w -> DispatchWrapper.wrap(w, "3") { w.dispatch(it); ordering.add(3) } } }
    )(store)

    // When
    wrappedStore.dispatch(DefaultReduxAction.Dummy)
    wrappedStore.dispatch(DefaultReduxAction.Dummy)
    wrappedStore.dispatch(DefaultReduxAction.Dummy)

    // Then
    assertEquals(ordering, arrayListOf(3, 2, 1, 3, 2, 1, 3, 2, 1))
  }

  @Test
  fun `Middleware should has access to the entire dispatch chain`() {
    // Setup
    val store = ThreadSafeStore(0) { a, _ -> a }
    class TriggerAction : IReduxAction
    class RepeatAction : IReduxAction
    val repeatCount = AtomicInteger()

    val wrappedDispatch = applyMiddlewares<Int>(
      { { w -> DispatchWrapper.wrap(w, "1") {
        w.dispatch(it)
        if (it is RepeatAction) repeatCount.incrementAndGet()
      } } },
      { i -> { w -> DispatchWrapper.wrap(w, "2") {
        w.dispatch(it)
        if (it is TriggerAction) i.dispatch(RepeatAction())
      } } }
    )(store)

    // When && Then
    wrappedDispatch.dispatch(DefaultReduxAction.Dummy)
    assertEquals(repeatCount.get(), 0)
    wrappedDispatch.dispatch(TriggerAction())
    assertEquals(repeatCount.get(), 1)
  }
}
