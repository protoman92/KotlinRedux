/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.core

import java.util.UUID

/** Created by viethai.pham on 2019/02/21 */
/** Provide a unique subscriber ID for [IReduxStore.subscribe]. */
interface IUniqueIDProvider {
  /** The unique ID to pass to [IReduxStore.subscribe] and [IReduxStore.unsubscribe]. */
  val uniqueID: String
}

/** Default implementation of [IUniqueIDProvider] that simply uses [UUID.randomUUID]. */
class DefaultUniqueIDProvider : IUniqueIDProvider {
  override val uniqueID: String = UUID.randomUUID().toString()
}
