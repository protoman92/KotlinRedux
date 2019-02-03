/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.core

/** Created by haipham on 2019/01/15 */
/**
 * A [IReduxStore] that handles [DefaultReduxAction].
 * @param GState The global state type.
 * @param store An [IReduxStore] instance.
 */
class DefaultActionStore<GState>(private val store: IReduxStore<GState>) :
  IReduxStore<GState> by store {
  override val reducer = ReduxReducerWrapper(this.store.reducer)

  override val deinitialize: IDeinitializer = {
    this.dispatch(DefaultReduxAction.Deinitialize)
    this.store.deinitialize()
  }
}
