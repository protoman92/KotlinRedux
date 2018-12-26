/*
 * Copyright (c) haipham 2018. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.saga

import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.SendChannel

/**
 * Created by haipham on 2018/12/26.
 */
/** Use this [SendChannel] to track [channel]'s lifecycle */
class LifecycleSendChannel<E, C : SendChannel<E>>(
  private val channel: C,
  private val onClose: (Throwable?) -> Unit = { println("Closed with $it") }
) : SendChannel<E> by channel {
  override fun close(cause: Throwable?): Boolean {
    val successful = this.channel.close(cause)
    if (successful) { this.onClose(cause) }
    return successful
  }
}

/** Use this [ReceiveChannel] to track [channel]'s lifecycle */
class LifecycleReceiveChannel<E, C : ReceiveChannel<E>>(
  private val channel: C,
  private val onCancel: () -> Unit = { println("Cancelled") }
) : ReceiveChannel<E> by channel {
  override fun cancel() { this.onCancel(); this.channel.cancel() }
}
