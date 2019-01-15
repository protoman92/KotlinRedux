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
class FinalReduxStore<State> private constructor(
  store: IReduxStore<State>
) : IReduxStore<State> by store {
  constructor(
    state: State,
    reducer: IReduxReducer<State>,
    context: CoroutineContext = EmptyCoroutineContext
  ) : this(fun (): IReduxStore<State> {
    val rootStore = SimpleReduxStore(state, reducer)
    val defaultActionStore = DefaultActionReduxStore(rootStore)
    return AsyncReduxStore(defaultActionStore, context)
  }())
}
