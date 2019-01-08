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
import org.swiften.redux.ui.ReduxUI

/** Created by haipham on 2018/12/19 */
class MainActivity : AppCompatActivity(), ReduxUI.IStaticPropContainer<State> {
  override lateinit var staticProps: ReduxUI.StaticProps<State>
  lateinit var fragmentCallback: FragmentManager.FragmentLifecycleCallbacks

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    if (savedInstanceState == null) {
      val tx = this.supportFragmentManager.beginTransaction()
      tx.replace(R.id.fragment, MainFragment())
      tx.commit()
    }

    this.fragmentCallback = ReduxUI.startFragmentInjection(this) {
      when (it) {
        is SearchFragment -> this.injector.injectLifecycleProps(it, Unit, it)
      }
    }
  }

  override fun onDestroy() {
    super.onDestroy()
    ReduxUI.endFragmentInjection(this, this.fragmentCallback)
  }
}
