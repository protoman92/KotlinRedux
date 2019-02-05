/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.core

/** Created by haipham on 2018/01/27 */
/** Mock test class to provide some utilities */
open class BaseMiddlewareTest {
  fun <GState> mockMiddlewareInput(state: GState): MiddlewareInput<GState> {
    return MiddlewareInput({}, { state }, { _, _ -> ReduxSubscription.EMPTY })
  }

  fun mockDispatchWrapper(dispatcher: IActionDispatcher = {}): DispatchWrapper {
    return DispatchWrapper(DispatchWrapper.ROOT_WRAPPER, dispatcher)
  }
}
