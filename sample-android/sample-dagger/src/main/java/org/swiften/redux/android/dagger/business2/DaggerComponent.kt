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
  @Parent2Scope
  @Provides
  fun dependencyLevel1(sd: DependencyLevel2): DependencyLevel1 {
    return DependencyLevel1(ParentFragment2::class.java.simpleName, sd)
  }
}

@Parent2Scope
@Subcomponent(modules = [Parent2Module::class])
interface Parent2Component {
  fun dependency(): DependencyLevel1
}

@Module
class Business2Module

@Business2Scope
@Subcomponent(modules = [Business2Module::class])
interface Business2Component {
  fun plus(module: Parent2Module): Parent2Component
}

