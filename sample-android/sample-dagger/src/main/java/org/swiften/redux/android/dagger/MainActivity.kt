/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.android.dagger

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import org.swiften.redux.android.dagger.business1.ParentFragment1

/** Created by haipham on 26/1/19 */
class MainActivity : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    this.setContentView(R.layout.main_activity)
    this.supportActionBar?.hide()

    if (savedInstanceState == null) {
      this.supportFragmentManager
        .beginTransaction()
        .replace(R.id.fragment, ParentFragment1())
        .commit()
    }
  }
}
