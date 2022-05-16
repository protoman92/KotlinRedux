/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.android.dagger.business2

import androidx.lifecycle.LifecycleOwner
import org.swiften.redux.android.dagger.Redux
import org.swiften.redux.android.lifecycle.ILifecycleInjectionHelper
import org.swiften.redux.android.lifecycle.injectLifecycle
import org.swiften.redux.ui.IPropInjector

/** Created by viethai.pham on 2019/02/21 */
class Business2InjectionHelper(
  private val component: Business2Component
) : ILifecycleInjectionHelper<Redux.State> {
  override fun inject(injector: IPropInjector<Redux.State>, owner: LifecycleOwner) {
    when (owner) {
      is Business2HostFragment -> this.inject(injector, owner)
    }
  }

  override fun deinitialize(owner: LifecycleOwner) {}

  private fun inject(injector: IPropInjector<Redux.State>, fragment: Business2HostFragment) {
    injector.injectLifecycle(this.component.hostDependency(), fragment, Business2HostFragment)
  }
}
