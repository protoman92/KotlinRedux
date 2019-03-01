/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.android.sample

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import kotlinx.android.synthetic.main.main_fragment.view_pager
import org.swiften.redux.core.DefaultUniqueIDProvider
import org.swiften.redux.core.IActionDispatcher
import org.swiften.redux.core.IRouterScreen
import org.swiften.redux.core.IUniqueIDProvider
import org.swiften.redux.core.IVetoableRouter
import org.swiften.redux.core.NestedRouter
import org.swiften.redux.ui.IPropContainer
import org.swiften.redux.ui.IPropLifecycleOwner
import org.swiften.redux.ui.IPropMapper
import org.swiften.redux.ui.NoopPropLifecycleOwner
import org.swiften.redux.ui.ObservableReduxProp
import org.swiften.redux.ui.StaticProp
import java.io.Serializable

/** Created by haipham on 27/1/19 */
class MainFragment : Fragment(),
  IUniqueIDProvider by DefaultUniqueIDProvider(),
  IPropContainer<MainFragment.S, MainFragment.A>,
  IPropLifecycleOwner<MainFragment.ILocalState, Unit> by NoopPropLifecycleOwner(),
  IVetoableRouter {
  companion object : IPropMapper<ILocalState, Unit, S, A> {
    override fun mapAction(dispatch: IActionDispatcher, outProp: Unit): A {
      return A(
        registerSubRouter = { dispatch(NestedRouter.Screen.RegisterSubRouter(it)) },
        unregisterSubRouter = { dispatch(NestedRouter.Screen.UnregisterSubRouter(it)) },
        selectPage = { dispatch(Redux.Action.Main.UpdateSelectedPage(it)) }
      )
    }

    override fun mapState(state: ILocalState, outProp: Unit) = S(state.main.selectedPage)
  }

  interface ILocalState {
    val main: S
  }

  data class S(val selectedPage: Int = 0) : Serializable
  class A(
    val registerSubRouter: (IVetoableRouter) -> Unit,
    val unregisterSubRouter: (IVetoableRouter) -> Unit,
    val selectPage: (Int) -> Unit
  )

  override var reduxProp by ObservableReduxProp<S, A> { _, next ->
    if (next.firstTime) next.action.registerSubRouter(this)
    next.state.also { this.view_pager.currentItem = it.selectedPage }
  }

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? = inflater.inflate(R.layout.main_fragment, container, false)

  override fun beforePropInjectionStarts(sp: StaticProp<ILocalState, Unit>) {
    this.view_pager.adapter = object : FragmentPagerAdapter(this.childFragmentManager) {
      override fun getItem(position: Int): Fragment = when (position) {
        Constants.MAIN_PAGE_DETAIL_INDEX -> DetailFragment()
        else -> SearchFragment()
      }

      override fun getCount() = 2
    }

    this.view_pager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
      override fun onPageScrollStateChanged(state: Int) {}

      override fun onPageScrolled(
        position: Int,
        positionOffset: Float,
        positionOffsetPixels: Int
      ) {}

      override fun onPageSelected(position: Int) {
        this@MainFragment.reduxProp.action.selectPage(position)
      }
    })
  }

  override fun afterPropInjectionEnds(sp: StaticProp<ILocalState, Unit>) {
    this.reduxProp.action.unregisterSubRouter(this)
  }

  override fun navigate(screen: IRouterScreen): Boolean {
    return when (screen) {
      is Redux.Screen.Back -> {
        if (this.reduxProp.state.selectedPage == 1) {
          this.reduxProp.action.selectPage(0); true
        } else false
      }

      else -> false
    }
  }
}
