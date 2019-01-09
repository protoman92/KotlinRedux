/*
 * Copyright (c) haipham 2018. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.router

import org.swiften.redux.core.IReduxAction
import org.swiften.redux.core.IReduxDispatcher
import org.swiften.redux.middleware.IReduxMiddlewareProvider
import org.swiften.redux.middleware.ReduxDispatchWrapper
import org.swiften.redux.middleware.IReduxMiddleware

/** Created by haipham on 2018/12/16 */
/** Top-level namespace for Redux Router middleware */
object ReduxRouterMiddleware {
  /**
   * Represents a screen that also implements [IReduxAction], so that views can simply dispatch
   * an [IScreen] to navigate to the associated screen.
   */
  interface IScreen : IReduxAction

  /** Abstract the necessary work to navigate from one [IScreen] to another */
  interface IRouter {
    /** Navigate to an [IScreen]. How this is done is left to the app's specific implementation */
    fun navigate(screen: IScreen)
  }

  /** [IReduxMiddlewareProvider] implementation for [IRouter] middleware */
  class Provider<State>(private val router: IRouter) : IReduxMiddlewareProvider<State> {
    override val middleware: IReduxMiddleware<State> = { input ->
      { wrapper -> ReduxDispatchWrapper("$wrapper.id-router",
        object : IReduxDispatcher {
          override fun invoke(action: IReduxAction) {
            wrapper.dispatch(action)

            /**
             * If [action] is an [IScreen] instance, use the [router] to navigate to the associated
             * screen.
             */
            when (action) {
              is IScreen -> this@Provider.router.navigate(action)
            }
          }
        })
      }
    }
  }
}
