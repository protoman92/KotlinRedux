/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.core

/** Created by haipham on 2019/01/15 */
/**
 * A [IReduxStore] that handles [DefaultReduxAction].
 * @param GlobalState The global state type.
 * @param store An [IReduxStore] instance.
 */
class DefaultActionStore<GlobalState>(private val store: IReduxStore<GlobalState>) :
  IReduxStore<GlobalState> by store {
  override val reducer = ReduxReducerWrapper(this.store.reducer)

  override val deinitialize: IDeinitializer = {
    this.dispatch(DefaultReduxAction.Deinitialize)
    this.store.deinitialize()
  }
}
