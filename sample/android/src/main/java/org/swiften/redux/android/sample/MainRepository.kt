/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.android.sample

/** Created by haipham on 2019/01/05 */
interface IMainRepository {
  fun searchMusicStore(input: String): MusicResult
}

class MainRepository(
  private val api: IMainApi,
  private val decoder: JSONDecoder
) : IMainRepository {
  override fun searchMusicStore(input: String): MusicResult {
    val result = this.api.searchMusicStore(input)
    val parsed = this.decoder.parse<MusicResult>(result)

    if (parsed != null) {
      return parsed
    } else {
      throw RuntimeException("Invalid JSON: $result")
    }
  }
}
