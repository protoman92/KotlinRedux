/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.android.sample

/** Created by haipham on 2019/01/05 */
interface IMainRepository {
  fun searchMusicStore(input: String): MusicResult?
  fun createFakeResult(): MusicResult?
}

class MainRepository(
  private val api: IMainApi,
  private val decoder: JSONDecoder
) : IMainRepository {
  override fun searchMusicStore(input: String): MusicResult? {
    val result = this.api.searchMusicStore(input)
    return this.decoder.parse<MusicResult>(result)
  }

  override fun createFakeResult() = MusicResult(5, arrayListOf(
    MusicTrack("Artist 1", "SGD", "Song 1", "Song 1", 1.2, 1000),
    MusicTrack("Artist 2", "SGD", "Song 2", "Song 2", 3.4, 2000),
    MusicTrack("Artist 3", "SGD", "Song 3", "Song 3", 5.6, 3000),
    MusicTrack("Artist 4", "SGD", "Song 4", "Song 4", 7.8, 4000),
    MusicTrack("Artist 5", "SGD", "Song 5", "Song 5", 9.1, 5000)
  ))
}
