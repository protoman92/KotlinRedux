/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.android.dagger

import androidx.lifecycle.LifecycleOwner
import org.swiften.redux.android.dagger.dagger.Fragment1Module
import org.swiften.redux.android.dagger.dagger.Fragment2Module
import org.swiften.redux.android.dagger.dagger.Fragment3Module
import org.swiften.redux.android.dagger.dagger.MainComponent
import org.swiften.redux.android.ui.lifecycle.ILifecycleOwnerInjectionHelper
import org.swiften.redux.android.ui.lifecycle.injectLifecycle
import org.swiften.redux.ui.IPropInjector

/** Created by viethai.pham on 2019/02/21 */
class InjectionHelper(val mainComponent: MainComponent) : ILifecycleOwnerInjectionHelper<Redux.State> {
  override fun inject(injector: IPropInjector<Redux.State>, owner: LifecycleOwner) {
    when (owner) {
      is Fragment1 -> this.inject(injector, owner)
      is Fragment2 -> this.inject(injector, owner)
      is Fragment3 -> this.inject(injector, owner)
    }
  }

  private fun inject(injector: IPropInjector<Redux.State>, fragment: Fragment1) {
    val component = this.mainComponent.plus(Fragment1Module())
    injector.injectLifecycle(component.dependency(), fragment, Fragment1)
  }

  private fun inject(injector: IPropInjector<Redux.State>, fragment: Fragment2) {
    val component = this.mainComponent.plus(Fragment2Module())
    injector.injectLifecycle(component.dependency(), fragment, Fragment2)
  }

  private fun inject(injector: IPropInjector<Redux.State>, fragment: Fragment3) {
    val component = this.mainComponent.plus(Fragment3Module())
    injector.injectLifecycle(component.dependency(), fragment, Fragment3)
  }
}
