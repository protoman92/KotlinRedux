/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.saga.cr

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.channels.filter
import kotlinx.coroutines.channels.map
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.channels.toChannel
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withTimeoutOrNull
import org.swiften.redux.core.IReduxDispatcher
import org.swiften.redux.saga.IReduxSagaOutput
import java.util.Date

/** Created by haipham on 2018/12/22 */
/** @see [IReduxSagaOutput] */
@Suppress("EXPERIMENTAL_API_USAGE")
class ReduxSagaOutput<T> internal constructor(
  private val identifier: String = DEFAULT_IDENTIFIER,
  private val scope: CoroutineScope,
  internal val channel: ReceiveChannel<T>,
  override val onAction: IReduxDispatcher
) : IReduxSagaOutput<T>, CoroutineScope by scope {
  companion object { internal const val DEFAULT_IDENTIFIER = "Unidentified" }

  /** Use this to set [identifier] using [creator] */
  internal constructor(
    creator: Any,
    scope: CoroutineScope,
    channel: ReceiveChannel<T>,
    onAction: IReduxDispatcher
  ): this (creator.javaClass.simpleName, scope, channel, onAction)

  internal var source: ReduxSagaOutput<*>? = null
  private var onDispose: () -> Unit = { }

  override fun toString(): String {
    val identifiers = arrayListOf<String>()
    var current: ReduxSagaOutput<*>? = this

    while (current != null) {
      identifiers.add(current.identifier)
      current = current.source
    }

    return identifiers.reversed().joinToString("-")
  }

  private fun <T2> with(
    identifier: String = this.identifier,
    newChannel: ReceiveChannel<T2>
  ): ReduxSagaOutput<T2>
  {
    val result = ReduxSagaOutput(
      identifier,
      this.scope,
      newChannel,
      this.onAction
    )
    result.source = this
    result.onDispose = { this.dispose() }
    return result
  }

  /** Add debugging caps to [channel] by injecting lifecycle observers */
  private fun <T2> debugChannel(channel: ReceiveChannel<T2>) = when (channel) {
    is Channel<T2> -> LifecycleChannel(this.identifier, channel)
    else -> LifecycleReceiveChannel(this.identifier, channel)
  }

  @ExperimentalCoroutinesApi
  override fun <T2> map(transform: (T) -> T2) = this.with("Map", this.produce<T2> {
    this@ReduxSagaOutput.channel
      .map(this.coroutineContext) { transform(it) }
      .toChannel(this)
  })

  @ExperimentalCoroutinesApi
  override fun <T2 : Any> mapSuspend(transform: suspend CoroutineScope.(T) -> T2) =
    this.with("Map", this.produce<T2> {
      this@ReduxSagaOutput.channel
        .map(this.coroutineContext) { transform(this, it) }
        .toChannel(this)
    })

  override fun <T2 : Any> mapAsync(transform: suspend CoroutineScope.(T) -> Deferred<T2>) =
    this.with("MapAsync", this.produce {
      this@ReduxSagaOutput.channel
        .map(this.coroutineContext) { transform(this, it) }
        .consumeEach { this.launch { this@produce.send(it.await()) } }
    })

  override fun doOnValue(perform: (T) -> Unit) =
    this.with("DoOnValue", this.produce {
      this@ReduxSagaOutput.channel.consumeEach { this.send(it); perform(it) }
    })

  @ExperimentalCoroutinesApi
  override fun <T2> flatMap(transform: (T) -> IReduxSagaOutput<T2>) =
    this.with("FlatMap", this.produce<T2> {
      this@ReduxSagaOutput.channel.consumeEach {
        this.launch { (transform(it) as ReduxSagaOutput<T2>).channel.toChannel(this@produce) }
      }
    })

  @ExperimentalCoroutinesApi
  override fun <T2> switchMap(transform: (T) -> IReduxSagaOutput<T2>) =
    this.with("SwitchMap", this.produce<T2> {
      var previousJob: Job? = null

      this@ReduxSagaOutput.channel.consumeEach {
        previousJob?.cancel()

        previousJob = this.launch {
          (transform(it) as ReduxSagaOutput<T2>).channel.toChannel(this@produce)
        }
      }
    })

  override fun filter(predicate: (T) -> Boolean) = this.with("Filter",
    this.channel.filter(this.coroutineContext) { predicate(it) })

  @ExperimentalCoroutinesApi
  override fun debounce(timeMillis: Long): ReduxSagaOutput<T> {
    if (timeMillis <= 0) { return this }

    return this.with("Debounce", this.produce {
      var previousJob: Job? = null

      this@ReduxSagaOutput.channel.consumeEach {
        val startTime = Date().time
        previousJob?.cancel()

        previousJob = this.launch {
          while ((Date().time - startTime) < timeMillis && this.isActive) { }
          if (this.isActive) { this@produce.send(it) }
        }
      }
    })
  }

  override fun delay(delayMillis: Long) = this.with("Delay", this.produce {
    this@ReduxSagaOutput.channel.consumeEach { delay(delayMillis); this@produce.send(it) }
  })

  @ExperimentalCoroutinesApi
  override fun catchError(catcher: (Throwable) -> T) = this.with("CatchError", this.produce {
    try { this@ReduxSagaOutput.channel.toChannel(this) } catch (e1: Throwable) {
      try { this.send(catcher(e1)); this.close() } catch (e2: Throwable) { this.close(e2) }
    }
  })

  override fun dispose() { this.channel.cancel(); this.onDispose() }

  override fun nextValue(timeoutMillis: Long) = runBlocking(this.coroutineContext) {
    withTimeoutOrNull(timeoutMillis) { this@ReduxSagaOutput.channel.receive() }
  }

  override fun subscribe(onValue: (T) -> Unit, onError: (Throwable) -> Unit) {
    this.launch {
      try { this@ReduxSagaOutput.channel.consumeEach(onValue) } catch (e: Throwable) { onError(e) }
    }
  }
}

/** Options for [TakeEffect] */
data class TakeEffectOptions(internal val debounceMillis: Long = 0)
