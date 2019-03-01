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
 *
 * For this [IRouter] implementation, we are not overly worried about performance because there
 * won't be a situation whereby the user has gone through thousands of [IVetoableRouter]-enabled
 * screens, thus significantly increasing the size of [subRouters].
 * @param navigator The navigation function that will be called before we touch [subRouters].
 * @param comparator A [Comparator] that will be used to sort [subRouters]. This will be done to
 * determine which [IVetoableRouter] should be invoked first, and should be used in conjunction
 * with [DefaultUniqueIDProvider] thanks to its auto-incrementing of provided IDs (so that we know
 * the order of object creation).
 */
class NestedRouter private constructor (
  private val navigator: (IRouterScreen) -> Boolean,
  private val comparator: Comparator<IVetoableRouter> = object : Comparator<IVetoableRouter> {
    override fun compare(p0: IVetoableRouter?, p1: IVetoableRouter?): Int {
      return if (p0 != null && p1 != null) (p1.uniqueID - p0.uniqueID).toInt() else 0
    }
  }
) : IRouter<IRouterScreen> {
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

        override fun deinitialize() {
          this.lock.withLock { this.router.deinitialize() }
        }
      }
    }
  }

  sealed class Screen : IRouterScreen {
    data class RegisterSubRouter(val subRouter: IVetoableRouter) : Screen()
    data class UnregisterSubRouter(val subRouter: IVetoableRouter) : Screen()
  }

  private val subRouters = arrayListOf<IVetoableRouter>()

  override fun navigate(screen: IRouterScreen) {
    if (when (screen) {
        is Screen.RegisterSubRouter -> {
          if (!this.subRouters.contains(screen.subRouter)) {
            this.subRouters.add(0, screen.subRouter)
            this.subRouters.sortWith(this@NestedRouter.comparator)
          }

          true
        }

        is Screen.UnregisterSubRouter -> {
          for (i in 0 until this.subRouters.size) {
            if (this.subRouters.elementAtOrNull(i)?.uniqueID == screen.subRouter.uniqueID) {
              this.subRouters.removeAt(i)
              break
            }
          }

          true
        }

        else -> false
    }) {
      return
    }

    if (this.navigator(screen)) return
    for (subRouter in this.subRouters) { if (subRouter.navigate(screen)) return }
  }

  override fun deinitialize() {
    this@NestedRouter.subRouters.clear()
  }
}
