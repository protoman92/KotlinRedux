/*
 * Copyright (c) haipham 2018. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.saga

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.ReceiveChannel
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
  class Output<out T> internal constructor(
    private val scope: CoroutineScope,
    internal val channel: ReceiveChannel<T>,
    val onAction: Redux.IDispatcher
  ): CoroutineScope by scope {
    /** Operator function for [Output.map] */
    interface IMapper<in T, out R> {
      suspend operator fun invoke(scope: CoroutineScope, value: T): R
    }

    /** Operator function for [Output.flatMap] and [Output.switchmap] */
    interface IFlatMapper<in T, R> {
      suspend operator fun invoke(scope: CoroutineScope, value: T): Output<R>
    }

    internal constructor(
      scope: CoroutineScope,
      channel: ReceiveChannel<T>,
      onAction: (Redux.IAction) -> Unit
    ) : this(scope, channel, object : Redux.IDispatcher {
      override operator fun invoke(action: Redux.IAction) = onAction(action)
    })

    private fun <T2> with(newChannel: ReceiveChannel<T2>) =
      Output(this.scope, newChannel, this.onAction)

    /** Map emissions from [channel] with [transform] */
    @ExperimentalCoroutinesApi
    internal fun <T2> map(transform: IMapper<T, T2>) =
      this.with(this.produce {
        for (value1 in this@Output.channel) {
          if (!this.isActive || this.isClosedForSend) { break }
          this.send(transform(this, value1))
        }

        if (!this.isActive) { this.close() }
      })

    /**
     * Flatten emissions from other [Output] produced by transforming the
     * elements emitted by [channel] to said [Output], and emitting everything.
     */
    @ExperimentalCoroutinesApi
    internal fun <T2> flatMap(transform: IFlatMapper<T, T2>) =
      this.with(this.produce {
        for (value1 in this@Output.channel) {
          val parentJob = SupervisorJob()
          val channel2 = transform(this, value1).channel

          this.launch(parentJob) {
            for (t2 in channel2) {
              if (!this.isActive || this@produce.isClosedForSend) { break }
              this@produce.send(t2)
            }

            if (!this.isActive) { this@produce.close() }
          }
        }
      })

    /**
     * Flatten emissions from the last [Output] produced by transforming the
     * latest element emitted by [channel]. Contrast this with [Output.flatMap].
     */
    @ExperimentalCoroutinesApi
    internal fun <T2> switchMap(transform: IFlatMapper<T, T2>) =
      this.with(this.scope.produce {
        var previousJob: Job? = null

        for (value1 in this@Output.channel) {
          val parentJob = SupervisorJob()
          val channel2 = transform(this, value1).channel

          val newJob = this.launch(parentJob, CoroutineStart.LAZY) {
            for (t2 in channel2) {
              if (!this.isActive || this@produce.isClosedForSend) { break }
              this@produce.send(t2)
            }

            if (!this.isActive) { this@produce.close() }
          }

          previousJob?.cancelChildren()
          previousJob = parentJob
          newJob.start()
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
  interface IEffect<State, out R> {
    /** Produce an [Output] with an [Input] */
    operator fun invoke(input: Input<State>): Output<R>
  }

  /** Transform one [IEffect] to another */
  interface ITransformer<State, R, R2> {
    operator fun invoke(effect: IEffect<State, R>): IEffect<State, R2>
  }
}

/**
 * Convenience method to call [ReduxSaga.IEffect.invoke] with convenience
 * parameters for testing.
 */
fun <State, R> ReduxSaga.IEffect<State, R>.invoke(
  scope: CoroutineScope,
  state: State,
  dispatch: (Redux.IAction) -> Unit
) = this.invoke(ReduxSaga.Input(
  scope,
  object : Redux.ILastState<State> { override operator fun invoke() = state },
  object : Redux.IDispatcher {
    override operator fun invoke(action: Redux.IAction) = dispatch(action)
  }))

/** Transform the current [ReduxSaga.IEffect] to another, based on
 * [transformer] */
fun <State, R, R2> ReduxSaga.IEffect<State, R>.transform(
  transformer: ReduxSaga.ITransformer<State, R, R2>
) = transformer(this)
