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
  ): CoroutineScope by scope {}

  /** Abstraction for Redux saga that handles [Redux.IAction] in the pipeline */
  interface IEffect<State, R> {
    /** Produce an [Output] with an [Input] */
    operator fun invoke(input: Input<State>): Output<R>
  }
}
