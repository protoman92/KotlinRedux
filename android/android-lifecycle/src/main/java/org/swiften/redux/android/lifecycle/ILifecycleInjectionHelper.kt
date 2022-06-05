/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.android.lifecycle

import androidx.lifecycle.LifecycleOwner
import org.swiften.redux.ui.IPropInjector

/**
 * Helps with [IPropInjector.inject] for [LifecycleOwner] instances. Use this to integrate with
 * Dagger.
 * @param GState The global state type.
 */
interface ILifecycleInjectionHelper<GState> {
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
