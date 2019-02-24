/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.android.dagger.business3

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.business_3_host.nav1
import kotlinx.android.synthetic.main.business_3_host.nav2
import org.swiften.redux.android.dagger.DependencyLevel1
import org.swiften.redux.android.dagger.R
import org.swiften.redux.android.dagger.Redux
import org.swiften.redux.core.DefaultUniqueIDProvider
import org.swiften.redux.core.IActionDispatcher
import org.swiften.redux.core.IUniqueIDProvider
import org.swiften.redux.ui.IPropContainer
import org.swiften.redux.ui.IPropLifecycleOwner
import org.swiften.redux.ui.IPropMapper
import org.swiften.redux.ui.NoopPropLifecycleOwner
import org.swiften.redux.ui.ObservableReduxProp
import org.swiften.redux.ui.StaticProp
import java.io.Serializable

/** Created by viethai.pham on 2019/02/21 */
class Business3HostFragment : Fragment(),
  IUniqueIDProvider by DefaultUniqueIDProvider(),
  IPropContainer<Business3HostFragment.S, Business3HostFragment.A>,
  IPropLifecycleOwner<Redux.State, DependencyLevel1> by NoopPropLifecycleOwner() {
  companion object : IPropMapper<Redux.State, DependencyLevel1, S, A> {
    override fun mapState(state: Redux.State, outProp: DependencyLevel1): S {
      return state.business3.flow3
    }

    override fun mapAction(dispatch: IActionDispatcher, outProp: DependencyLevel1): A {
      return A(
        { dispatch(Redux.Screen.Business1) },
        { dispatch(Redux.Screen.Business2) }
      )
    }
  }

  class S : Serializable

  class A(val goToScreen1: () -> Unit, val goToScreen2: () -> Unit)

  override var reduxProp by ObservableReduxProp<S, A> { _, _ -> }

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    return inflater.inflate(R.layout.business_3_host, container, false)
  }

  override fun beforePropInjectionStarts(sp: StaticProp<Redux.State, DependencyLevel1>) {
    this.nav1.setOnClickListener { this@Business3HostFragment.reduxProp.action.goToScreen1() }
    this.nav2.setOnClickListener { this@Business3HostFragment.reduxProp.action.goToScreen2() }
  }
}
