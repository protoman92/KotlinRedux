/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.android.sample

import com.beust.klaxon.Klaxon

/** Created by haipham on 2019/01/05 */
class JSONDecoder {
  inline fun <reified T> parse(value: String) = Klaxon().parse<T>(value)
}
