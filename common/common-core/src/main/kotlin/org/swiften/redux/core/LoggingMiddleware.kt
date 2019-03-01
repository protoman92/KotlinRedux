/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.core

import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.withLock

/** Created by haipham on 2019/01/27 */
/**
 * [IMiddleware] implementation that supports logging. Specify [logger] to customize how events
 * are formatted.
 * @param GState The global state type.
 * @param logger Function to specify how [GState] and [IReduxAction] are formatted.
 */
internal class LoggingMiddleware<GState>(
  private val logger: (GState, IReduxAction?) -> Unit
) : IMiddleware<GState>, IUniqueIDProvider by DefaultUniqueIDProvider() {
  override fun invoke(p1: MiddlewareInput<GState>): DispatchMapper {
    return { wrapper ->
      val lock = ReentrantLock()
      var lastAction: IReduxAction? = null

      val subscription = p1.subscriber(this@LoggingMiddleware.uniqueID) {
        lock.withLock { this@LoggingMiddleware.logger(it, lastAction) }
      }

      DispatchWrapper.wrap(wrapper, "logging") {
        if (it == DefaultReduxAction.Deinitialize) {
          lock.withLock { subscription.unsubscribe() }
        }

        lock.withLock { lastAction = it }
        wrapper.dispatch(it)
      }
    }
  }
}

/**
 * Create a [LoggingMiddleware] with [logger].
 * @param GState The global state type.
 * @param logger See [LoggingMiddleware.logger].
 * @return A [LoggingMiddleware] instance.
 */
fun <GState> createLoggingMiddleware(
  logger: (GState, IReduxAction?) -> Unit = { s, a -> println("Redux: action $a, state $s") }
): IMiddleware<GState> = LoggingMiddleware(logger)
