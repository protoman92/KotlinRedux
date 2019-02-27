/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.android.ui.lifecycle

import androidx.lifecycle.LifecycleOwner
import org.swiften.redux.ui.IPropInjector
import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.withLock

/** Created by viethai.pham on 2019/02/27 */
/**
 * Helps with [IPropInjector.inject] for [LifecycleOwner] instances. Use this to integrate with
 * Dagger.
 * @param GState The global state type.
 */
interface ILifecycleOwnerInjector<GState> where GState : Any {
  fun inject(injector: IPropInjector<GState>, owner: LifecycleOwner)
}

/**
 * A variant of [ILifecycleOwnerInjector] whose [inject] invocation can be vetoed.
 * @param GState The global state type.
 * @param LState The local state type that [GState] must extend from. This is useful for when we
 * develop an app using multiple modules that do not have access to [GState]. [LState] can therefore
 * be an interface that [GState] extends from.
 */
interface IVetoableLifecycleOwnerInjector<GState, LState> where LState : Any, GState : LState {
  /**
   * Perform injection for [owner]. Return false if [owner] cannot receive injection from this
   * [IVetoableLifecycleOwnerInjector].
   * @param injector An [IPropInjector] instance.
   * @param owner A [LifecycleOwner] instance.
   * @return A [Boolean] value.
   */
  fun inject(injector: IPropInjector<LState>, owner: LifecycleOwner): Boolean
}

/**
 * Combine a [Collection] of [IVetoableLifecycleOwnerInjector] into a [ILifecycleOwnerInjector].
 * @param injectionHelpers A [Collection] of [IVetoableLifecycleOwnerInjector].
 * @return An [ILifecycleOwnerInjector] instance.
 */
fun <GState> combineLifecycleOwnerInjectors(
  injectionHelpers: Collection<IVetoableLifecycleOwnerInjector<GState, *>>
): ILifecycleOwnerInjector<GState> where GState : Any {
  return object : ILifecycleOwnerInjector<GState> {
    private val lock = ReentrantLock()

    @Suppress("UNCHECKED_CAST")
    override fun inject(injector: IPropInjector<GState>, owner: LifecycleOwner) {
      this.lock.withLock {
        for (helper in injectionHelpers) {
          if ((helper as IVetoableLifecycleOwnerInjector<GState, GState>).inject(injector, owner)) {
            return@withLock
          }
        }
      }
    }
  }
}

/**
 * Same as [combineLifecycleOwnerInjectors], but handles vararg of
 * [IVetoableLifecycleOwnerInjector].
 * @param injectionHelpers Vararg of [IVetoableLifecycleOwnerInjector].
 * @return An [ILifecycleOwnerInjector] instance.
 */
fun <GState> combineLifecycleOwnerInjectors(
  vararg injectionHelpers: IVetoableLifecycleOwnerInjector<GState, *>
): ILifecycleOwnerInjector<GState> where GState : Any {
  return combineLifecycleOwnerInjectors(injectionHelpers.asList())
}
