/*
 * Copyright (c) haipham 2018. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.saga

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.produce
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

  /** [Output] for an [ReduxSagaEffect], which can stream values of some type */
  class Output<T> internal constructor(
    private val scope: CoroutineScope,
    private val _channel: ReceiveChannel<T>,
    val onAction: ReduxDispatcher
  ) : CoroutineScope by scope {
    /** Operator function for [Output.map] */
    interface IMapper<in T, out R> {
      suspend operator fun invoke(scope: CoroutineScope, value: T): R
    }

    /** Operator function for [Output.flatMap] and [Output.switchMap] */
    interface IFlatMapper<in T, R> {
      suspend operator fun invoke(scope: CoroutineScope, value: T): Output<R>
    }

    /** Operator function for [Output.catchError] */
    interface IErrorCatcher<out R> {
      suspend operator fun invoke(scope: CoroutineScope, error: Throwable): R
    }

    companion object {
      internal val DEFAULT_IDENTIFIER = "UnidentifiedOutput"
    }

    /** Use this to set [identifier] internally */
    internal constructor(
      identifier: String,
      scope: CoroutineScope,
      channel: ReceiveChannel<T>,
      onAction: ReduxDispatcher
    ) : this(scope, channel, onAction) {
      this.identifier = identifier
    }

    /** Use this to set [identifier] using [creator] */
    internal constructor(
      creator: Any,
      scope: CoroutineScope,
      channel: ReceiveChannel<T>,
      onAction: ReduxDispatcher
    ): this ("${creator.javaClass.simpleName}Output", scope, channel, onAction)

    /** This [identifier] may be useful for debugging purposes */
    internal var identifier = Output.DEFAULT_IDENTIFIER
    internal val channel get() = LifecycleReceiveChannel(this._channel)

    override fun toString() = this.identifier

    private fun <T2> with(newChannel: ReceiveChannel<T2>) =
      Output(this.scope, newChannel, this.onAction)

    /** Map emissions from [channel] with [transform] */
    @ExperimentalCoroutinesApi
    internal fun <T2> map(transform: IMapper<T, T2>) =
      this.with(this.produce {
        this.invokeOnClose { println("Closed") }

        try {
          for (value in this@Output.channel) {
            if (!this.isActive || this.isClosedForSend) { break }
            this.send(transform(this, value))
          }
        } catch (e: Throwable) { this.close(e) }
      })

    /**
     * Flatten emissions from other [Output] produced by transforming the
     * elements emitted by [channel] to said [Output], and emitting everything.
     */
    @ExperimentalCoroutinesApi
    internal fun <T2> flatMap(transform: IFlatMapper<T, T2>) =
      this.with(this.produce {
        try {
          for (value in this@Output.channel) {
            val output2 = transform(this, value)
            val parentJob = SupervisorJob()

            this.launch(parentJob) {
              for (t2 in output2.channel) {
                if (!this.isActive || this@produce.isClosedForSend) { break }
                this@produce.send(t2)
              }

              if (!this.isActive) { output2.terminate() }
            }
          }
        } catch (e: Throwable) { this.close(e) }
      })

    /**
     * Flatten emissions from the last [Output] produced by transforming the
     * latest element emitted by [channel]. Contrast this with [Output.flatMap].
     */
    @ExperimentalCoroutinesApi
    internal fun <T2> switchMap(transform: IFlatMapper<T, T2>) =
      this.with(this.scope.produce {
        var previousJob: Job? = null

        try {
          for (value in this@Output.channel) {
            previousJob?.cancelChildren()
            val parentJob = SupervisorJob()
            val output2 = transform(this, value)

            val newJob = this.launch(parentJob, CoroutineStart.LAZY) {
              for (t2 in output2.channel) {
                if (!this.isActive || this@produce.isClosedForSend) { break }
                this@produce.send(t2)
              }

              if (!this.isActive) { output2.terminate() }
            }

            previousJob = parentJob
            newJob.start()
          }
        } catch (e: Throwable) { this.close(e) }
      })

    /** Throttle emissions with [timeMillis] */
    @ExperimentalCoroutinesApi
    internal fun debounce(timeMillis: Long): Output<T> {
      if (timeMillis <= 0) { return this }

      return this.with(this.produce {
        var previousJob: Job? = null

        for (value in this@Output.channel) {
          val startTime = Date().time
          val parentJob = SupervisorJob()

          val newJob = this.launch(parentJob, CoroutineStart.LAZY) {
            while (Date().time - startTime < timeMillis && this.isActive) { }
            if (this.isActive) { this@produce.send(value) }
          }

          previousJob?.cancelChildren()
          previousJob = parentJob
          newJob.start()
        }
      })
    }

    /** Catch possible errors and return a value produced by [fallback] */
    @ExperimentalCoroutinesApi
    internal fun catchError(fallback: IErrorCatcher<T>) =
      this.with(this.produce {
        try { for (value in this@Output.channel) { this@produce.send(value) } }
        catch (e1: Throwable) {
          try { this.send(fallback(this, e1)); this.close() }
          catch (e2: Throwable) { this.close(e2) }
        }
      })

    /** Catch possible errors and return [fallback] value */
    @ExperimentalCoroutinesApi
    internal fun catchError(fallback: T) =
      this.catchError(object : IErrorCatcher<T> {
        override suspend operator fun invoke(
          scope: CoroutineScope,
          error: Throwable
        ) = fallback
    })

    /** Terminate the current [channel] */
    fun terminate() = this.channel.cancel()

    /** Get the next [T], but only if it arrives before [timeoutInMillis] */
    fun nextValue(timeoutInMillis: Long) = runBlocking(this.coroutineContext) {
      withTimeoutOrNull(timeoutInMillis) { this@Output.channel.receive() }
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
