/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.core

import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.withLock

/** Created by viethai.pham on 2019/02/25 */
/**
 * [IRouter] implementation that holds on to a [List] of [IVetoableRouter], each of which will
 * call [IVetoableRouter.navigate] to check if it can perform a successful navigation. If not, we
 * move on to the next [IVetoableRouter] until the end.
 * @param navigator The navigation function that will be called before we touch [subRouters].
 */
class NestedRouter private constructor (private val navigator: (IRouterScreen) -> Boolean) : IRouter<IRouterScreen> {
  companion object {
    /**
     * Create an [IRouter] instance that provides locking mechanisms for an internal [NestedRouter].
     * @param navigator See [NestedRouter.navigator].
     * @return An [IRouter] instance.
     */
    fun create(navigator: (IRouterScreen) -> Boolean): IRouter<IRouterScreen> {
      return object : IRouter<IRouterScreen> {
        private val lock = ReentrantLock()
        private val router = NestedRouter(navigator)

        override fun navigate(screen: IRouterScreen) {
          this.lock.withLock { this.router.navigate(screen) }
        }

        override val deinitialize: IDeinitializer get() = {
          this.lock.withLock { this.router.deinitialize() }
        }
      }
    }
  }

  sealed class Screen : IRouterScreen {
    data class RegisterSubRouter(val subRouter: IVetoableRouter<IRouterScreen>) : Screen()
    data class UnregisterSubRouter(val subRouter: IVetoableRouter<IRouterScreen>) : Screen()
  }

  private val subRouters = mutableSetOf<IVetoableRouter<IRouterScreen>>()

  override val deinitialize: IDeinitializer get() = {
    this@NestedRouter.subRouters.clear()
  }

  override fun navigate(screen: IRouterScreen) {
    if (when (screen) {
        is Screen.RegisterSubRouter -> {
          this.subRouters.add(screen.subRouter)
          true
        }

        is Screen.UnregisterSubRouter -> {
          this.subRouters.remove(screen.subRouter)
          true
        }

        else -> false
    }) {
      return
    }

    if (this.navigator(screen)) return
    val subRouterSize = this.subRouters.size

    if (subRouterSize > 0) {
      for (subRouter in this.subRouters) {
        if (subRouter.navigate(screen)) {
          return
        }
      }
    }
  }
}
