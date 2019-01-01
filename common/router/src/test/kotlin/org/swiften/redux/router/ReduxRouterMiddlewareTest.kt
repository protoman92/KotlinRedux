/*
 * Copyright (c) haipham 2018. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.router

import org.swiften.redux.core.ReduxPreset
import org.swiften.redux.core.SimpleReduxStore
import org.swiften.redux.middleware.ReduxMiddleware
import org.testng.Assert
import org.testng.annotations.AfterMethod
import org.testng.annotations.Test

/**
 * Created by haipham on 2018-12-16.
 */
class ReduxRouterMiddlewareTest: ReduxRouterMiddleware.IRouter {
  sealed class Screen: ReduxRouterMiddleware.IScreen {
    object Screen1: Screen()
    object Screen2: Screen()
    object Screen3: Screen()

    override fun toString(): String {
      return this.javaClass.simpleName
    }
  }

  private val screens = arrayListOf<ReduxRouterMiddleware.IScreen>()

  override fun navigate(screen: ReduxRouterMiddleware.IScreen) {
    this.screens.add(screen)
  }

  @AfterMethod
  fun afterMethod() {
    this.screens.clear()
  }

  @Test
  fun `Dispatching screen actions should navigate to the assoc screen`() {
    /// Setup
    val store = SimpleReduxStore(0) { a, b -> a }

    val wrappedStore = ReduxMiddleware.applyMiddlewares(
      ReduxRouterMiddleware.Provider<Int>(this).middleware
    )(store)

    /// When
    wrappedStore.dispatch(ReduxPreset.DefaultAction.Dummy)
    wrappedStore.dispatch(Screen.Screen1)
    wrappedStore.dispatch(Screen.Screen2)
    wrappedStore.dispatch(Screen.Screen3)

    /// Then
    Assert.assertEquals(this.screens, arrayListOf(
      Screen.Screen1,
      Screen.Screen2,
      Screen.Screen3
    ))
  }
}