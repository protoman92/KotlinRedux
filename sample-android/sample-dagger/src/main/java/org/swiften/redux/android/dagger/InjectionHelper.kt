/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.android.dagger

import androidx.lifecycle.LifecycleOwner
import org.swiften.redux.android.dagger.dagger.Fragment1DependencyModule
import org.swiften.redux.android.dagger.dagger.MainComponent
import org.swiften.redux.android.ui.lifecycle.ILifecycleOwnerInjectionHelper
import org.swiften.redux.android.ui.lifecycle.injectLifecycle
import org.swiften.redux.ui.IPropInjector

/** Created by viethai.pham on 2019/02/21 */
class InjectionHelper(val mainComponent: MainComponent) : ILifecycleOwnerInjectionHelper<Redux.State> {
  override fun inject(injector: IPropInjector<Redux.State>, owner: LifecycleOwner) {
    when (owner) {
      is Fragment1 -> this.inject(injector, owner)
      is Fragment2 -> injector.injectLifecycle(Unit, owner, Fragment2)
      is Fragment3 -> injector.injectLifecycle(Unit, owner, Fragment3)
    }
  }

  private fun inject(injector: IPropInjector<Redux.State>, fragment1: Fragment1) {
    val component = this.mainComponent.plus(Fragment1DependencyModule())
    injector.injectLifecycle(component.dependency(), fragment1, Fragment1)
  }
}
