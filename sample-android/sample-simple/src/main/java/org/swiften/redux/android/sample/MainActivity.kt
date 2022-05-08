/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.android.sample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import org.swiften.redux.core.DefaultUniqueIDProvider
import org.swiften.redux.core.IActionDispatcher
import org.swiften.redux.core.IRouterScreen
import org.swiften.redux.core.IUniqueIDProvider
import org.swiften.redux.core.IVetoableSubRouter
import org.swiften.redux.core.NavigationResult
import org.swiften.redux.core.NestedRouter
import org.swiften.redux.ui.IPropContainer
import org.swiften.redux.ui.IPropLifecycleOwner
import org.swiften.redux.ui.IPropMapper
import org.swiften.redux.ui.NoopPropLifecycleOwner
import org.swiften.redux.ui.ObservableReduxProp
import org.swiften.redux.ui.StaticProp

/** Created by haipham on 26/1/19 */
class MainActivity : AppCompatActivity(),
  IUniqueIDProvider by DefaultUniqueIDProvider(),
  IPropContainer<Unit, MainActivity.Action>,
  IPropLifecycleOwner<Redux.State, Unit> by NoopPropLifecycleOwner(),
  IVetoableSubRouter {
  companion object : IPropMapper<Redux.State, Unit, Unit, Action> {
    override fun mapState(state: Redux.State, outProp: Unit) = Unit

    override fun mapAction(dispatch: IActionDispatcher, outProp: Unit): Action {
      return Action (
        registerSubRouter = { dispatch(NestedRouter.Screen.RegisterSubRouter(it)) },
        unregisterSubRouter = { dispatch(NestedRouter.Screen.UnregisterSubRouter(it)) },
        goBack = { dispatch(Redux.Screen.Back) }
      )
    }
  }

  class Action(
    val registerSubRouter: (IVetoableSubRouter) -> Unit,
    val unregisterSubRouter: (IVetoableSubRouter) -> Unit,
    val goBack: () -> Unit
  )

  override var reduxProp by ObservableReduxProp<Unit, Action> { _, next ->
    if (next.firstTime) {
      next.action.registerSubRouter(this)
    }
  }

  override fun afterPropInjectionEnds(sp: StaticProp<Redux.State, Unit>) {
    this.reduxProp.action.unregisterSubRouter(this)
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    this.setContentView(R.layout.main_activity)
    this.supportActionBar?.hide()

    if (savedInstanceState == null) {
      this.supportFragmentManager
        .beginTransaction()
        .replace(R.id.fragment, MainFragment())
        .addToBackStack(null)
        .commit()
    }
  }

  override fun onBackPressed() {
    this.reduxProp.action.goBack()
  }

  override val subRouterPriority: Long get() = this.uniqueID

  override fun navigate(screen: IRouterScreen): NavigationResult {
    return when (screen) {
      is Redux.Screen.Back -> {
        if (this.supportFragmentManager.backStackEntryCount > 1) {
          this.supportFragmentManager.popBackStack(); NavigationResult.Break
        } else {
          this.finish(); NavigationResult.Break
        }
      }

      else -> {
        val fragment: Fragment? = when (screen) {
          is Redux.Screen.MainFragment -> MainFragment()
          else -> null
        }

        fragment?.also {
          this.supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment, it)
            .addToBackStack(null)
            .commit()
        }

        NavigationResult.Break
      }
    }
  }
}
