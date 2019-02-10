/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.android.ui

import org.swiften.redux.android.util.AndroidUtil
import org.swiften.redux.core.IReduxStore
import org.swiften.redux.core.IReduxSubscription
import org.swiften.redux.ui.IPropContainer
import org.swiften.redux.ui.IPropLifecycleOwner
import org.swiften.redux.ui.IPropMapper
import org.swiften.redux.ui.PropInjector
import org.swiften.redux.ui.ReduxProps
import org.swiften.redux.ui.StaticProps

/** Created by haipham on 2018/12/17 */
/**
 * [PropInjector] specifically for Android that calls [inject] on the main thread. We use
 * inheritance here to ensure [StaticProps.injector] is set with this class instance.
 * @param GState The global state type.
 * @param GExt See [PropInjector.external].
 * @param store An [IReduxStore] instance.
 * @param external See [PropInjector.external].
 * @param runner An [AndroidUtil.IMainThreadRunner] instance.
 */
class AndroidPropInjector<GState, GExt>(
  store: IReduxStore<GState>,
  external: GExt,
  private val runner: AndroidUtil.IMainThreadRunner = AndroidUtil.MainThreadRunner
) : PropInjector<GState, GExt>(store, external) where GState : Any, GExt : Any {
  override fun <LExt, OutProps, State, Action> inject(
    view: IPropContainer<GState, LExt, State, Action>,
    outProps: OutProps,
    mapper: IPropMapper<GState, LExt, OutProps, State, Action>
  ): IReduxSubscription where LExt : Any, State : Any, Action : Any {
    return super.inject(object :
      IPropContainer<GState, LExt, State, Action>,
      IPropLifecycleOwner<GState, LExt> {
      override var reduxProps: ReduxProps<State, Action>
        get() = view.reduxProps
        set(value) {
          this@AndroidPropInjector.runner { view.reduxProps = value }
        }

      override fun beforePropInjectionStarts(sp: StaticProps<GState, LExt>) {
        this@AndroidPropInjector.runner { view.beforePropInjectionStarts(sp) }
      }

      override fun afterPropInjectionEnds() {
        this@AndroidPropInjector.runner { view.afterPropInjectionEnds() }
      }

      override fun toString() = view.toString()
    }, outProps, mapper)
  }
}
