/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.android.sample

/** Created by haipham on 2019/01/05 */
interface IMainRepository: IMainApi

class MainRepository(private val api: IMainApi) : IMainRepository {
  override fun searchMusicStore(input: String) = this.api.searchMusicStore(input)
}
