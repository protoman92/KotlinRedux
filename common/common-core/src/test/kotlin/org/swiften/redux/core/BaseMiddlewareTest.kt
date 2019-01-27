/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.core

/** Created by haipham on 2018/01/27 */
/** Mock test class to provide some utilities */
open class BaseMiddlewareTest {
  fun <GlobalState> mockMiddlewareInput(state: GlobalState): MiddlewareInput<GlobalState> {
    return MiddlewareInput({ state }, { _, _ -> ReduxSubscription("mock") {} })
  }

  fun mockDispatchWrapper(dispatcher: IActionDispatcher = {}): DispatchWrapper {
    return DispatchWrapper(DispatchWrapper.ROOT_WRAPPER, dispatcher)
  }
}
