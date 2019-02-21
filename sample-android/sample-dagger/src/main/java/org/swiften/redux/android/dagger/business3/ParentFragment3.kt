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
import kotlinx.android.synthetic.main.parent_3.nav1
import kotlinx.android.synthetic.main.parent_3.nav2
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
class ParentFragment3 : Fragment(),
  ISubscriberIDProvider by DefaultSubscriberIDProvider(),
  IPropContainer<ParentFragment3.S, ParentFragment3.A>,
  IPropLifecycleOwner<Redux.State, DependencyLevel1> by NoopPropLifecycleOwner() {
  companion object : IPropMapper<Redux.State, DependencyLevel1, S, A> {
    override fun mapState(state: Redux.State, outProp: DependencyLevel1): S {
      return state.business3.flow3
    }

    override fun mapAction(dispatch: IActionDispatcher, outProp: DependencyLevel1): A {
      return A(
        { dispatch(Redux.Screen.Screen1) },
        { dispatch(Redux.Screen.Screen2) }
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
    return inflater.inflate(R.layout.parent_3, container, false)
  }

  override fun beforePropInjectionStarts(sp: StaticProp<Redux.State, DependencyLevel1>) {
    this.nav1.setOnClickListener { this@ParentFragment3.reduxProp.action.goToScreen1() }
    this.nav2.setOnClickListener { this@ParentFragment3.reduxProp.action.goToScreen2() }
  }
}
