/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.android.dagger.business1

import java.io.Serializable

/** Created by viethai.pham on 2019/02/23 */
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
