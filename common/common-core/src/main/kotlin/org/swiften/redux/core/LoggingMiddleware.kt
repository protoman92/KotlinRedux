/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.core

import java.util.UUID
import java.util.concurrent.locks.ReentrantReadWriteLock
import kotlin.concurrent.read
import kotlin.concurrent.write

/** Created by haipham on 2019/01/27 */
/**
 * [IMiddleware] implementation that supports logging. Specify [logger] to customize how events
 * are formatted.
 * @param GlobalState The global state type.
 * @param logger Function to specify how [GlobalState] and [IReduxAction] are formatted.
 */
internal class LoggingMiddleware<GlobalState>(
  private val logger: (GlobalState, IReduxAction?) -> Unit
) : IMiddleware<GlobalState> {
  override fun invoke(p1: MiddlewareInput<GlobalState>): DispatchMapper {
    return { wrapper ->
      val lock = ReentrantReadWriteLock()
      val subscriberId = "${this@LoggingMiddleware}${UUID.randomUUID()}"
      var lastAction: IReduxAction? = null

      val subscription = p1.subscriber(subscriberId) {
        lock.read { this@LoggingMiddleware.logger(it, lastAction) }
      }

      DispatchWrapper("${wrapper.id}-logging") {
        if (it == DefaultReduxAction.Deinitialize) {
          lock.write { subscription.unsubscribe() }
        }

        lock.write { lastAction = it }
        wrapper.dispatch(it)
      }
    }
  }
}

/**
 * Create a [LoggingMiddleware] with [logger].
 * @param GlobalState The global state type.
 * @param logger See [LoggingMiddleware.logger].
 * @return A [LoggingMiddleware] instance.
 */
fun <GlobalState> createLoggingMiddleware(logger: (GlobalState, IReduxAction?) -> Unit = { s, a ->
  println("Redux: Last action $a, Last state $s")
}): IMiddleware<GlobalState> = LoggingMiddleware(logger)
