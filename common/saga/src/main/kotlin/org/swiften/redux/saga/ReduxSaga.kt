/*
 * Copyright (c) haipham 2018. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.saga

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.*
import org.swiften.redux.core.Redux
import org.swiften.redux.core.ReduxDispatcher
import org.swiften.redux.core.ReduxStateGetter
import java.util.*

/**
 * Created by haipham on 2018/12/22.
 */
/** Abstraction for Redux saga that handles [Redux.IAction] in the pipeline */
typealias ReduxSagaEffect<State, R> = Function1<ReduxSaga.Input<State>, ReduxSaga.Output<R>>

/** Top-level namespace for Redux saga */
object ReduxSaga {
  /**
   * [Input] for an [ReduxSagaEffect], which exposes a [Redux.IStore]'s internal
   * functionalities.
   */
  class Input<State>(
    val scope: CoroutineScope = GlobalScope,
    val stateGetter: ReduxStateGetter<State>,
    val dispatch: ReduxDispatcher
  )

  /**
   * [Output] for an [ReduxSagaEffect], which can stream values of some type.
   * Use [identifier] for debugging purposes.
   */
  @Suppress("EXPERIMENTAL_API_USAGE")
  class Output<T> internal constructor(
    internal val identifier: String = Output.DEFAULT_IDENTIFIER,
    private val scope: CoroutineScope,
    internal val channel: ReceiveChannel<T>,
    val onAction: ReduxDispatcher
  ) : CoroutineScope by scope {
    companion object { internal const val DEFAULT_IDENTIFIER = "Unidentified" }

    /** Use this to set [identifier] using [creator] */
    internal constructor(
      creator: Any,
      scope: CoroutineScope,
      channel: ReceiveChannel<T>,
      onAction: ReduxDispatcher
    ): this (creator.javaClass.simpleName, scope, channel, onAction)

    internal var source: Output<*>? = null
    private var onDispose: () -> Unit = { }

    override fun toString() = this.identifier

    private fun <T2> with(identifier: String = this.identifier,
                          newChannel: ReceiveChannel<T2>): Output<T2> {
      val result = Output(identifier, this.scope, newChannel, this.onAction)
      result.source = this
      result.onDispose = { this.dispose() }
      return result
    }

    private fun newIdentifier(identifier: String, op: String) =
      "${this.identifier}-$op-$identifier"

    /** Add debugging caps to [channel] by injecting lifecycle observers */
    private fun <T2> debugChannel(channel: ReceiveChannel<T2>) = when(channel) {
      is Channel<T2> -> LifecycleChannel(this.identifier, channel)
      else -> LifecycleReceiveChannel(this.identifier, channel)
    }

    /** Map emissions from [channel] with [transform] */
    @ExperimentalCoroutinesApi
    internal fun <T2> map(identifier: String = this.identifier,
                          transform: suspend CoroutineScope.(T) -> T2) =
      this.with(this.newIdentifier(identifier, "map"), this.produce<T2> {
        this@Output.channel
          .map(this.coroutineContext) { transform(this, it) }
          .toChannel(this)
      })

    /** Append [identifier] with [creator] */
    @ExperimentalCoroutinesApi
    internal fun <T2> map(creator: Any, transform: suspend CoroutineScope.(T) -> T2)
      = this.map(creator.javaClass.simpleName, transform)

    /**
     * Flatten emissions from other [Output] produced by transforming the
     * elements emitted by [channel] to said [Output], and emitting everything.
     */
    @ExperimentalCoroutinesApi
    internal fun <T2> flatMap(identifier: String = this.identifier,
                              transform: suspend CoroutineScope.(T) -> Output<T2>) =
      this.with(this.newIdentifier(identifier, "flatMap"), this.produce<T2> {
        this@Output.channel
          .map(this.coroutineContext) { transform(this, it) }
          .consumeEach { this.launch { it.channel.toChannel(this@produce) } }
      })

    /**
     * Flatten emissions from the last [Output] produced by transforming the
     * latest element emitted by [channel]. Contrast this with [Output.flatMap].
     */
    @ExperimentalCoroutinesApi
    internal fun <T2> switchMap(identifier: String = this.identifier,
                                transform: suspend CoroutineScope.(T) -> Output<T2>) =
      this.with(this.newIdentifier(identifier, "switchMap"), this.produce<T2> {
        var previousJob: Job? = null

        this@Output.channel
          .map(this.coroutineContext) { transform(this, it) }
          .consumeEach {
            previousJob?.cancel()
            previousJob = this.launch { it.channel.toChannel(this@produce) }
          }
      })

    /** Throttle emissions with [timeMillis] */
    @ExperimentalCoroutinesApi
    internal fun debounce(timeMillis: Long): Output<T> {
      if (timeMillis <= 0) { return this }

      return this.with(newChannel = this.produce {
        var previousJob: Job? = null

        this@Output.channel
          .consumeEach {
            val startTime = Date().time
            val parentJob = SupervisorJob()

            val newJob = this.launch(parentJob, CoroutineStart.LAZY) {
              while (Date().time - startTime < timeMillis && this.isActive) { }
              if (this.isActive) { this@produce.send(it) }
            }

            previousJob?.cancelChildren()
            previousJob = parentJob
            newJob.start()
          }
      })
    }

    /** Catch possible errors and return a value produced by [fallback] */
    @ExperimentalCoroutinesApi
    internal fun catchError(identifier: String = this.identifier,
                            fallback: suspend CoroutineScope.(Throwable) -> T) =
      this.with(identifier, this.produce {
        try { this@Output.channel.toChannel(this) }
        catch (e1: Throwable) {
          try { this.send(fallback(this, e1)); this.close() }
          catch (e2: Throwable) { this.close(e2) }
        }
      })

    /** Append [identifier] with [creator] */
    @ExperimentalCoroutinesApi
    internal fun catchError(creator: Any, fallback: suspend CoroutineScope.(Throwable) -> T) =
      this.catchError(creator.javaClass.simpleName, fallback)

    /** Terminate the current [channel] */
    fun dispose() { this.channel.cancel(); this.onDispose() }

    /** Get the next [T], but only if it arrives before [timeoutInMillis] */
    fun nextValue(timeoutInMillis: Long) = runBlocking(this.coroutineContext) {
      withTimeoutOrNull(timeoutInMillis) { this@Output.channel.receive() }
    }

    fun subscribe(onValue: (T) -> Unit, onError: (Throwable) -> Unit = { }) {
      this.launch {
        try { this@Output.channel.consumeEach(onValue) }
        catch (e: Throwable) { onError(e) }
      }
    }
  }
}

/**
 * Convenience method to call [ReduxSagaEffect] with convenience parameters
 * for testing.
 */
fun <State, R> ReduxSagaEffect<State, R>.invoke(
  scope: CoroutineScope,
  state: State,
  dispatch: ReduxDispatcher
) = this.invoke(ReduxSaga.Input(scope, { state }, dispatch))
