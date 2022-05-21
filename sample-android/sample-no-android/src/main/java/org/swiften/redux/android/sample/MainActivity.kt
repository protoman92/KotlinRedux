/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.android.sample

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import org.swiften.redux.core.DefaultUniqueIDProvider
import org.swiften.redux.core.IUniqueIDProvider

/** Created by haipham on 20/5/22 */
class MainActivity : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    this.setContentView(R.layout.main_activity)
    this.supportActionBar?.hide()

    /** Code to test :common:common-all integration */
    val uniqueIDProvider: IUniqueIDProvider = DefaultUniqueIDProvider()
    Log.i("NoAndroidSample", uniqueIDProvider.toString())
  }
}
