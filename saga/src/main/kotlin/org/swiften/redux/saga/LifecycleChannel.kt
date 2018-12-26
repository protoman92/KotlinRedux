/*
 * Copyright (c) haipham 2018. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.saga

import kotlinx.coroutines.channels.Channel

/**
 * Created by haipham on 2018/12/26.
 */
/** Use this [Channel] to track [channel]'s lifecycle */
class LifecycleChannel<E, C: Channel<E>>(
  private val channel: C,
  private val onClose: (Throwable?) -> Unit = { println("Closed with $it") }
) : Channel<E> by channel {
  override fun close(cause: Throwable?): Boolean {
    val successful = this.channel.close(cause)
    if (successful) { this.onClose(cause) }
    return successful
  }
}
