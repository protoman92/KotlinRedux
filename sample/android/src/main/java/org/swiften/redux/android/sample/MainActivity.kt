/*
 * Copyright (c) haipham 2018. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.android.sample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import org.swiften.redux.ui.ReduxUI

/** Created by haipham on 2018/12/19 */
class MainActivity : AppCompatActivity(), ReduxUI.IStaticPropContainerView<State> {
  override var staticProps: ReduxUI.StaticProps<State>? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    if (savedInstanceState == null) {
      val tx = this.supportFragmentManager.beginTransaction()
      tx.replace(R.id.fragment, MainFragment())
      tx.commit()
    }

    this.supportFragmentManager.registerFragmentLifecycleCallbacks(
      object : FragmentManager.FragmentLifecycleCallbacks() {
        override fun onFragmentStarted(fm: FragmentManager, f: Fragment) {
          val injector = requireNotNull(this@MainActivity.staticProps).injector

          when (f) {
            is SearchFragment -> injector.injectProps(f, Unit, f)
          }
        }
      }, true)
  }
}
