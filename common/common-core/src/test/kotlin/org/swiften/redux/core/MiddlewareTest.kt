/*
 * Copyright (c) haipham 2018. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.core

import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Test
import org.swiften.redux.saga.common.SagaMiddleware
import org.swiften.redux.thunk.ThunkMiddleware
import java.util.Collections.synchronizedList
import java.util.concurrent.atomic.AtomicInteger

/** Created by haipham on 2018/12/16 */
class MiddlewareTest : BaseMiddlewareTest() {
  @Test
  fun `Applying middlewares to a store should produce correct order`() {
    // Setup
    val store = ThreadSafeStore(0) { s, _ -> s }
    val order = arrayListOf<Int>()

    val wrappedStore = applyMiddlewares<Int>(
      { { w -> DispatchWrapper.wrap(w, "1") { w.dispatch(it); order.add(1); EmptyAwaitable } } },
      { { w -> DispatchWrapper.wrap(w, "2") { w.dispatch(it); order.add(2); EmptyAwaitable } } },
      { { w -> DispatchWrapper.wrap(w, "3") { w.dispatch(it); order.add(3); EmptyAwaitable } } }
    )(store)

    // When
    wrappedStore.dispatch(DefaultReduxAction.Dummy)
    wrappedStore.dispatch(DefaultReduxAction.Dummy)
    wrappedStore.dispatch(DefaultReduxAction.Dummy)

    // Then
    assertEquals(order, arrayListOf(3, 2, 1, 3, 2, 1, 3, 2, 1))
  }

  @Test
  fun `Root dispatch should be invoked first before any middleware dispatch wrapper`() {
    // Setup
    data class Action(val value: Int) : IReduxAction
    val iteration = 10
    val dispatched = synchronizedList(arrayListOf<IReduxAction>())
    val store = ThreadSafeStore(0) { s, a ->
      println("Dispatching in store $a")
      dispatched.add(a); s
    }

    val wrappedStore = applyMiddlewares<Int>(
      {
        { w ->
          DispatchWrapper.wrap(w, "1") {
            w.dispatch(it).await()
            println("Dispatching in middleware $it")
            Assert.assertTrue(dispatched.contains(it)); EmptyAwaitable
          }
        }
      }
    )(store)

    // When
    (0 until iteration).forEach { wrappedStore.dispatch(Action(it)) }

    runBlocking {
      delay(1000)

      // Then
      assertEquals(dispatched.map { (it as Action).value }.sorted(), (0 until iteration).toList())
    }
  }

  @Test
  fun `Middleware input should has access to the entire dispatch chain`() {
    // Setup
    val store = ThreadSafeStore(0) { a, _ -> a }
    class TriggerAction : IReduxAction
    class RepeatAction : IReduxAction
    val repeatCount = AtomicInteger()

    val wrappedDispatch = applyMiddlewares<Int>(
      { { w -> DispatchWrapper.wrap(w, "1") {
        w.dispatch(it)
        if (it is RepeatAction) repeatCount.incrementAndGet()
        EmptyAwaitable
      } } },
      { i -> { w -> DispatchWrapper.wrap(w, "2") {
        w.dispatch(it)
        if (it is TriggerAction) i.dispatch(RepeatAction())
        EmptyAwaitable
      } } }
    )(store)

    // When && Then
    wrappedDispatch.dispatch(DefaultReduxAction.Dummy)
    assertEquals(repeatCount.get(), 0)
    wrappedDispatch.dispatch(TriggerAction())
    assertEquals(repeatCount.get(), 1)
  }

  @Test
  fun `Middlewares should ensure original dispatch results are returned`() {
    // Setup
    data class Action(val value: Int) : IReduxAction
    val iteration = 1000
    val baseStore = ThreadSafeStore(0) { s, a -> when (a) { is Action -> a.value; else -> s } }

    val store = applyMiddlewares<Int>(
      ThunkMiddleware.create(Unit),
      BatchDispatchMiddleware.create(),
      RouterMiddleware.create(object : IRouter<IRouterScreen> {
        override fun navigate(screen: IRouterScreen) {}
        override fun deinitialize() {}
      }),
      SagaMiddleware.create(effects = arrayListOf()),
      LoggingMiddleware.create()
    )(baseStore)

    val wrappedDispatch = store.dispatch

    // When
    val nextState = wrappedDispatch(BatchAction((0 until iteration).map { Action(it) })).await()

    // Then
    Assert.assertTrue(nextState is Int)
  }
}
