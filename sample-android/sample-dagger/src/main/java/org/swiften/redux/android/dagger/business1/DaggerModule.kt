/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.android.dagger.business1

import com.beust.klaxon.Klaxon
import dagger.Module
import dagger.Provides
import org.swiften.redux.android.dagger.DependencyLevel1
import org.swiften.redux.android.dagger.DependencyLevel2

/** Created by viethai.pham on 2019/02/23 */
@Module
class APIModule {
  @Provides
  @Business1Scope
  fun searchApi(): ISearchAPI<String> {
    return API()
  }
}

@Module
class RepositoryModule {
  @Provides
  @Business1Scope
  fun jsonDecoder(): Klaxon {
    return Klaxon()
  }

  @Provides
  @Business1Scope
  fun repository(decoder: Klaxon, api: ISearchAPI<String>): Repository {
    return Repository(decoder, api)
  }

  @Provides
  @Business1Scope
  fun searchRepository(repository: Repository): ISearchRepository {
    return repository
  }
}

@Module
class Parent1Module {
  @Provides
  @Business1Scope
  fun dependencyLevel1(sd: DependencyLevel2): DependencyLevel1 {
    return DependencyLevel1(ParentFragment1::class.java.simpleName, sd)
  }
}

@Module
class Business1Module
