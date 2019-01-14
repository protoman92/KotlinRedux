/*
 * Copyright (c) haipham 2018. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.android.sample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import org.swiften.redux.android.ui.core.endFragmentInjection
import org.swiften.redux.android.ui.core.injectLifecycleProps
import org.swiften.redux.android.ui.core.startFragmentInjection
import org.swiften.redux.core.IReduxDispatcher
import org.swiften.redux.ui.IReduxPropContainer
import org.swiften.redux.ui.IReduxPropMapper
import org.swiften.redux.ui.StaticProps
import org.swiften.redux.ui.VariableProps
import kotlin.properties.Delegates

/** Created by haipham on 2018/12/19 */
class MainActivity : AppCompatActivity(),
  IReduxPropContainer<State, Unit, MainActivity.A>,
  IReduxPropMapper<State, Unit, Unit, MainActivity.A> by MainActivity {
  class A(val goToMainScreen: () -> Unit)

  companion object : IReduxPropMapper<State, Unit, Unit, A> {
    override fun mapState(state: State, outProps: Unit) = Unit

    override fun mapAction(dispatch: IReduxDispatcher, state: State, outProps: Unit) =
      A { dispatch(MainRedux.Screen.MainScreen) }
  }

  override lateinit var staticProps: StaticProps<State>

  override var variableProps by Delegates.observable<VariableProps<Unit, A>?>(null) { _, _, p ->
    p?.also { it.actions.goToMainScreen() }
  }

  private lateinit var fragmentCallback: FragmentManager.FragmentLifecycleCallbacks

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
  }

  override fun beforePropInjectionStarts() {
    this.fragmentCallback = startFragmentInjection(this) {
      when (it) {
        is SearchFragment -> this.injector.injectLifecycleProps(it, Unit, it)
        is MusicDetailFragment -> this.injector.injectLifecycleProps(it, Unit, it)
      }
    }
  }

  override fun afterPropInjectionEnds() {
    endFragmentInjection(this, this.fragmentCallback)
  }
}
