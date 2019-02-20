/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.android.sample

import java.io.Serializable

/** Created by haipham on 27/1/19 */
object Constants {
  const val MAIN_PAGE_SEARCH_INDEX = 0
  const val MAIN_PAGE_DETAIL_INDEX = 1
}

data class MusicTrack(
  val artistName: String,
  val currency: String,
  val previewUrl: String,
  val trackName: String,
  val trackPrice: Double,
  val trackTimeMillis: Int
) : Serializable

data class MusicResult(
  val resultCount: Int,
  val results: List<MusicTrack>
) : Serializable

enum class ResultLimit(val count: Int) : Serializable {
  FIVE(5),
  TEN(10),
  TWENTY(20)
}
