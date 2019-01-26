/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.store

import org.swiften.redux.core.IReducer
import org.swiften.redux.core.IReduxStore
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

/** Created by haipham on 2019/01/15 */
/**
 * [FinalStore] is a [IReduxStore] that combines all crucial [IReduxStore] implementations to
 * provide a full suite of functionalities.
 */
class FinalStore<GlobalState> private constructor(
  store: IReduxStore<GlobalState>
) : IReduxStore<GlobalState> by store {
  constructor(
    state: GlobalState,
    reducer: IReducer<GlobalState>,
    context: CoroutineContext = EmptyCoroutineContext
  ) : this(fun (): IReduxStore<GlobalState> {
    val rootStore = ThreadSafeStore(state, reducer)
    val defaultActionStore = DefaultActionStore(rootStore)
    return AsyncStore(defaultActionStore, context)
  }())
}