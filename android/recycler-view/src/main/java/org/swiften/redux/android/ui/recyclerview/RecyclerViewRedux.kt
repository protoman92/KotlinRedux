/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.android.ui.recyclerview

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.swiften.redux.android.ui.core.injectPropsOnMainThread
import org.swiften.redux.core.IReduxDispatcher
import org.swiften.redux.ui.IReduxPropContainer
import org.swiften.redux.ui.IReduxPropInjector
import org.swiften.redux.ui.IReduxPropMapper
import org.swiften.redux.ui.IReduxStatePropMapper

/** Created by haipham on 2019/01/08 */
/** Inject props for a [RecyclerView.Adapter] with a compatible [VH] */
fun <State, Adapter, VH, VHState, VHAction> IReduxPropInjector<State>.injectRecyclerViewProps(
  adapter: Adapter,
  adapterMapper: IReduxStatePropMapper<State, Unit, Int>,
  vhMapper: IReduxPropMapper<State, Int, VHState, VHAction>
): RecyclerView.Adapter<VH> where
  VH : RecyclerView.ViewHolder,
  VH : IReduxPropContainer<State, VHState, VHAction>,
  Adapter : RecyclerView.Adapter<VH>
{
  require(adapter.itemCount == 0) { "$adapter should not manually declare item count" }

  return object : RecyclerView.Adapter<VH>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
      adapter.onCreateViewHolder(parent, viewType)

    override fun getItemCount() =
      adapterMapper.mapState(this@injectRecyclerViewProps.stateGetter(), Unit)

    override fun getItemViewType(position: Int) = adapter.getItemViewType(position)
    override fun getItemId(position: Int) = adapter.getItemId(position)
    override fun onFailedToRecycleView(holder: VH) = adapter.onFailedToRecycleView(holder)

    override fun onBindViewHolder(holder: VH, position: Int) {
      adapter.onBindViewHolder(holder, position)
      this@injectRecyclerViewProps.injectPropsOnMainThread(holder, position, vhMapper)
    }

    /** Unsubscribe from [holder]'s subscription on recycling */
    override fun onViewRecycled(holder: VH) {
      super.onViewRecycled(holder)
      adapter.onViewRecycled(holder)

      try {
        holder.staticProps.subscription.unsubscribe()
      } catch (e: UninitializedPropertyAccessException) { }
    }

    override fun onViewAttachedToWindow(holder: VH) {
      super.onViewAttachedToWindow(holder)
      adapter.onViewAttachedToWindow(holder)
    }

    override fun onViewDetachedFromWindow(holder: VH) {
      super.onViewDetachedFromWindow(holder)
      adapter.onViewDetachedFromWindow(holder)
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
      super.onAttachedToRecyclerView(recyclerView)
      adapter.onAttachedToRecyclerView(recyclerView)
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
      super.onDetachedFromRecyclerView(recyclerView)
      adapter.onDetachedFromRecyclerView(recyclerView)
    }

    override fun setHasStableIds(hasStableIds: Boolean) {
      super.setHasStableIds(hasStableIds)
      adapter.setHasStableIds(hasStableIds)
    }

    override fun registerAdapterDataObserver(observer: RecyclerView.AdapterDataObserver) {
      super.registerAdapterDataObserver(observer)
      adapter.registerAdapterDataObserver(observer)
    }

    override fun unregisterAdapterDataObserver(observer: RecyclerView.AdapterDataObserver) {
      super.unregisterAdapterDataObserver(observer)
      adapter.unregisterAdapterDataObserver(observer)
    }
  }
}

/** Similar to [IReduxPropInjector.injectRecyclerViewProps], but ignores action props */
fun <State, Adapter, VH, VHState> IReduxPropInjector<State>.injectRecyclerViewProps(
  adapter: Adapter,
  adapterMapper: IReduxStatePropMapper<State, Unit, Int>,
  vhMapper: IReduxStatePropMapper<State, Int, VHState>
): RecyclerView.Adapter<VH> where
  VH : RecyclerView.ViewHolder,
  VH : IReduxPropContainer<State, VHState, Unit>,
  Adapter : RecyclerView.Adapter<VH> =
  this.injectRecyclerViewProps<State, Adapter, VH, VHState, Unit>(adapter, adapterMapper,
    object : IReduxPropMapper<State, Int, VHState, Unit> {
      override fun mapState(state: State, outProps: Int) = vhMapper.mapState(state, outProps)
      override fun mapAction(dispatch: IReduxDispatcher, state: State, outProps: Int) = Unit
    })

/**
 * Similar to [IReduxPropInjector.injectRecyclerViewProps], but [Adapter] now conforms to
 * [IReduxStatePropMapper].
 */
fun <State, Adapter, VH, VHState> IReduxPropInjector<State>.injectRecyclerViewProps(
  adapter: Adapter,
  vhMapper: IReduxStatePropMapper<State, Int, VHState>
): RecyclerView.Adapter<VH> where
  VH : RecyclerView.ViewHolder,
  VH : IReduxPropContainer<State, VHState, Unit>,
  Adapter : RecyclerView.Adapter<VH>,
  Adapter : IReduxStatePropMapper<State, Unit, Int> =
  this.injectRecyclerViewProps(adapter, adapter, vhMapper)
