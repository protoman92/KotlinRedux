/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.android.dagger

import androidx.lifecycle.LifecycleOwner
import org.swiften.redux.android.dagger.business1.ParentFragment1
import org.swiften.redux.android.dagger.business2.ParentFragment2
import org.swiften.redux.android.dagger.business3.ParentFragment3
import org.swiften.redux.android.dagger.business1.Fragment1Module
import org.swiften.redux.android.dagger.business2.Fragment2Module
import org.swiften.redux.android.dagger.business3.Fragment3Module
import org.swiften.redux.android.ui.lifecycle.ILifecycleOwnerInjectionHelper
import org.swiften.redux.android.ui.lifecycle.injectLifecycle
import org.swiften.redux.ui.IPropInjector

/** Created by viethai.pham on 2019/02/21 */
class InjectionHelper(val mainComponent: MainComponent) : ILifecycleOwnerInjectionHelper<Redux.State> {
  override fun inject(injector: IPropInjector<Redux.State>, owner: LifecycleOwner) {
    when (owner) {
      is ParentFragment1 -> this.inject(injector, owner)
      is ParentFragment2 -> this.inject(injector, owner)
      is ParentFragment3 -> this.inject(injector, owner)
    }
  }

  private fun inject(injector: IPropInjector<Redux.State>, fragment: ParentFragment1) {
    val component = this.mainComponent.plus(Fragment1Module())
    injector.injectLifecycle(component.dependency(), fragment,
      ParentFragment1
    )
  }

  private fun inject(injector: IPropInjector<Redux.State>, fragment: ParentFragment2) {
    val component = this.mainComponent.plus(Fragment2Module())
    injector.injectLifecycle(component.dependency(), fragment,
      ParentFragment2
    )
  }

  private fun inject(injector: IPropInjector<Redux.State>, fragment: ParentFragment3) {
    val component = this.mainComponent.plus(Fragment3Module())
    injector.injectLifecycle(component.dependency(), fragment,
      ParentFragment3
    )
  }
}
