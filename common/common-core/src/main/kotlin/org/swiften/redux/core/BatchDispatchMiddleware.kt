/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.core

/** Created by viethai.pham on 2019/03/03 */
/**
 * An [IReduxAction] that contains multiple other [IReduxAction] instances that can be dispatched
 * individually.
 */
data class BatchAction(internal val actions: Collection<IReduxAction>) : IReduxAction {
  constructor(vararg actions: IReduxAction) : this(actions.toList())
  override fun toString(): String = "Batch action, containing ${this.actions.size} child actions"
}

/**
 * [IMiddleware] that detects if an action if a [BatchAction]. If it is, access [BatchAction.actions]
 * and dispatch them individually.
 */
class BatchDispatchMiddleware internal constructor() : IMiddleware<Any> {
  companion object {
    fun create(): IMiddleware<Any> = BatchDispatchMiddleware()
  }

  @Suppress("UNCHECKED_CAST")
  override fun invoke(p1: MiddlewareInput<Any>): DispatchMapper {
    return { wrapper ->
      DispatchWrapper.wrap(wrapper, "batch") { action ->
        val dispatchJob = wrapper.dispatch(action)

        when (action) {
          is BatchAction -> action.actions.map { p1.dispatch(it) }
        }

        dispatchJob
      }
    }
  }
}
