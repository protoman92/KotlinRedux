/*
 * Copyright (c) haipham 2018. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.router

import org.swiften.redux.core.DefaultReduxAction
import org.swiften.redux.middleware.applyReduxMiddlewares
import org.swiften.redux.store.SimpleReduxStore
import org.testng.Assert
import org.testng.annotations.AfterMethod
import org.testng.annotations.Test

/** Created by haipham on 2018/12/16 */
class ReduxRouterMiddlewareTest : IReduxRouter<ReduxRouterMiddlewareTest.Screen> {
  sealed class Screen : IReduxRouterScreen {
    object Screen1 : Screen()
    object Screen2 : Screen()
    object Screen3 : Screen()

    override fun toString(): String {
      return this.javaClass.simpleName
    }
  }

  private val screens = arrayListOf<IReduxRouterScreen>()

  override fun navigate(screen: Screen) {
    this.screens.add(screen)
  }

  @AfterMethod
  fun afterMethod() {
    this.screens.clear()
  }

  @Test
  fun `Dispatching screen actions should navigate to the assoc screen`() {
    // Setup
    val store = SimpleReduxStore(0) { a, _ -> a }

    val wrappedStore = applyReduxMiddlewares(
      createRouterMiddlewareProvider<Int, Screen>(this).middleware
    )(store)

    // When
    wrappedStore.dispatch(DefaultReduxAction.Dummy)
    wrappedStore.dispatch(Screen.Screen1)
    wrappedStore.dispatch(Screen.Screen2)
    wrappedStore.dispatch(Screen.Screen3)

    // Then
    Assert.assertEquals(this.screens, arrayListOf(
      Screen.Screen1,
      Screen.Screen2,
      Screen.Screen3
    ))
  }
}
