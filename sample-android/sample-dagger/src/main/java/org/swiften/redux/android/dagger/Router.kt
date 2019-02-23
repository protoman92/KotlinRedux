/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.android.dagger

import android.app.Application
import androidx.fragment.app.Fragment
import org.swiften.redux.android.dagger.business1.Business1HostFragment
import org.swiften.redux.android.dagger.business2.Business2HostFragment
import org.swiften.redux.android.dagger.business3.Business3HostFragment
import org.swiften.redux.android.router.createSingleActivityRouter
import org.swiften.redux.core.IRouter

/** Created by viethai.pham on 2019/02/21 */
class Router(application: Application) : IRouter<Redux.Screen>
by createSingleActivityRouter<MainActivity, Redux.Screen>(application, { a, s ->
  val fragment: Fragment = when (s) {
    is Redux.Screen.Business1 -> Business1HostFragment()
    is Redux.Screen.Business2 -> Business2HostFragment()
    is Redux.Screen.Business3 -> Business3HostFragment()
  }

  a.supportFragmentManager.beginTransaction()
    .replace(R.id.fragment, fragment)
    .commit()
})
