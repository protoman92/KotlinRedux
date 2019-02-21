/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.android.dagger

import android.app.Application
import androidx.fragment.app.Fragment
import org.swiften.redux.android.router.createSingleActivityRouter
import org.swiften.redux.core.IRouter

/** Created by viethai.pham on 2019/02/21 */
class Router(application: Application) : IRouter<Redux.Screen>
by createSingleActivityRouter<MainActivity, Redux.Screen>(application, { a, s ->
  println("Redux $s")
  val fragment: Fragment = when (s) {
    is Redux.Screen.Screen1 -> Fragment1()
    is Redux.Screen.Screen2 -> Fragment2()
    is Redux.Screen.Screen3 -> Fragment3()
  }

  a.supportFragmentManager.beginTransaction()
    .replace(R.id.fragment, fragment)
    .commit()
})
