/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.android.lifecycle

import androidx.lifecycle.LifecycleOwner
import org.swiften.redux.ui.IPropInjector

/**
 * A variant of [ILifecycleInjectionHelper] whose [inject] invocation can be vetoed.
 * @param GState The global state type.
 * @param LState The local state type that [GState] must extend from. This is useful for when we
 * develop an app using multiple modules that do not have access to [GState]. [LState] can therefore
 * be an interface that [GState] extends from.
 */
interface IVetoableLifecycleInjectionHelper<GState, LState> where GState : LState {
  /**
   * Perform injection for [owner]. Return false if [owner] cannot receive injection from this
   * [IVetoableLifecycleInjectionHelper].
   * @param injector An [IPropInjector] instance.
   * @param owner A [LifecycleOwner] instance.
   * @return A [Boolean] value.
   */
  fun inject(injector: IPropInjector<LState>, owner: LifecycleOwner): Boolean

  /**
   * Deinitialize injection for [owner]. Return false if [owner] cannot be un-injected by this
   * [IVetoableLifecycleInjectionHelper]/
   * @param owner A [LifecycleOwner] instance.
   * @return A [Boolean] value.
   */
  fun deinitialize(owner: LifecycleOwner): Boolean
}
