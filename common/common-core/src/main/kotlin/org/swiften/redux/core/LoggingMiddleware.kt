/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.core

import java.util.UUID

/** Created by haipham on 2019/01/27 */
internal class LoggingMiddleware<GlobalState>(private val logger: (GlobalState) -> Unit) :
  IMiddleware<GlobalState> {
  override fun invoke(p1: MiddlewareInput<GlobalState>): DispatchMapper {
    return { wrapper ->
      val subscriberId = "${this@LoggingMiddleware}${UUID.randomUUID()}"
      val subscription = p1.subscriber(subscriberId) { this@LoggingMiddleware.logger(it) }

      DispatchWrapper("${wrapper.id}-logging") {
        wrapper.dispatch(it)

        if (it == DefaultReduxAction.Deinitialize) {
          subscription.unsubscribe()
        }
      }
    }
  }
}

/** Create a [LoggingMiddleware] with [logger] */
fun <GlobalState> createLoggingMiddleware(logger: (GlobalState) -> Unit): IMiddleware<GlobalState>
  = LoggingMiddleware(logger)
