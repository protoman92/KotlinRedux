/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.core

/** Created by haipham on 2019/02/16 */
/**
 * Use this [DispatchWrapper] to track the ordering of [dispatch] wrapping using [id].
 * @param id A [String] value.
 * @param dispatch See [IReduxStore.dispatch].
 */
class DispatchWrapper private constructor (val id: String, val dispatch: IActionDispatcher) {
  companion object {
    private const val ROOT_WRAPPER = "root"

    /**
     * Create the root [DispatchWrapper] with an [IActionDispatcher].
     * @param dispatch An [IActionDispatcher] instance.
     * @return A [DispatchWrapper] instance.
     */
    fun root(dispatch: IActionDispatcher): DispatchWrapper {
      return DispatchWrapper(this.ROOT_WRAPPER, dispatch)
    }

    /**
     * Wrap another [DispatchWrapper] with a new [id] and [dispatch].
     * @param wrapper A [DispatchWrapper] instance.
     * @param id The [DispatchWrapper.id] of the resulting [DispatchWrapper].
     * @param dispatch The [DispatchWrapper.dispatch] of the resulting [DispatchWrapper].
     * @return A [DispatchWrapper] instance.
     */
    fun wrap(wrapper: DispatchWrapper, id: String, dispatch: IActionDispatcher): DispatchWrapper {
      return DispatchWrapper("$id -> ${wrapper.id}", dispatch)
    }
  }

  override fun toString() = this.id
}
