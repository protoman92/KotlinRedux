/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.android.dagger.business1

import dagger.Module
import dagger.Provides
import dagger.Subcomponent
import org.swiften.redux.android.dagger.DependencyLevel1
import org.swiften.redux.android.dagger.DependencyLevel2

/** Created by viethai.pham on 2019/02/21 */
@Module
class Parent1Module {
  @Parent1Scope
  @Provides
  fun dependencyLevel1(sd: DependencyLevel2): DependencyLevel1 {
    return DependencyLevel1(ParentFragment1::class.java.simpleName, sd)
  }
}

@Parent1Scope
@Subcomponent(modules = [Parent1Module::class])
interface Parent1Component {
  fun dependency(): DependencyLevel1
}

@Module
class Business1Module

@Business1Scope
@Subcomponent(modules = [Business1Module::class])
interface Business1Component {
  fun plus(module: Parent1Module): Parent1Component
}