/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.saga.cr

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.SendChannel

/** Created by haipham on 2018/12/26 */
/** Use this [SendChannel] to track [channel]'s lifecycle */
@ExperimentalCoroutinesApi
class LifecycleSendChannel<E>(
  private val identifier: String,
  private val channel: SendChannel<E>,
  private val onClose: (Throwable?) -> Unit = { println("Closed $identifier with $it") }
) : SendChannel<E> by channel {
  init { this.invokeOnClose(this.onClose) }

  override fun toString() = this.identifier

  override fun close(cause: Throwable?): Boolean {
    val successful = this.channel.close(cause)
    if (successful) { this.onClose(cause) }
    return successful
  }
}

/** Use this [ReceiveChannel] to track [channel]'s lifecycle */
class LifecycleReceiveChannel<E>(
  private val identifier: String,
  private val channel: ReceiveChannel<E>,
  private val onCancel: () -> Unit = { println("Cancelled $identifier") }
) : ReceiveChannel<E> by channel {
  override fun toString() = this.identifier
  override fun cancel() { this.onCancel(); this.channel.cancel() }
}

/** Track all lifecycles for both [SendChannel] and [ReceiveChannel] */
@ExperimentalCoroutinesApi
class LifecycleChannel<E>(
  private val sender: LifecycleSendChannel<E>,
  private val receiver: LifecycleReceiveChannel<E>
) : Channel<E>, SendChannel<E> by sender, ReceiveChannel<E> by receiver {
  constructor(
    identifier: String,
    channel: Channel<E>,
    onSendClose: (Throwable?) -> Unit,
    onReceiveCancel: () -> Unit
  ) : this(
    LifecycleSendChannel(identifier, channel, onSendClose),
    LifecycleReceiveChannel(identifier, channel, onReceiveCancel)
  )

  constructor(identifier: String, channel: Channel<E>) : this(
    LifecycleSendChannel(identifier, channel),
    LifecycleReceiveChannel(identifier, channel)
  )

  override fun toString() = "${this.sender}"
}
