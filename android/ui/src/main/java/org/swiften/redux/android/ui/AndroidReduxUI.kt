/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.android.ui

import android.os.Handler
import android.os.Looper
import org.swiften.redux.core.IReduxStore
import org.swiften.redux.ui.IPropContainer
import org.swiften.redux.ui.IPropMapper
import org.swiften.redux.ui.PropInjector
import org.swiften.redux.ui.ReduxProps
import org.swiften.redux.ui.StaticProps

/** Created by haipham on 2018/12/17 */
internal fun runOnUIThread(runnable: () -> Unit) {
  if (Looper.myLooper() == Looper.getMainLooper()) { runnable() } else {
    Handler(Looper.getMainLooper()).post { runnable() }
  }
}

/**
 * [PropInjector] specifically for Android that calls [injectProps] on the main thread. We
 * use inheritance here to ensure [StaticProps.injector] is set with this class instance.
 */
class AndroidPropInjector<GlobalState>(store: IReduxStore<GlobalState>) :
  PropInjector<GlobalState>(store) {
  override fun <OutProps, State, Action> injectProps(
    view: IPropContainer<GlobalState, State, Action>,
    outProps: OutProps,
    mapper: IPropMapper<GlobalState, OutProps, State, Action>
  ) = super.injectProps(
    object : IPropContainer<GlobalState, State, Action> {
      override var reduxProps: ReduxProps<GlobalState, State, Action>?
        get() = view.reduxProps
        set(value) { runOnUIThread { view.reduxProps = value } }

      override fun beforePropInjectionStarts(sp: StaticProps<GlobalState>) = runOnUIThread {
        view.beforePropInjectionStarts(sp)
      }

      override fun afterPropInjectionEnds() = runOnUIThread { view.afterPropInjectionEnds() }
      override fun toString() = view.toString()
    },
    outProps, mapper
  )
}
