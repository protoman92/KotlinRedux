/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.android.dagger.business1

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_1.nav1
import kotlinx.android.synthetic.main.fragment_1.nav2
import org.swiften.redux.android.dagger.DependencyLevel1
import org.swiften.redux.android.dagger.R
import org.swiften.redux.android.dagger.Redux
import org.swiften.redux.core.DefaultSubscriberIDProvider
import org.swiften.redux.core.IActionDispatcher
import org.swiften.redux.core.ISubscriberIDProvider
import org.swiften.redux.ui.IPropContainer
import org.swiften.redux.ui.IPropLifecycleOwner
import org.swiften.redux.ui.IPropMapper
import org.swiften.redux.ui.NoopPropLifecycleOwner
import org.swiften.redux.ui.ObservableReduxProp
import org.swiften.redux.ui.StaticProp
import java.io.Serializable

/** Created by viethai.pham on 2019/02/21 */
class Fragment1 : Fragment(),
  ISubscriberIDProvider by DefaultSubscriberIDProvider(),
  IPropContainer<Fragment1.S, Fragment1.A>,
  IPropLifecycleOwner<Fragment1.ILocalState, DependencyLevel1> by NoopPropLifecycleOwner() {
  companion object : IPropMapper<ILocalState, DependencyLevel1, S, A> {
    override fun mapState(state: ILocalState, outProp: DependencyLevel1): S =
      S()

    override fun mapAction(dispatch: IActionDispatcher, outProp: DependencyLevel1): A =
      A(
        { dispatch(Redux.Screen.Screen2) },
        { dispatch(Redux.Screen.Screen3) }
      )
  }

  interface ILocalState {
    val flow1: S
  }

  class S : Serializable

  class A(val goToScreen2: () -> Unit, val goToScreen3: () -> Unit)

  override var reduxProp by ObservableReduxProp<S, A> { _, _ -> }

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    return inflater.inflate(R.layout.fragment_1, container, false)
  }

  override fun beforePropInjectionStarts(sp: StaticProp<ILocalState, DependencyLevel1>) {
    this.nav1.setOnClickListener { this@Fragment1.reduxProp.action.goToScreen2() }
    this.nav2.setOnClickListener { this@Fragment1.reduxProp.action.goToScreen3() }
  }
}
