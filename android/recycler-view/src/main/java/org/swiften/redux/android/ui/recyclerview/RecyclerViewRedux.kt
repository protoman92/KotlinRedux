/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.android.ui.recyclerview

import androidx.recyclerview.widget.RecyclerView
import org.swiften.redux.core.IReduxDispatcher
import org.swiften.redux.ui.ReduxUI

/** Created by haipham on 2019/01/08 */
/** Inject props for a [RecyclerView.Adapter] with a compatible [VH] */
fun <State, SP, AP, VH> ReduxUI.IPropInjector<State>.injectRecyclerViewProps(
  adapter: RecyclerView.Adapter<VH>,
  mapper: ReduxUI.IPropMapper<State, Int, SP, AP>
): RecyclerView.Adapter<VH> where
  VH : RecyclerView.ViewHolder,
  VH : ReduxUI.IPropContainer<State, SP, AP> = adapter

/** Similar to [ReduxUI.IPropInjector.injectRecyclerViewProps], but ignores action props */
fun <State, SP, VH> ReduxUI.IPropInjector<State>.injectRecyclerViewProps(
  adapter: RecyclerView.Adapter<VH>,
  mapper: ReduxUI.IStatePropMapper<State, Int, SP>
): RecyclerView.Adapter<VH> where
  VH : RecyclerView.ViewHolder,
  VH : ReduxUI.IPropContainer<State, SP, Unit> =
  this.injectRecyclerViewProps<State, SP, Unit, VH>(adapter,
    object : ReduxUI.IPropMapper<State, Int, SP, Unit> {
      override fun mapState(state: State, outProps: Int) = mapper.mapState(state, outProps)
      override fun mapAction(dispatch: IReduxDispatcher, state: State, outProps: Int) = Unit
    })
