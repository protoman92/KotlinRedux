/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.android.dagger

import dagger.Component
import dagger.Module
import dagger.Provides
import org.swiften.redux.android.dagger.business1.Business1Component
import org.swiften.redux.android.dagger.business1.Business1Module
import org.swiften.redux.android.dagger.business1.Business1SagaComponentProvider
import org.swiften.redux.android.dagger.business2.Business2Component
import org.swiften.redux.android.dagger.business2.Business2Module
import org.swiften.redux.android.dagger.business3.Business3Component
import org.swiften.redux.android.dagger.business3.Business3Module
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

@Singleton
@Component(modules = [
  DependencyLevel2Module::class,
  DependencyLevel3Module::class
])
interface MainComponent : Business1SagaComponentProvider {
  fun provide(module: Business1Module): Business1Component
  fun provide(module: Business2Module): Business2Component
  fun provide(module: Business3Module): Business3Component
}
