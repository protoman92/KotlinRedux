/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.android.ui

import android.os.Handler
import android.os.Looper
import org.swiften.redux.core.IReduxStore
import org.swiften.redux.ui.IReduxPropContainer
import org.swiften.redux.ui.IReduxPropMapper
import org.swiften.redux.ui.ReduxPropInjector
import org.swiften.redux.ui.ReduxProps
import org.swiften.redux.ui.StaticProps

/** Created by haipham on 2018/12/17 */
internal fun runOnUIThread(runnable: () -> Unit) {
  if (Looper.myLooper() == Looper.getMainLooper()) { runnable() } else {
    Handler(Looper.getMainLooper()).post { runnable() }
  }
}

/**
 * [ReduxPropInjector] specifically for Android that calls [injectProps] on the main thread. We
 * use inheritance here to ensure [StaticProps.injector] is set with this class instance.
 */
class AndroidPropInjector<State>(store: IReduxStore<State>) : ReduxPropInjector<State>(store) {
  override fun <OutProps, StateProps, ActionProps> injectProps(
    view: IReduxPropContainer<State, StateProps, ActionProps>,
    outProps: OutProps,
    mapper: IReduxPropMapper<State, OutProps, StateProps, ActionProps>
  ) = super.injectProps(
    object : IReduxPropContainer<State, StateProps, ActionProps> {
      override var reduxProps: ReduxProps<State, StateProps, ActionProps>
        get() = view.reduxProps
        set(value) { runOnUIThread { view.reduxProps = value } }

      override fun beforePropInjectionStarts(sp: StaticProps<State>) = runOnUIThread {
        view.beforePropInjectionStarts(sp)
      }

      override fun afterPropInjectionEnds() = runOnUIThread { view.afterPropInjectionEnds() }
      override fun toString() = view.toString()
    },
    outProps, mapper
  )
}
