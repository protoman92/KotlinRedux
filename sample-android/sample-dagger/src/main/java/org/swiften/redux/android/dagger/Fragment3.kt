/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.android.dagger

import androidx.fragment.app.Fragment
import org.swiften.redux.core.DefaultSubscriberIDProvider
import org.swiften.redux.core.IActionDispatcher
import org.swiften.redux.core.ISubscriberIDProvider
import org.swiften.redux.ui.IPropContainer
import org.swiften.redux.ui.IPropLifecycleOwner
import org.swiften.redux.ui.IPropMapper
import org.swiften.redux.ui.NoopPropLifecycleOwner
import org.swiften.redux.ui.ObservableReduxProp

/** Created by viethai.pham on 2019/02/21 */
class Fragment3 : Fragment(),
  ISubscriberIDProvider by DefaultSubscriberIDProvider(),
  IPropContainer<Fragment3.S, Fragment3.A>,
  IPropLifecycleOwner<Fragment3.ILocalState, Unit> by NoopPropLifecycleOwner() {
  companion object : IPropMapper<ILocalState, Unit, S, A> {
    override fun mapState(state: ILocalState, outProp: Unit): S = S()
    override fun mapAction(dispatch: IActionDispatcher, outProp: Unit): A = A()
  }

  interface ILocalState {
    val flow3: S
  }

  class S
  class A

  override var reduxProp by ObservableReduxProp<S, A> { _, _ -> }
}
