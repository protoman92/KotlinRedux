/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.android.ui.recyclerview

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.swiften.redux.ui.EmptyPropLifecycleOwner
import org.swiften.redux.ui.IPropContainer
import org.swiften.redux.ui.IPropInjector
import org.swiften.redux.ui.IPropLifecycleOwner
import org.swiften.redux.ui.IPropMapper
import org.swiften.redux.ui.IVariablePropContainer
import org.swiften.redux.ui.ObservableReduxProps
import org.swiften.redux.ui.ReduxProps
import org.swiften.redux.ui.VariableProps
import org.swiften.redux.ui.unsubscribeSafely

/** Created by haipham on 2019/01/24 */
/**
 * Custom Redux-compatible [ListAdapter] implementation. This [ListAdapter] can receive [ReduxProps]
 * in order to call [ListAdapter.submitList].
 */
abstract class ReduxListAdapter<GlobalState, VH, S, A>(
  private val adapter: RecyclerView.Adapter<VH>,
  diffCallback: DiffUtil.ItemCallback<S>
) : ListAdapter<S, VH>(diffCallback),
  IPropLifecycleOwner<GlobalState> by EmptyPropLifecycleOwner(),
  IPropContainer<GlobalState, List<S>?, A> where VH : RecyclerView.ViewHolder {
  override var reduxProps by ObservableReduxProps<GlobalState, List<S>?, A> { _, next ->
    next?.state?.also { this.submitList(it) }
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
    adapter.onCreateViewHolder(parent, viewType)

  override fun getItemViewType(position: Int) = adapter.getItemViewType(position)
  override fun getItemId(position: Int) = adapter.getItemId(position)
  override fun onFailedToRecycleView(holder: VH) = adapter.onFailedToRecycleView(holder)

  override fun onViewRecycled(holder: VH) {
    super.onViewRecycled(holder)
    this.adapter.onViewRecycled(holder)
  }

  override fun onViewAttachedToWindow(holder: VH) {
    super.onViewAttachedToWindow(holder)
    this.adapter.onViewAttachedToWindow(holder)
  }

  override fun onViewDetachedFromWindow(holder: VH) {
    super.onViewDetachedFromWindow(holder)
    this.adapter.onViewDetachedFromWindow(holder)
  }

  override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
    super.onAttachedToRecyclerView(recyclerView)
    this.adapter.onAttachedToRecyclerView(recyclerView)
  }

  override fun setHasStableIds(hasStableIds: Boolean) {
    super.setHasStableIds(hasStableIds)
    this.adapter.setHasStableIds(hasStableIds)
  }

  override fun registerAdapterDataObserver(observer: RecyclerView.AdapterDataObserver) {
    super.registerAdapterDataObserver(observer)
    this.adapter.registerAdapterDataObserver(observer)
  }

  override fun unregisterAdapterDataObserver(observer: RecyclerView.AdapterDataObserver) {
    super.unregisterAdapterDataObserver(observer)
    this.adapter.unregisterAdapterDataObserver(observer)
  }
}

/**
 * Inject props for a [Adapter] with a compatible [VH] by wrapping it in a [ListAdapter]. Note
 * that [adapter] does not have to be a [ListAdapter] - it can be any [RecyclerView.Adapter] as
 * long as it implements [RecyclerView.Adapter.onCreateViewHolder].
 *
 * Since we do not call [IPropInjector.injectProps] directly into [VH], we cannot use
 * [IPropMapper.mapAction] on [VH] itself. As a result, we must pass down
 * [VariableProps.action] from [ReduxListAdapter.reduxProps] into each [VH] instance. The
 * [VHAction] should contain actions that take at least one [Int] parameter, (e.g. (Int) -> Unit),
 * so that we can use [RecyclerView.ViewHolder.getLayoutPosition] to call them.
 *
 * Note that this does not support lifecycle handling, so we will need to manually set null via
 * [RecyclerView.setAdapter] to invoke [RecyclerView.Adapter.onDetachedFromRecyclerView].
 */
fun <GlobalState, Adapter, VH, VHState, VHAction> IPropInjector<GlobalState>.injectDiffedAdapterProps(
  adapter: Adapter,
  adapterMapper: IPropMapper<GlobalState, Unit, List<VHState>?, VHAction>,
  diffCallback: DiffUtil.ItemCallback<VHState>
): ReduxListAdapter<GlobalState, VH, VHState, VHAction> where
  VH : RecyclerView.ViewHolder,
  VH : IVariablePropContainer<VHState, VHAction>,
  Adapter : RecyclerView.Adapter<VH> {
  val listAdapter = object : ReduxListAdapter<GlobalState, VH, VHState, VHAction>(adapter, diffCallback) {
    override fun onBindViewHolder(holder: VH, position: Int) {
      adapter.onBindViewHolder(holder, position)

      this.reduxProps?.variable?.action?.also {
        holder.reduxProps = VariableProps(this.getItem(position), it)
      }
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
      super.onDetachedFromRecyclerView(recyclerView)
      this.unsubscribeSafely()
    }
  }

  this.injectProps(listAdapter, Unit, adapterMapper)
  return listAdapter
}
