/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.android.dagger.business3

import androidx.lifecycle.LifecycleOwner
import org.swiften.redux.android.dagger.Redux
import org.swiften.redux.android.ui.lifecycle.ILifecycleInjectionHelper
import org.swiften.redux.android.ui.lifecycle.injectLifecycle
import org.swiften.redux.ui.IPropInjector

/** Created by viethai.pham on 2019/02/21 */
class Business3InjectionHelper(
  private val component: Business3Component
) : ILifecycleInjectionHelper<Redux.State> {
  override fun inject(injector: IPropInjector<Redux.State>, owner: LifecycleOwner) {
    when (owner) {
      is Business3HostFragment -> this.inject(injector, owner)
    }
  }

  private fun inject(injector: IPropInjector<Redux.State>, fragment: Business3HostFragment) {
    injector.injectLifecycle(this.component.hostDependency(), fragment, Business3HostFragment)
  }
}
