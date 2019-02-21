/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.android.dagger.business3

import dagger.Module
import dagger.Provides
import dagger.Subcomponent
import org.swiften.redux.android.dagger.DependencyLevel1
import org.swiften.redux.android.dagger.DependencyLevel2

/** Created by viethai.pham on 2019/02/21 */
@Module
class Parent3Module {
  @Parent3Scope
  @Provides
  fun dependencyLevel1(sd: DependencyLevel2): DependencyLevel1 {
    return DependencyLevel1(ParentFragment3::class.java.simpleName, sd)
  }
}

@Parent3Scope
@Subcomponent(modules = [Parent3Module::class])
interface Parent3Component {
  fun dependency(): DependencyLevel1
}

@Module
class Business3Module

@Business3Scope
@Subcomponent(modules = [Business3Module::class])
interface Business3Component {
  fun plus(module: Parent3Module): Parent3Component
}
