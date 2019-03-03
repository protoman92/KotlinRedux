/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.core

import kotlin.reflect.KClass

/** Created by haipham on 2018/12/16 */
/**
 * [IMiddleware] implementation for [IRouter] middleware.
 * @param Screen The app [IRouterScreen] type.
 * @param cls The [Class] of the [Screen] type to check type for [IReduxAction].
 * @param router An [IRouter] instance.
 */
class RouterMiddleware<out Screen> private constructor (
  private val cls: Class<Screen>,
  private val router: IRouter<Screen>
) : IMiddleware<Any> where Screen : IRouterScreen {
  companion object {
    /**
     * Create a [RouterMiddleware] with [router].
     * @param Screen The app [IRouterScreen] type.
     * @param router See [RouterMiddleware.router].
     * @return A [RouterMiddleware] instance.
     */
    inline fun <reified Screen> create(
      router: IRouter<Screen>
    ): IMiddleware<Any> where Screen : IRouterScreen {
      return RouterMiddleware(Screen::class, router)
    }
  }

  constructor(cls: KClass<Screen>, router: IRouter<Screen>) : this(cls.java, router)

  override fun invoke(p1: MiddlewareInput<Any>): DispatchMapper {
    return { wrapper ->
      DispatchWrapper.wrap(wrapper, "router") { action ->
        val dispatchJob = wrapper.dispatch(action)

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

        dispatchJob
      }
    }
  }
}
