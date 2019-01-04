/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.android.sample

import com.beust.klaxon.Klaxon

/** Created by haipham on 2019/01/05 */
interface IMainRepository {
  fun searchMusicStore(input: String): MusicResult?
}

class MainRepository(
  private val klx: Klaxon,
  private val api: IMainApi) : IMainRepository
{
  override fun searchMusicStore(input: String): MusicResult? {
    val result = this.api.searchMusicStore(input)
    return this.klx.parse<MusicResult>(result)
  }
}
