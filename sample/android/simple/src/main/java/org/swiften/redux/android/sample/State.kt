/*
 * Copyright (c) haipham 2018. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.android.sample

import java.io.Serializable

/** Created by haipham on 2018/12/19 */
data class State(
  val autocompleteQuery: String? = "",
  val musicResult: MusicResult? = MusicResult(
    resultCount = 3,
    results = arrayListOf(
      MusicTrack("Artist 1", "SGD", "", "Song 1", 1.0, 0),
      MusicTrack("Artist 2", "SGD", "", "Song 2", 2.0, 0),
      MusicTrack("Artist 3", "SGD", "", "Song 3", 3.0, 0)
    )
  ),
  val selectedResultIndex: Int? = null,
  val loadingMusic: Boolean? = null
) : Serializable {
  fun currentSelectedTrack() = this.selectedResultIndex?.let { index ->
    this.musicResult?.results?.elementAtOrNull(index)
  }
}
