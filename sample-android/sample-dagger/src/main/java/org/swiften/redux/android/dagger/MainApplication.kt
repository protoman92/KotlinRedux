/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.android.dagger

import android.app.Application
import com.squareup.leakcanary.LeakCanary
import org.swiften.redux.android.ui.AndroidPropInjector
import org.swiften.redux.android.ui.lifecycle.injectActivitySerializable
import org.swiften.redux.core.FinalStore
import org.swiften.redux.core.applyMiddlewares

/** Created by haipham on 26/1/19 */
class MainApplication : Application() {
  override fun onCreate() {
    super.onCreate()
    if (LeakCanary.isInAnalyzerProcess(this)) { return }
    LeakCanary.install(this)
    val store = applyMiddlewares<Redux.State>()(FinalStore(Redux.State(), Redux.Reducer))
    val injector = AndroidPropInjector(store)

    injector.injectActivitySerializable(this) {
      when (it) {
        is Fragment1 -> this.inject(Unit, it, Fragment1)
        is Fragment2 -> this.inject(Unit, it, Fragment2)
        is Fragment3 -> this.inject(Unit, it, Fragment3)
      }
    }
  }
}
