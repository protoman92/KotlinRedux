/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.android.sample

import android.app.Application
import android.content.Intent
import android.net.Uri
import androidx.fragment.app.Fragment
import org.swiften.redux.android.router.createSingleActivityRouter
import org.swiften.redux.core.IRouter

/** Created by viethai.pham on 2019/02/04 */
class MainRouter(application: Application) :
  IRouter<MainRedux.Screen> by
  createSingleActivityRouter<MainActivity, MainRedux.Screen>(application, { activity, screen ->
    val f: Fragment? = when (screen) {
      is MainRedux.Screen.MusicDetail -> MusicDetailFragment()

      is MainRedux.Screen.WebView -> {
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(screen.url))
        activity.startActivity(browserIntent)
        null
      }
    }

    f?.also {
      activity.supportFragmentManager
        .beginTransaction()
        .add(R.id.fragment, it, it.javaClass.canonicalName)
        .addToBackStack(null)
        .commitAllowingStateLoss()
    }
  })
