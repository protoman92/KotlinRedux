/*
 * Copyright (c) haipham 2018. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.router

import org.swiften.redux.core.DefaultReduxAction
import org.swiften.redux.core.IReduxAction
import org.swiften.redux.core.IReduxDeinitializerProvider
import org.swiften.redux.middleware.IReduxMiddleware
import org.swiften.redux.middleware.ReduxDispatchMapper
import org.swiften.redux.middleware.ReduxDispatchWrapper
import org.swiften.redux.middleware.ReduxMiddlewareInput
import kotlin.reflect.KClass

/** Created by haipham on 2018/12/16 */
/** Top-level namespace for Redux Router middleware */
/**
 * Represents a screen that also implements [IReduxAction], so that views can simply dispatch an
 * [IReduxRouterScreen] to navigate to the associated screen.
 */
interface IReduxRouterScreen : IReduxAction

/** Abstract the necessary work to navigate from one [Screen] to another */
interface IReduxRouter<Screen> : IReduxDeinitializerProvider where Screen : IReduxRouterScreen {
  /**
   * Navigate to an [IReduxRouterScreen]. How this is done is left to the app's specific
   * implementation
   */
  fun navigate(screen: Screen)
}

/** [IReduxMiddleware] implementation for [IReduxRouter] middleware */
@PublishedApi
internal class ReduxRouterMiddleware<GlobalState, Screen>(
  private val cls: Class<Screen>,
  private val router: IReduxRouter<Screen>
) : IReduxMiddleware<GlobalState> where Screen : IReduxRouterScreen {
  constructor(cls: KClass<Screen>, router: IReduxRouter<Screen>) : this(cls.java, router)

  override fun invoke(p1: ReduxMiddlewareInput<GlobalState>): ReduxDispatchMapper {
    return { wrapper ->
      ReduxDispatchWrapper("$wrapper.id-router") { action ->
        wrapper.dispatch(action)
        /**
         * If [action] is an [Screen] instance, use the [router] to navigate to the associated
         * screen.
         */
        if (this@ReduxRouterMiddleware.cls.isInstance(action)) {
          val screen = this@ReduxRouterMiddleware.cls.cast(action)
          this@ReduxRouterMiddleware.router.navigate(screen)
        }

        /** If [DefaultReduxAction.Deinitialize] is sent, call [IReduxRouter.deinitialize] */
        if (action == DefaultReduxAction.Deinitialize) {
          this@ReduxRouterMiddleware.router.deinitialize()
        }
      }
    }
  }
}

/** Create a [ReduxRouterMiddleware] with [router] */
inline fun <GlobalState, reified Screen> createRouterMiddleware(router: IReduxRouter<Screen>):
  IReduxMiddleware<GlobalState> where Screen : IReduxRouterScreen =
  ReduxRouterMiddleware(Screen::class, router)
