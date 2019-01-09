/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.android.ui.recyclerview

import androidx.recyclerview.widget.RecyclerView
import org.swiften.redux.core.IReduxDispatcher
import org.swiften.redux.ui.IReduxPropContainer
import org.swiften.redux.ui.IReduxPropInjector
import org.swiften.redux.ui.IReduxPropMapper
import org.swiften.redux.ui.IReduxStatePropMapper

/** Created by haipham on 2019/01/08 */
/** Inject props for a [RecyclerView.Adapter] with a compatible [VH] */
fun <State, SP, AP, VH> IReduxPropInjector<State>.injectRecyclerViewProps(
  adapter: RecyclerView.Adapter<VH>,
  mapper: IReduxPropMapper<State, Int, SP, AP>
): RecyclerView.Adapter<VH> where
  VH : RecyclerView.ViewHolder,
  VH : IReduxPropContainer<State, SP, AP> = adapter

/** Similar to [IReduxPropInjector.injectRecyclerViewProps], but ignores action props */
fun <State, SP, VH> IReduxPropInjector<State>.injectRecyclerViewProps(
  adapter: RecyclerView.Adapter<VH>,
  mapper: IReduxStatePropMapper<State, Int, SP>
): RecyclerView.Adapter<VH> where
  VH : RecyclerView.ViewHolder,
  VH : IReduxPropContainer<State, SP, Unit> =
  this.injectRecyclerViewProps<State, SP, Unit, VH>(adapter,
    object : IReduxPropMapper<State, Int, SP, Unit> {
      override fun mapState(state: State, outProps: Int) = mapper.mapState(state, outProps)
      override fun mapAction(dispatch: IReduxDispatcher, state: State, outProps: Int) = Unit
    })
