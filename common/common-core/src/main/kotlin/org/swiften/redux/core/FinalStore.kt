/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.core

/** Created by haipham on 2019/01/15 */
/**
 * [FinalStore] is a [IReduxStore] that combines all crucial [IReduxStore] implementations to
 * provide a full suite of functionalities.
 * @param GlobalState The global state type.
 * @param store An [IReduxStore] instance.
 */
class FinalStore<GlobalState> private constructor(store: IReduxStore<GlobalState>) :
  IReduxStore<GlobalState> by store {
  constructor(state: GlobalState, reducer: IReducer<GlobalState>) :
    this(fun (): IReduxStore<GlobalState> {
      val rootStore = ThreadSafeStore(state, reducer)
      return DefaultActionStore(rootStore)
    }())
}
