/*
 * Copyright (c) haipham 2018. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.saga

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import org.swiften.redux.core.Redux
import org.swiften.redux.core.ReduxDispatcher
import org.swiften.redux.middleware.ReduxMiddleware

/**
 * Created by haipham on 2018/12/22.
 */
private typealias DispatchWrapper = ReduxMiddleware.DispatchWrapper

/** Top-level namespace for Redux Saga middleware */
object ReduxSagaMiddleware {
  /** [ReduxMiddleware.IProvider] implementation for Saga */
  class Provider<State>(
    private val effects: List<ReduxSaga.IEffect<State, Any>>
  ): ReduxMiddleware.IProvider<State>, CoroutineScope {
    private val job = SupervisorJob()

    override val coroutineContext = Dispatchers.Default + this.job

    override val middleware = object : ReduxMiddleware.IMiddleware<State> {
      override fun invoke(input: ReduxMiddleware.Input<State>) =
        object : ReduxMiddleware.IDispatchMapper {
        override fun invoke(wrapper: DispatchWrapper): DispatchWrapper {
          val scope = this@Provider
          val sgInput = ReduxSaga.Input(scope, input.lastState, wrapper.dispatch)
          val outputs = this@Provider.effects.map { it(sgInput) }

          return DispatchWrapper("${wrapper.id}-saga", object : ReduxDispatcher {
            override fun invoke(action: Redux.IAction) {
              wrapper.dispatch(action)
              outputs.forEach { it.onAction(action) }
            }
          })
        }
      }
    }
  }
}
