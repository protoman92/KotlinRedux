/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.android.dagger.business2

import dagger.Module
import dagger.Provides
import dagger.Subcomponent
import org.swiften.redux.android.dagger.DependencyLevel1
import org.swiften.redux.android.dagger.DependencyLevel2

/** Created by viethai.pham on 2019/02/21 */
@Module
class Fragment2Module {
  @Fragment2Scope
  @Provides
  fun dependencyLevel1(sd: DependencyLevel2): DependencyLevel1 {
    return DependencyLevel1(Fragment2::class.java.simpleName, sd)
  }
}

@Fragment2Scope
@Subcomponent(modules = [Fragment2Module::class])
interface Fragment2Component {
  fun dependency(): DependencyLevel1
}
