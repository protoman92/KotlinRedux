/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.android.sample

import android.net.Uri
import java.net.URL

/** Created by haipham on 27/1/19 */
interface ISearchAPI<Result> {
  fun searchMusicStore(query: String, limit: Int): Result
}

class API : ISearchAPI<String> {
  override fun searchMusicStore(query: String, limit: Int): String {
    val url = Uri.Builder()
      .scheme("https")
      .authority("itunes.apple.com")
      .appendPath("search")
      .appendQueryParameter("term", query)
      .appendQueryParameter("limit", "$limit")
      .appendQueryParameter("media", "music")
      .build()
      .toString()

    return URL(url).readText()
  }
}
