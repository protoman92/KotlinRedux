/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.android.dagger

import dagger.Component
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/** Created by viethai.pham on 2019/02/21 */
@Module
class DependencyLevel3Module {
  @Singleton
  @Provides
  fun dependencyLevel3(): DependencyLevel3 {
    return DependencyLevel3()
  }
}

@Module
class DependencyLevel2Module {
  @Singleton
  @Provides
  fun dependencyLevel2(sd: DependencyLevel3): DependencyLevel2 {
    return DependencyLevel2(sd)
  }
}

@Module
class Fragment1DependencyModule {
  @Singleton
  @Provides
  fun dependencyLevel1(sd: DependencyLevel2): DependencyLevel1 {
    return DependencyLevel1(sd)
  }
}

@Singleton
@Component(modules = [
  Fragment1DependencyModule::class,
  DependencyLevel2Module::class,
  DependencyLevel3Module::class
])
interface MainComponent {
  fun fragment1Dependency(): DependencyLevel1
}
