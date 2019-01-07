/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.android.sample

import java.net.URL

/** Created by haipham on 2019/01/05 */
interface IMainApi {
  fun searchMusicStore(input: String): String
}

class MainApi : IMainApi {
  override fun searchMusicStore(input: String): String {
    val url = "https://itunes.apple.com/search?term=$input&limit=5&media=music"
    return URL(url).readText()
  }
}
