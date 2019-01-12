/*
 * Copyright (c) haipham 2018. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.router

import org.swiften.redux.core.IReduxAction
import org.swiften.redux.core.IReduxDispatcher
import org.swiften.redux.middleware.IReduxMiddleware
import org.swiften.redux.middleware.IReduxMiddlewareProvider
import org.swiften.redux.middleware.ReduxDispatchWrapper
import kotlin.reflect.KClass

/** Created by haipham on 2018/12/16 */
/** Top-level namespace for Redux Router middleware */
/**
 * Represents a screen that also implements [IReduxAction], so that views can simply dispatch an
 * [IReduxRouterScreen] to navigate to the associated screen.
 */
interface IReduxRouterScreen : IReduxAction

/** Abstract the necessary work to navigate from one [Screen] to another */
interface IReduxRouter<Screen> where Screen : IReduxRouterScreen {
  /**
   * Navigate to an [IReduxRouterScreen]. How this is done is left to the app's specific
   * implementation
   */
  fun navigate(screen: Screen)
}

/** [IReduxMiddlewareProvider] implementation for [IReduxRouter] middleware */
class ReduxRouterMiddlewareProvider<State, Screen>(
  private val router: IReduxRouter<Screen>,
  private val cls: Class<Screen>
) : IReduxMiddlewareProvider<State> where Screen : IReduxRouterScreen {
  constructor(router: IReduxRouter<Screen>, cls: KClass<Screen>) : this(router, cls.java)

  override val middleware: IReduxMiddleware<State> = { input ->
    { wrapper -> ReduxDispatchWrapper("$wrapper.id-router",
      object : IReduxDispatcher {
        override fun invoke(action: IReduxAction) {
          wrapper.dispatch(action)

          /**
           * If [action] is an [IReduxRouterScreen] instance, use the [router] to navigate to the
           * associated screen.
           */
          if (this@ReduxRouterMiddlewareProvider.cls.isInstance(action)) {
            val screen = this@ReduxRouterMiddlewareProvider.cls.cast(action)
            this@ReduxRouterMiddlewareProvider.router.navigate(screen)
          }
        }
      })
    }
  }
}
