/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.android.dagger

import androidx.lifecycle.LifecycleOwner
import org.swiften.redux.android.dagger.business1.Business1InjectionHelper
import org.swiften.redux.android.dagger.business1.Business1Module
import org.swiften.redux.android.dagger.business1.Business1HostFragment
import org.swiften.redux.android.dagger.business2.Business2InjectionHelper
import org.swiften.redux.android.dagger.business2.Business2Module
import org.swiften.redux.android.dagger.business2.Business2HostFragment
import org.swiften.redux.android.dagger.business3.Business3InjectionHelper
import org.swiften.redux.android.dagger.business3.Business3Module
import org.swiften.redux.android.dagger.business3.Business3HostFragment
import org.swiften.redux.android.ui.lifecycle.ILifecycleOwnerInjectionHelper
import org.swiften.redux.ui.IPropInjector

/** Created by viethai.pham on 2019/02/21 */
class InjectionHelper(private val component: MainComponent) : ILifecycleOwnerInjectionHelper<Redux.State> {
  override fun inject(injector: IPropInjector<Redux.State>, owner: LifecycleOwner) {
    when (owner) {
      is Business1HostFragment -> {
        val module = Business1Module()
        val childComponent = this@InjectionHelper.component.provide(module)
        Business1InjectionHelper(childComponent).inject(injector, owner)
      }

      is Business2HostFragment -> {
        val module = Business2Module()
        val childComponent = this@InjectionHelper.component.provide(module)
        Business2InjectionHelper(childComponent).inject(injector, owner)
      }

      is Business3HostFragment -> {
        val module = Business3Module()
        val childComponent = this@InjectionHelper.component.provide(module)
        Business3InjectionHelper(childComponent).inject(injector, owner)
      }
    }
  }
}
