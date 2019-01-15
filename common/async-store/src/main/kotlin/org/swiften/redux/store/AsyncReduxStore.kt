/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.store

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.swiften.redux.core.IReduxDispatcher
import org.swiften.redux.core.IReduxStore
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

/** Created by haipham on 2018/12/16 */
/**
 * [AsyncReduxStore] is a [IReduxStore] that calls [IReduxStore.dispatch] on other threads. This
 * class wraps a base [store] and assumes that [store] has thread-safe [State] accesses/updates.
 */
class AsyncReduxStore<State>(
  private val store: IReduxStore<State>,
  private val context: CoroutineContext = EmptyCoroutineContext
) : IReduxStore<State> by store {
  override val dispatch: IReduxDispatcher = { action ->
    GlobalScope.launch(this.context) { this@AsyncReduxStore.store.dispatch(action) }
  }
}
