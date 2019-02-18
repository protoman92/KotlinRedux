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
import org.swiften.redux.ui.ReduxProp
import org.swiften.redux.ui.StaticProp

/** Created by haipham on 2018/12/17 */
/**
 * [PropInjector] specifically for Android that calls [inject] on the main thread. We use
 * inheritance here to ensure [StaticProp.injector] is set with this class instance.
 * @param GState The global state type.
 * @param store An [IReduxStore] instance.
 * @param runner An [AndroidUtil.IMainThreadRunner] instance.
 */
class AndroidPropInjector<GState>(
  store: IReduxStore<GState>,
  private val runner: AndroidUtil.IMainThreadRunner = AndroidUtil.MainThreadRunner
) : PropInjector<GState>(store) where GState : Any {
  override fun <LState, OutProp, View, State, Action> inject(
    outProp: OutProp,
    view: View,
    mapper: IPropMapper<LState, OutProp, State, Action>
  ): IReduxSubscription where
    LState : Any,
    View : IPropContainer<State, Action>,
    View : IPropLifecycleOwner<LState, OutProp>,
    State : Any,
    Action : Any {
    return super.inject(outProp, object : IPropContainer<State, Action>, IPropLifecycleOwner<LState, OutProp> {
      override var reduxProp: ReduxProp<State, Action>
        get() = view.reduxProp
        set(value) { this@AndroidPropInjector.runner { view.reduxProp = value } }

      override fun beforePropInjectionStarts(sp: StaticProp<LState, OutProp>) {
        this@AndroidPropInjector.runner { view.beforePropInjectionStarts(sp) }
      }

      override fun afterPropInjectionEnds() {
        this@AndroidPropInjector.runner { view.afterPropInjectionEnds() }
      }

      override fun toString() = view.toString()
    }, mapper)
  }
}
