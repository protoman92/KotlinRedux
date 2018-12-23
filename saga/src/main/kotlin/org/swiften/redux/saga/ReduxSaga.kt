/*
 * Copyright (c) haipham 2018. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.saga

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.map
import kotlinx.coroutines.channels.produce
import org.swiften.redux.core.Redux
import java.util.concurrent.locks.ReentrantReadWriteLock
import kotlin.concurrent.read
import kotlin.concurrent.write

/**
 * Created by haipham on 2018/12/22.
 */
/** Top-level namespace for Redux saga */
object ReduxSaga {
  /**
   * [Input] for an [IEffect], which exposes a [Redux.IStore]'s internal
   * functionalities.
   */
  class Input<State>(
    val scope: CoroutineScope = GlobalScope,
    val lastState: Redux.ILastState<State>,
    val dispatch: Redux.IDispatcher
  )

  /** [Output] for an [IEffect], which can stream values of some type */
  data class Output<T>(
    private val scope: CoroutineScope,
    private val channel: ReceiveChannel<T>,
    val onAction: Redux.IDispatcher
  ): CoroutineScope by scope {
    private fun <T2> with(newChannel: ReceiveChannel<T2>): Output<T2> {
      return Output(this.scope, newChannel, this.onAction)
    }

    /** See [ReceiveChannel.map] */
    internal fun <T2> map(selector: suspend (T) -> T2) =
      this.with(this.channel.map(this.coroutineContext) { selector(it) })

    @ExperimentalCoroutinesApi
    internal fun <T2> flatMap(selector: suspend (T) -> Output<T2>) =
      this.with(this.scope.produce {
        val value1 = this@Output.channel.receive()
        val channel2 = selector(value1).channel

        this.launch {
          while (this.isActive) {}
          this@produce.close()
          channel2.cancel()
        }

        this.launch {
          for (t2 in channel2) {
            if (this@produce.isClosedForSend) break
            this@produce.send(t2)
          }
        }
      })

    @ExperimentalCoroutinesApi
    internal fun <T2> switchMap(selector: suspend (T) -> Output<T2>): Output<T2> {
      val lock = ReentrantReadWriteLock()
      var latestJob1: Job? = null
      var latestJob2: Job? = null

      return this.with(this.scope.produce {
        val value1 = this@Output.channel.receive()
        val channel2 = selector(value1).channel
        lock.read { latestJob1?.cancel(); latestJob2?.cancel() }

        val job1 = this.launch {
          while (this.isActive) {}
          this@produce.close()
          channel2.cancel()
        }

        val job2 = this.launch {
          for (t2 in channel2) {
            if (this@produce.isClosedForSend) break
            this@produce.send(t2)
          }
        }

        lock.write { latestJob1 = job1; latestJob2 = job2 }
      })
    }
  }

  /** Abstraction for Redux saga that handles [Redux.IAction] in the pipeline */
  interface IEffect<State, R> {
    /** Produce an [Output] with an [Input] */
    operator fun invoke(input: Input<State>): Output<R>
  }
}
