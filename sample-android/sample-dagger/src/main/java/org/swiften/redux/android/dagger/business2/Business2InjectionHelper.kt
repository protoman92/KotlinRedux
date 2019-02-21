/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.android.dagger.business2

import androidx.lifecycle.LifecycleOwner
import org.swiften.redux.android.dagger.Redux
import org.swiften.redux.android.ui.lifecycle.ILifecycleOwnerInjectionHelper
import org.swiften.redux.android.ui.lifecycle.injectLifecycle
import org.swiften.redux.ui.IPropInjector

/** Created by viethai.pham on 2019/02/21 */
class Business2InjectionHelper(
  private val component: Business2Component
) : ILifecycleOwnerInjectionHelper<Redux.State> {
  override fun inject(injector: IPropInjector<Redux.State>, owner: LifecycleOwner) {
    when (owner) {
      is ParentFragment2 -> this.inject(injector, owner)
    }
  }

  private fun inject(injector: IPropInjector<Redux.State>, fragment: ParentFragment2) {
    val component = this.component.plus(Parent2Module())
    injector.injectLifecycle(component.dependency(), fragment, ParentFragment2)
  }
}
