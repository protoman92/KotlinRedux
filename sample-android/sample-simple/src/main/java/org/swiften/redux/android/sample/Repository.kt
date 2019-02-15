/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.android.sample

import com.beust.klaxon.Klaxon

/** Created by haipham on 27/1/19 */
class Repository(
  private val decoder: Klaxon,
  private val api: ISearchAPI<String>
) : ISearchAPI<MusicResult?> {
  override fun searchMusicStore(query: String, limit: Int): MusicResult? {
    return this.api.searchMusicStore(query, limit).let { this.decoder.parse<MusicResult>(it) }
  }
}
