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
class Parent2Module {
  @Business2Scope
  @Provides
  fun dependencyLevel1(sd: DependencyLevel2): DependencyLevel1 {
    return DependencyLevel1(ParentFragment2::class.java.simpleName, sd)
  }
}

@Business2Scope
@Subcomponent(modules = [Parent2Module::class])
interface Parent2Component {
  fun dependency(): DependencyLevel1
}
