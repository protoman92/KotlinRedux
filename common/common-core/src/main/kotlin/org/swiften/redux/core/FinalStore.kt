/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.core

/** Created by haipham on 2019/01/15 */
/**
 * [FinalStore] is a [IReduxStore] that combines all crucial [IReduxStore] implementations to
 * provide a full suite of functionalities.
 * @param GState The global state type.
 * @param store An [IReduxStore] instance.
 */
class FinalStore<GState : Any> private constructor(store: IReduxStore<GState>) :
  IReduxStore<GState> by store {
  constructor(state: GState, reducer: IReducer<GState, IReduxAction>) :
    this(fun (): IReduxStore<GState> {
      return DefaultActionStore(state, reducer)
    }())
}
