/*
 * Copyright (c) haipham 2018. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.router

import org.swiften.redux.core.DefaultReduxAction
import org.swiften.redux.core.IDeinitializerProvider
import org.swiften.redux.core.IReduxAction
import org.swiften.redux.middleware.DispatchMapper
import org.swiften.redux.middleware.DispatchWrapper
import org.swiften.redux.middleware.IMiddleware
import org.swiften.redux.middleware.MiddlewareInput
import kotlin.reflect.KClass

/** Created by haipham on 2018/12/16 */
/**
 * Represents a screen that also implements [IReduxAction], so that views can simply dispatch an
 * [IRouterScreen] to navigate to the associated screen.
 */
interface IRouterScreen : IReduxAction

/** Abstract the necessary work to navigate from one [Screen] to another */
interface IRouter<Screen> : IDeinitializerProvider where Screen : IRouterScreen {
  /**
   * Navigate to an [IRouterScreen]. How this is done is left to the app's specific
   * implementation.
   */
  fun navigate(screen: Screen)
}

/** [IMiddleware] implementation for [IRouter] middleware */
@PublishedApi
internal class RouterMiddleware<GlobalState, Screen>(
  private val cls: Class<Screen>,
  private val router: IRouter<Screen>
) : IMiddleware<GlobalState> where Screen : IRouterScreen {
  constructor(cls: KClass<Screen>, router: IRouter<Screen>) : this(cls.java, router)

  override fun invoke(p1: MiddlewareInput<GlobalState>): DispatchMapper {
    return { wrapper ->
      DispatchWrapper("${wrapper.id}-router") { action ->
        wrapper.dispatch(action)

        /**
         * If [action] is an [Screen] instance, use the [router] to navigate to the associated
         * screen.
         */
        if (this@RouterMiddleware.cls.isInstance(action)) {
          val screen = this@RouterMiddleware.cls.cast(action)
          this@RouterMiddleware.router.navigate(screen)
        }

        /** If [DefaultReduxAction.Deinitialize] is sent, call [IRouter.deinitialize] */
        if (action == DefaultReduxAction.Deinitialize) {
          this@RouterMiddleware.router.deinitialize()
        }
      }
    }
  }
}

/** Create a [RouterMiddleware] with [router] */
inline fun <GlobalState, reified Screen> createRouterMiddleware(router: IRouter<Screen>):
  IMiddleware<GlobalState> where Screen : IRouterScreen =
  RouterMiddleware(Screen::class, router)
