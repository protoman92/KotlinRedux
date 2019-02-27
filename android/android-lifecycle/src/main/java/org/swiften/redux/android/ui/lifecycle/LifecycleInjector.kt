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
interface ILifecycleInjectionHelper<GState> where GState : Any {
  /**
   * Perform injection for [owner].
   * @param injector An [IPropInjector] instance.
   * @param owner A [LifecycleOwner] instance.
   */
  fun inject(injector: IPropInjector<GState>, owner: LifecycleOwner)

  /**
   * Deinitialize injection for [owner].
   * @param owner A [LifecycleOwner] instance.
   */
  fun deinitialize(owner: LifecycleOwner)
}

/**
 * A variant of [ILifecycleInjectionHelper] whose [inject] invocation can be vetoed.
 * @param GState The global state type.
 * @param LState The local state type that [GState] must extend from. This is useful for when we
 * develop an app using multiple modules that do not have access to [GState]. [LState] can therefore
 * be an interface that [GState] extends from.
 */
interface IVetoableLifecycleInjectionHelper<GState, LState> where LState : Any, GState : LState {
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

/**
 * Combine a [Collection] of [IVetoableLifecycleInjectionHelper] into a [ILifecycleInjectionHelper].
 * @param injectionHelpers A [Collection] of [IVetoableLifecycleInjectionHelper].
 * @return An [ILifecycleInjectionHelper] instance.
 */
fun <GState> combineLifecycleInjectionHelpers(
  injectionHelpers: Collection<IVetoableLifecycleInjectionHelper<GState, *>>
): ILifecycleInjectionHelper<GState> where GState : Any {
  return object : ILifecycleInjectionHelper<GState> {
    private val lock = ReentrantLock()

    @Suppress("UNCHECKED_CAST")
    override fun inject(injector: IPropInjector<GState>, owner: LifecycleOwner) {
      this.lock.withLock {
        for (helper in injectionHelpers) {
          if ((helper as IVetoableLifecycleInjectionHelper<GState, GState>).inject(injector, owner)) {
            return@withLock
          }
        }
      }
    }

    override fun deinitialize(owner: LifecycleOwner) {
      this.lock.withLock {
        for (helper in injectionHelpers) {
          if (helper.deinitialize(owner)) return@withLock
        }
      }
    }
  }
}

/**
 * Same as [combineLifecycleInjectionHelpers], but handles vararg of
 * [IVetoableLifecycleInjectionHelper].
 * @param injectionHelpers Vararg of [IVetoableLifecycleInjectionHelper].
 * @return An [ILifecycleInjectionHelper] instance.
 */
fun <GState> combineLifecycleInjectionHelpers(
  vararg injectionHelpers: IVetoableLifecycleInjectionHelper<GState, *>
): ILifecycleInjectionHelper<GState> where GState : Any {
  return combineLifecycleInjectionHelpers(injectionHelpers.asList())
}
