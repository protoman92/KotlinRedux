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
import java.util.*

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
    internal val channel: ReceiveChannel<T>,
    val onAction: Redux.IDispatcher
  ): CoroutineScope by scope {
    private fun <T2> with(newChannel: ReceiveChannel<T2>) =
      Output(this.scope, newChannel, this.onAction)

    /** See [ReceiveChannel.map] */
    internal fun <T2> map(transform: suspend (T) -> T2) =
      this.with(this.channel.map(this.coroutineContext) { transform(it) })

    /**
     * Flatten emissions from other [Output] produced by transforming the
     * elements emitted by [channel] to said [Output], and emitting everything.
     */
    @ExperimentalCoroutinesApi
    internal fun <T2> flatMap(transform: suspend (T) -> Output<T2>) =
      this.with(this.scope.produce {
        for (value1 in this@Output.channel) {
          val channel2 = transform(value1).channel
          this.launch { while (this.isActive) { }; channel2.cancel() }

          this.launch {
            for (t2 in channel2) {
              if (this@produce.isClosedForSend) break
              this@produce.send(t2)
            }
          }
        }
      })

    /**
     * Flatten emissions from the last [Output] produced by transforming the
     * latest element emitted by [channel]. Contrast this with [Output.flatMap].
     */
    @ExperimentalCoroutinesApi
    internal fun <T2> switchMap(transform: suspend (T) -> Output<T2>) =
      this.with(this.scope.produce {
        var previousJob: Job? = null

        for (value1 in this@Output.channel) {
          val parentJob = SupervisorJob()
          val channel2 = transform(value1).channel

          val job1 = this.launch(parentJob, CoroutineStart.LAZY) {
            while (this.isActive) { }; channel2.cancel()
          }

          val job2 = this.launch(parentJob, CoroutineStart.LAZY) {
            for (t2 in channel2) {
              if (this@produce.isClosedForSend) break
              this@produce.send(t2)
            }
          }

          previousJob?.cancelChildren()
          previousJob = parentJob
          job1.start(); job2.start()
        }
      })

    @ExperimentalCoroutinesApi
    internal fun debounce(timeMillis: Long): Output<T> {
      return this.with(this.produce {
        var previousJob: Job? = null

        for (value1 in this@Output.channel) {
          val startTime = Date().time
          val parentJob = SupervisorJob()

          val newJob = this.launch(parentJob, CoroutineStart.LAZY) {
            while (Date().time - startTime < timeMillis && this.isActive) { }
            if (this.isActive) { this@produce.send(value1) }
          }

          previousJob?.cancelChildren()
          previousJob = parentJob
          newJob.start()
        }
      })
    }

    /** Terminate the current [channel] */
    fun terminate() = this.channel.cancel()

    /** Get the next [T], but only if it arrives before [timeoutInMillis] */
    fun nextValue(timeoutInMillis: Long) = runBlocking(this.coroutineContext) {
      withTimeoutOrNull(timeoutInMillis) { this@Output.channel.receive() }
    }
  }

  /** Abstraction for Redux saga that handles [Redux.IAction] in the pipeline */
  interface IEffect<State, R> {
    /** Produce an [Output] with an [Input] */
    operator fun invoke(input: Input<State>): Output<R>
  }
}
