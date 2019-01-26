/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.android.sample

import android.net.Uri
import java.net.URL

/** Created by haipham on 2019/01/05 */
interface IMainApi {
  fun searchMusicStore(input: String): String
}

class MainApi : IMainApi {
  override fun searchMusicStore(input: String): String {
    val url = Uri.Builder()
      .scheme("https")
      .authority("itunes.apple.com")
      .appendPath("search")
      .appendQueryParameter("term", input)
      .appendQueryParameter("limit", "5")
      .appendQueryParameter("media", "music")
      .build()
      .toString()

    return URL(url).readText()
  }
}
