/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.android.dagger.dagger

import dagger.Module
import dagger.Provides
import dagger.Subcomponent
import org.swiften.redux.android.dagger.DependencyLevel1
import org.swiften.redux.android.dagger.DependencyLevel2
import org.swiften.redux.android.dagger.Fragment1Scope

/** Created by viethai.pham on 2019/02/21 */
@Module
class Fragment1DependencyModule {
  @Fragment1Scope
  @Provides
  fun dependencyLevel1(sd: DependencyLevel2): DependencyLevel1 {
    return DependencyLevel1(sd)
  }
}

@Fragment1Scope
@Subcomponent(modules = [Fragment1DependencyModule::class])
interface Fragment1Component {
  fun dependency(): DependencyLevel1
}
