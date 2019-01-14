/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.android.sample

import java.io.Serializable

/** Created by haipham on 2019/01/05 */
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
