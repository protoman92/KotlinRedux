/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.core

/** Created by haipham on 2019/01/15 */
/**
 * A [IReduxStore] that handles [DefaultReduxAction].
 * @param GState The global state type.
 * @param state See [ThreadSafeStore.state].
 * @param reducer See [ThreadSafeStore.reducer].
 */
class DefaultActionStore<GState>(state: GState, reducer: IReducer<GState>) :
  IReduxStore<GState> where GState : Any {
  private val store: IReduxStore<GState>
  init { this.store = ThreadSafeStore(state, ReduxReducerWrapper(reducer)) }

  override val reducer get() = this.store.reducer
  override val dispatch get() = this.store.dispatch
  override val lastState get() = this.store.lastState
  override val subscribe get() = this.store.subscribe
  override val unsubscribe get() = this.store.unsubscribe

  override val deinitialize: IDeinitializer = {
    this.dispatch(DefaultReduxAction.Deinitialize)
    this.store.deinitialize()
  }
}
