/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package com.google.samples.apps.sunflower.dependency

import com.squareup.picasso.Picasso
import org.swiften.redux.ui.IPropInjector

/** Created by haipham on 2019/01/17 */
interface IPicassoProvider {
  val picasso: Picasso
}

interface IDependency : IPicassoProvider
