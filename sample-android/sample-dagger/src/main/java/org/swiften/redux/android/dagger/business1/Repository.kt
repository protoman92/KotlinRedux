/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.android.dagger.business1

import com.beust.klaxon.Klaxon

/** Created by viethai.pham on 2019/02/23 */
interface ISearchRepository : ISearchAPI<MusicResult?>

interface ISearchRepositoryProvider {
  fun searchRepository(): ISearchRepository
}

class Repository(
  private val decoder: Klaxon,
  private val api: ISearchAPI<String>
) : ISearchRepository {
  override fun searchMusicStore(query: String, limit: Int): MusicResult? {
    return this.api.searchMusicStore(query, limit).let { this.decoder.parse<MusicResult>(it) }
  }
}
