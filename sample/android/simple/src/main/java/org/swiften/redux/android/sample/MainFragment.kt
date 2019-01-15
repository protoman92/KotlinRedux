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
import androidx.fragment.app.FragmentPagerAdapter
import kotlinx.android.synthetic.main.fragment_main.viewPager

/** Created by haipham on 2018/12/20 */
class MainFragment : Fragment() {
  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? = inflater.inflate(R.layout.fragment_main, container, false)

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    val fm = requireNotNull(this.fragmentManager)
    this.viewPager.offscreenPageLimit = 1

    this.viewPager.adapter = object : FragmentPagerAdapter(fm) {
      override fun getItem(position: Int): Fragment = when (position) {
        1 -> Fragment2()
        2 -> Fragment3()
        3 -> CameraFragment()
        else -> SearchFragment()
      }

      override fun getCount(): Int = 4
    }
  }
}
