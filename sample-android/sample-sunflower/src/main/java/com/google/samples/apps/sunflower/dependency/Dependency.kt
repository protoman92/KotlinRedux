/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package com.google.samples.apps.sunflower.dependency

import com.google.samples.apps.sunflower.GardenFragment
import com.google.samples.apps.sunflower.PlantDetailFragment
import com.google.samples.apps.sunflower.PlantListFragment
import com.squareup.picasso.Picasso

/** Created by haipham on 2019/01/17 */
interface IPicassoProvider {
  val picasso: Picasso
}

interface IDependency :
  IPicassoProvider,
  GardenFragment.IDependency,
  PlantListFragment.IDependency,
  PlantDetailFragment.IDependency
