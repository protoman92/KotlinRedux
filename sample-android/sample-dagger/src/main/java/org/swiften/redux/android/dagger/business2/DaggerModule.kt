/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.android.dagger.business2

import dagger.Module
import dagger.Provides
import org.swiften.redux.android.dagger.DependencyLevel1
import org.swiften.redux.android.dagger.DependencyLevel2

/** Created by viethai.pham on 2019/02/23 */
@Module
class Business2HostModule {
  @Provides
  @Business2Scope
  fun dependencyLevel1(sd: DependencyLevel2): DependencyLevel1 {
    return DependencyLevel1(Business2HostFragment::class.java.simpleName, sd)
  }
}

@Module
class Business2Module
