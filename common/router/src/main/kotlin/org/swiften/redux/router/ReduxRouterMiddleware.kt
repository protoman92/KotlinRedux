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

/** Created by haipham on 2018/12/16 */
/** Top-level namespace for Redux Router middleware */
/**
 * Represents a screen that also implements [IReduxAction], so that views can simply dispatch an
 * [IReduxRouterScreen] to navigate to the associated screen.
 */
interface IReduxRouterScreen : IReduxAction

/** Abstract the necessary work to navigate from one [IReduxRouterScreen] to another */
interface IReduxRouter {
  /**
   * Navigate to an [IReduxRouterScreen]. How this is done is left to the app's specific
   * implementation
   */
  fun navigate(screen: IReduxRouterScreen)
}

/** [IReduxMiddlewareProvider] implementation for [IReduxRouter] middleware */
class ReduxRouterMiddlewareProvider<State>(
  private val router: IReduxRouter
) : IReduxMiddlewareProvider<State> {
  override val middleware: IReduxMiddleware<State> = { input ->
    { wrapper -> ReduxDispatchWrapper("$wrapper.id-router",
      object : IReduxDispatcher {
        override fun invoke(action: IReduxAction) {
          wrapper.dispatch(action)

          /**
           * If [action] is an [IReduxRouterScreen] instance, use the [router] to navigate to the
           * associated screen.
           */
          when (action) {
            is IReduxRouterScreen -> this@ReduxRouterMiddlewareProvider.router.navigate(action)
          }
        }
      })
    }
  }
}
