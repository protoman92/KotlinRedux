package org.swiften.redux

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
    val store = SimpleReduxStore(0, object : Redux.IReducer<Int> {
      override operator fun invoke(previous: Int, action: Redux.IAction): Int {
        return previous
      }
    })

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