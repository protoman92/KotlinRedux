/*
 * Copyright (c) haipham 2018. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.saga

import io.reactivex.Flowable
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import org.swiften.redux.core.Redux
import org.swiften.redux.core.ReduxDispatcher
import org.swiften.redux.core.ReduxStateGetter
import java.util.concurrent.TimeUnit

/** Created by haipham on 2018/12/22 */
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
  class Output<T> internal constructor(
    private val scope: CoroutineScope,
    internal val stream: Flowable<T>,
    val onAction: ReduxDispatcher
  ) : CoroutineScope by scope {
    internal var source: Output<*>? = null
    private var onDispose: () -> Unit = { }
    private val disposable by lazy { CompositeDisposable() }

    private fun <T2> with(newStream: Flowable<T2>): Output<T2> {
      val result = Output(this.scope, newStream, this.onAction)
      result.source = this
      result.onDispose = { this.dispose() }
      return result
    }

    /** Wrapper for [Flowable.map] */
    internal fun <T2> map(transform: (T) -> T2) =
      this.with(this.stream.map(transform))

    /** Wrapper for [Flowable.doOnNext] */
    internal fun doOnValue(perform: (T) -> Unit) =
      this.with(this.stream.doOnNext(perform))

    /** Wrapper for [Flowable.flatMap] */
    internal fun <T2> flatMap(transform: (T) -> Output<T2>) =
      this.with(this.stream.flatMap { transform(it).stream })

    /** Wrapper for [Flowable.switchMap] */
    internal fun <T2> switchMap(transform: (T) -> Output<T2>) =
      this.with(this.stream.switchMap { transform(it).stream })

    /** Wrapper for [Flowable.filter] */
    internal fun filter(selector: (T) -> Boolean) =
      this.with(this.stream.filter(selector))

    /** Wrapper for [Flowable.debounce] */
    internal fun debounce(timeMillis: Long): Output<T> {
      if (timeMillis <= 0) { return this }
      return this.with(this.stream.debounce(timeMillis, TimeUnit.MILLISECONDS))
    }

    /** Wrapper for [Flowable.onErrorReturn] */
    internal fun catchError(fallback: (Throwable) -> T) =
      this.with(this.stream.onErrorReturn(fallback))

    /** Terminate the current [stream] */
    fun dispose() { this.disposable.clear(); this.onDispose() }

    /** Get the next [T], but only if it arrives before [timeoutInMillis] */
    fun nextValue(timeoutInMillis: Long): T? {
      try {
        return this.stream
          .timeout(timeoutInMillis, TimeUnit.MILLISECONDS)
          .blockingFirst()
      } catch (e: Throwable) {
        return null
      }
    }

    fun subscribe(onValue: (T) -> Unit, onError: (Throwable) -> Unit = { }) {
      this.disposable.addAll(this.stream.subscribe(onValue, onError))
    }
  }

  /** Options for [TakeEffect] */
  data class TakeOptions(internal val debounceMillis: Long = 0)
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
