/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.core

/** Created by viethai.pham on 2019/02/25 */
/**
 * Represents a screen that also implements [IReduxAction], so that views can simply dispatch an
 * [IRouterScreen] to navigate to the associated screen.
 */
interface IRouterScreen : IReduxAction

/**
 * Abstract the necessary work to navigate from one [Screen] to another.
 * @param Screen The app [IRouterScreen] type.
 */
interface IRouter<in Screen> : IDeinitializerProvider where Screen : IRouterScreen {
  /**
   * Navigate to an [IRouterScreen]. How this is done is left to the app's specific implementation.
   * @param screen The incoming [Screen] instance.
   */
  fun navigate(screen: Screen)
}

/**
 * Represents a provider for [subRouterPriority] - which is used to sort [NestedRouter.subRouters]
 * based on priority. This allows us to determine which [IVetoableSubRouter] to be called first.
 */
interface ISubRouterPriorityProvider {
  /** The priority of a [IVetoableSubRouter]. */
  val subRouterPriority: Long
}

/**
 * Represents a router whose [navigate] returns a [Boolean] indicating whether a successful
 * navigation happened. This can be used in a main-sub router set-up whereby there is a [Collection]
 * of [IVetoableSubRouter], and every time a [IRouterScreen] arrives, the first [IVetoableSubRouter]
 * that returns true for [navigate] performs the navigation.
 */
interface IVetoableSubRouter : IUniqueIDProvider, ISubRouterPriorityProvider {
  /**
   * Navigate to an [IRouterScreen]. How this is done is left to the app's specific implementation.
   * @param screen The incoming [IRouterScreen] instance.
   * @return A [Boolean] value.
   */
  fun navigate(screen: IRouterScreen): Boolean
}
