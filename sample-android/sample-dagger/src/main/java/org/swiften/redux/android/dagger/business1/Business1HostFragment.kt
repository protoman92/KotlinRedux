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
import kotlinx.android.synthetic.main.business_1_host.nav1
import kotlinx.android.synthetic.main.business_1_host.nav2
import kotlinx.android.synthetic.main.business_1_host.result_container
import kotlinx.android.synthetic.main.business_1_host.search_progress_bar
import kotlinx.android.synthetic.main.business_1_host.search_view
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
class Business1HostFragment : Fragment(),
  ISubscriberIDProvider by DefaultSubscriberIDProvider(),
  IPropContainer<Business1HostFragment.S, Business1HostFragment.A>,
  IPropLifecycleOwner<Redux.State, DependencyLevel1> by NoopPropLifecycleOwner() {
  companion object : IPropMapper<Redux.State, DependencyLevel1, S, A> {
    override fun mapState(state: Redux.State, outProp: DependencyLevel1): S {
      return state.business1.parent
    }

    override fun mapAction(dispatch: IActionDispatcher, outProp: DependencyLevel1): A {
      return A(
        initializeBusiness1 = { dispatch(Business1Redux.Action.Initialize) },
        deinitializeBusiness1 = { dispatch(Business1Redux.Action.Deinitialize) },
        goToScreen2 = { dispatch(Redux.Screen.Business2) },
        goToScreen3 = { dispatch(Redux.Screen.Business3) }
      )
    }
  }

  data class S(val loading: Boolean = false) : Serializable

  class A(
    val initializeBusiness1: () -> Unit,
    val deinitializeBusiness1: () -> Unit,
    val goToScreen2: () -> Unit,
    val goToScreen3: () -> Unit
  )

  override var reduxProp by ObservableReduxProp<S, A> { prev, next ->
    if (prev == null) next.action.initializeBusiness1()
    this.search_progress_bar.visibility = if (next.state.loading) View.VISIBLE else View.INVISIBLE
  }

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    return inflater.inflate(R.layout.business_1_host, container, false)
  }

  override fun beforePropInjectionStarts(sp: StaticProp<Redux.State, DependencyLevel1>) {
    this.nav1.setOnClickListener { this@Business1HostFragment.reduxProp.action.goToScreen2() }
    this.nav2.setOnClickListener { this@Business1HostFragment.reduxProp.action.goToScreen3() }
    sp.injector.inject(Unit, this.search_view, SearchView)
    sp.injector.inject(Unit, this.result_container, SearchResultContainer)
  }

  override fun afterPropInjectionEnds(sp: StaticProp<Redux.State, DependencyLevel1>) {
    this.reduxProp.action.deinitializeBusiness1()
    sp.injector.unsubscribe(this.search_view.uniqueSubscriberID)
    sp.injector.unsubscribe(this.result_container.uniqueSubscriberID)
  }
}
