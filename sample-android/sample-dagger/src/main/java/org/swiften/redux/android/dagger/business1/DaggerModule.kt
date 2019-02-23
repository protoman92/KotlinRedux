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
class Business1APIModule {
  @Provides
  @Business1Scope
  fun searchApi(): ISearchAPI<String> {
    return Business1API()
  }
}

@Module
class Business1RepositoryModule {
  @Provides
  @Business1Scope
  fun jsonDecoder(): Klaxon {
    return Klaxon()
  }

  @Provides
  @Business1Scope
  fun repository(decoder: Klaxon, api: ISearchAPI<String>): Business1Repository {
    return Business1Repository(decoder, api)
  }

  @Provides
  @Business1Scope
  fun searchRepository(repository: Business1Repository): ISearchRepository {
    return repository
  }
}

@Module
class Business1HostModule {
  @Provides
  @Business1Scope
  fun dependencyLevel1(sd: DependencyLevel2): DependencyLevel1 {
    return DependencyLevel1(Business1HostFragment::class.java.simpleName, sd)
  }
}

@Module
class Business1SagaModule

@Module
class Business1Module
