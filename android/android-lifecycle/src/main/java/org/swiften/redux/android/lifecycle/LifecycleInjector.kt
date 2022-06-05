/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.android.lifecycle

import androidx.lifecycle.LifecycleOwner
import org.swiften.redux.ui.IPropInjector
import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.withLock

/** Created by viethai.pham on 2019/02/27 */
/**
 * Combine a [Collection] of [IVetoableLifecycleInjectionHelper] into a [ILifecycleInjectionHelper].
 * @param injectionHelpers A [Collection] of [IVetoableLifecycleInjectionHelper].
 * @return An [ILifecycleInjectionHelper] instance.
 */
fun <GState> combineLifecycleInjectionHelpers(
  injectionHelpers: Collection<IVetoableLifecycleInjectionHelper<GState, *>>
): ILifecycleInjectionHelper<GState> {
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
): ILifecycleInjectionHelper<GState> {
  return combineLifecycleInjectionHelpers(injectionHelpers.asList())
}
