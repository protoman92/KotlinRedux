/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.android.dagger.business1

import android.net.Uri
import java.net.URL

/** Created by viethai.pham on 2019/02/23 */
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
