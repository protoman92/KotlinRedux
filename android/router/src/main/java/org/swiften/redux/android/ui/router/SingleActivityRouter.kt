/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.android.ui.router

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import org.swiften.redux.router.IReduxRouter
import org.swiften.redux.router.IReduxRouterScreen

/** Created by haipham on 2019/01/12 */
/** [IReduxRouter] that works for a single [AppCompatActivity] and multiple [Fragment] */
class SingleActivityRouter<Screen>(
  private val activity: AppCompatActivity,
  private val fragmentGetter: (Screen) -> Fragment,
  private val navigate: (FragmentManager, Fragment) -> Unit
) : IReduxRouter<Screen> where Screen : IReduxRouterScreen {
  override fun navigate(screen: Screen) {
    this.navigate(this.activity.supportFragmentManager, this.fragmentGetter(screen))
  }
}
