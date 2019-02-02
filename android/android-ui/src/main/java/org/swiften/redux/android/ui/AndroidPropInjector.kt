/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.android.ui

import org.swiften.redux.android.util.AndroidUtil
import org.swiften.redux.core.IReduxStore
import org.swiften.redux.ui.IPropContainer
import org.swiften.redux.ui.IPropMapper
import org.swiften.redux.ui.PropInjector
import org.swiften.redux.ui.ReduxProps
import org.swiften.redux.ui.StaticProps

/** Created by haipham on 2018/12/17 */
/**
 * [PropInjector] specifically for Android that calls [inject] on the main thread. We use
 * inheritance here to ensure [StaticProps.injector] is set with this class instance.
 */
class AndroidPropInjector<GlobalState>(
  store: IReduxStore<GlobalState>,
  private val runner: AndroidUtil.IMainThreadRunner = AndroidUtil.MainThreadRunner
) : PropInjector<GlobalState>(store) {
  override fun <OutProps, State, Action> inject(
    view: IPropContainer<GlobalState, State, Action>,
    outProps: OutProps,
    mapper: IPropMapper<GlobalState, OutProps, State, Action>
  ) = super.inject(
    object : IPropContainer<GlobalState, State, Action> {
      override var reduxProps: ReduxProps<GlobalState, State, Action>
        get() = view.reduxProps
        set(value) { this@AndroidPropInjector.runner { view.reduxProps = value } }

      override fun beforePropInjectionStarts(sp: StaticProps<GlobalState>) {
        this@AndroidPropInjector.runner { view.beforePropInjectionStarts(sp) }
      }

      override fun afterPropInjectionEnds() {
        this@AndroidPropInjector.runner { view.afterPropInjectionEnds() }
      }

      override fun toString() = view.toString()
    },
    outProps, mapper)
}
