/*
 * Copyright (c) haipham 2018. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.router

import org.junit.Assert
import org.junit.Test
import org.swiften.redux.core.DefaultReduxAction
import org.swiften.redux.core.IReduxDeinitializer
import org.swiften.redux.middleware.applyReduxMiddlewares
import org.swiften.redux.store.DefaultActionReduxStore
import org.swiften.redux.store.SimpleReduxStore

/** Created by haipham on 2018/12/16 */
class ReduxRouterMiddlewareTest {
  sealed class Screen : IReduxRouterScreen {
    object Screen1 : Screen()
    object Screen2 : Screen()
    object Screen3 : Screen()

    override fun toString(): String {
      return this.javaClass.simpleName
    }
  }

  class Router : IReduxRouter<Screen> {
    val screens = arrayListOf<IReduxRouterScreen>()
    var deinitialized = false

    override fun navigate(screen: Screen) {
      this.screens.add(screen)
    }

    override val deinitialize: IReduxDeinitializer get() = { this.deinitialized = true }
  }

  @Test
  fun `Dispatching screen actions should navigate to the assoc screen`() {
    // Setup
    val store = SimpleReduxStore(0) { a, _ -> a }
    val router = Router()
    val wrappedStore = applyReduxMiddlewares(createRouterMiddleware<Int, Screen>(router))(store)

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

    val wrappedStore = applyReduxMiddlewares(
      createRouterMiddleware<Int, Screen>(router)
    )(DefaultActionReduxStore(SimpleReduxStore(0) { a, _ -> a }))

    // When
    wrappedStore.dispatch(DefaultReduxAction.Deinitialize)

    // Then
    Assert.assertTrue(router.deinitialized)
  }
}
