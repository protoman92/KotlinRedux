/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.store

import org.swiften.redux.core.IReduxReducer
import org.swiften.redux.core.IReduxStore
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

/** Created by haipham on 2019/01/15 */
/**
 * [FinalReduxStore] is a [IReduxStore] that combines all crucial [IReduxStore] implementations to
 * provide a full suite of functionalities.
 */
class FinalReduxStore<GlobalState> private constructor(
  store: IReduxStore<GlobalState>
) : IReduxStore<GlobalState> by store {
  constructor(
    state: GlobalState,
    reducer: IReduxReducer<GlobalState>,
    context: CoroutineContext = EmptyCoroutineContext
  ) : this(fun (): IReduxStore<GlobalState> {
    val rootStore = SimpleReduxStore(state, reducer)
    val defaultActionStore = DefaultActionReduxStore(rootStore)
    return AsyncReduxStore(defaultActionStore, context)
  }())
}
