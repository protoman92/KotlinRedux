/*
 * Copyright (c) haipham 2018. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.router

import org.junit.Assert
import org.junit.Test
import org.swiften.redux.core.DefaultActionStore
import org.swiften.redux.core.DefaultReduxAction
import org.swiften.redux.core.IDeinitializer
import org.swiften.redux.core.ThreadSafeStore
import org.swiften.redux.middleware.applyMiddlewares

/** Created by haipham on 2018/12/16 */
class RouterMiddlewareTest {
  sealed class Screen : IRouterScreen {
    object Screen1 : Screen()
    object Screen2 : Screen()
    object Screen3 : Screen()

    override fun toString(): String {
      return this.javaClass.simpleName
    }
  }

  class Router : IRouter<Screen> {
    val screens = arrayListOf<IRouterScreen>()
    var deinitialized = false

    override fun navigate(screen: Screen) {
      this.screens.add(screen)
    }

    override val deinitialize: IDeinitializer get() = { this.deinitialized = true }
  }

  @Test
  fun `Dispatching screen actions should navigate to the assoc screen`() {
    // Setup
    val store = ThreadSafeStore(0) { a, _ -> a }
    val router = Router()
    val wrappedStore = applyMiddlewares<Int>(createRouterMiddleware<Screen>(router))(store)

    // When
    wrappedStore.dispatch(DefaultReduxAction.Dummy)
    wrappedStore.dispatch(Screen.Screen1)
    wrappedStore.dispatch(Screen.Screen2)
    wrappedStore.dispatch(Screen.Screen3)

    // Then
    Assert.assertEquals(router.screens, arrayListOf(
      Screen.Screen1,
      Screen.Screen2,
      Screen.Screen3
    ))
  }

  @Test
  fun `Sending deinitialize action should deinitialize router`() {
    // Setup
    val router = Router()

    val wrappedStore = applyMiddlewares<Int>(
      createRouterMiddleware(router)
    )(DefaultActionStore(ThreadSafeStore(0) { a, _ -> a }))

    // When
    wrappedStore.dispatch(DefaultReduxAction.Deinitialize)

    // Then
    Assert.assertTrue(router.deinitialized)
  }
}
