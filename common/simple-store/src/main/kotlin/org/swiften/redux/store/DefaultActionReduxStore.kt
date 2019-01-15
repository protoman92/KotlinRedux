/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.store

import org.swiften.redux.core.DefaultReduxAction
import org.swiften.redux.core.IReduxDeinitializer
import org.swiften.redux.core.IReduxReducer
import org.swiften.redux.core.IReduxStore
import org.swiften.redux.core.ReduxReducerWrapper

/** Created by haipham on 2019/01/15 */
/** A [IReduxStore] that handles [DefaultReduxAction] */
class DefaultActionReduxStore<State>(
  private val store: IReduxStore<State>
) : IReduxStore<State> by store {
  constructor(state: State, reducer: IReduxReducer<State>) : this(SimpleReduxStore(state, reducer))

  override val reducer = ReduxReducerWrapper(this.store.reducer)

  override val deinitialize: IReduxDeinitializer = {
    this.dispatch(DefaultReduxAction.Deinitialize)
    this.store.deinitialize()
  }
}
