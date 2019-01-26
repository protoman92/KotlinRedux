/*
 * Copyright (c) haipham 2018. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.android.sample

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_camera.camera

/** Created by haipham on 2018/12/21 */
class CameraFragment : Fragment() {
  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? = inflater.inflate(R.layout.fragment_camera, container, false)

  override fun onStart() {
    super.onStart()
    this.camera.onStart()
  }

  override fun onResume() {
    super.onResume()
    this.camera.onResume()
  }

  override fun onPause() {
    this.camera.onPause()
    super.onPause()
  }

  override fun onStop() {
    this.camera.onStop()
    super.onStop()
  }

  override fun onRequestPermissionsResult(
    requestCode: Int,
    permissions: Array<String>,
    grantResults: IntArray
  ) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    this.camera.onRequestPermissionsResult(requestCode, permissions, grantResults)
  }
}
