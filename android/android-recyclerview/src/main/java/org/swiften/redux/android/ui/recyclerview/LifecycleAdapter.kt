/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.android.ui.recyclerview

import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.swiften.redux.android.ui.lifecycle.ILifecycleCallback
import org.swiften.redux.android.ui.lifecycle.LifecycleObserver
import org.swiften.redux.ui.IPropContainer
import org.swiften.redux.ui.IPropInjector
import org.swiften.redux.ui.IPropMapper
import org.swiften.redux.ui.IStateMapper
import org.swiften.redux.ui.IVariablePropContainer
import org.swiften.redux.ui.unsubscribeSafely

/** Created by haipham on 2019/01/24/1 */
/** Perform [injectRecyclerAdapter], but also handle lifecycle with [lifecycleOwner] */
fun <GlobalState, VH, VHState, VHAction> IPropInjector<GlobalState>.injectRecyclerAdapter(
  lifecycleOwner: LifecycleOwner,
  adapter: RecyclerView.Adapter<VH>,
  adapterMapper: IStateMapper<GlobalState, Unit, Int>,
  vhMapper: IPropMapper<GlobalState, Int, VHState, VHAction>
): RecyclerView.Adapter<VH> where
  VH : RecyclerView.ViewHolder,
  VH : IPropContainer<GlobalState, VHState, VHAction> {
  val wrappedAdapter = this.injectRecyclerAdapter(adapter, adapterMapper, vhMapper)

  LifecycleObserver(lifecycleOwner, object : ILifecycleCallback {
    override fun onSafeForStartingLifecycleAwareTasks() {}
    override fun onSafeForEndingLifecycleAwareTasks() { wrappedAdapter.unsubscribeSafely() }
  })

  return wrappedAdapter
}

/** Perform [injectDiffedAdapter], but also handle lifecycle with [lifecycleOwner] */
fun <GlobalState, VH, VHS, VHA> IPropInjector<GlobalState>.injectDiffedAdapter(
  lifecycleOwner: LifecycleOwner,
  adapter: RecyclerView.Adapter<VH>,
  adapterMapper: IPropMapper<GlobalState, Unit, List<VHS>?, VHA>,
  diffCallback: DiffUtil.ItemCallback<VHS>
): ListAdapter<VHS, VH> where
  VH : RecyclerView.ViewHolder,
  VH : IVariablePropContainer<VHS, VHA?> {
  val wrappedAdapter = this.injectDiffedAdapter(adapter, adapterMapper, diffCallback)

  LifecycleObserver(lifecycleOwner, object : ILifecycleCallback {
    override fun onSafeForStartingLifecycleAwareTasks() {}
    override fun onSafeForEndingLifecycleAwareTasks() { wrappedAdapter.unsubscribeSafely() }
  })

  return wrappedAdapter
}

/** Instead of [DiffUtil.ItemCallback], use [IDiffItemCallback] to avoid abstract class */
fun <GlobalState, VH, VHS, VHA> IPropInjector<GlobalState>.injectDiffedAdapter(
  lifecycleOwner: LifecycleOwner,
  adapter: RecyclerView.Adapter<VH>,
  adapterMapper: IPropMapper<GlobalState, Unit, List<VHS>?, VHA>,
  diffCallback: IDiffItemCallback<VHS>
) where VH : RecyclerView.ViewHolder, VH : IVariablePropContainer<VHS, VHA?> =
  this.injectDiffedAdapter(lifecycleOwner, adapter, adapterMapper,
    object : DiffUtil.ItemCallback<VHS>() {
      override fun areItemsTheSame(oldItem: VHS, newItem: VHS) =
        diffCallback.areItemsTheSame(oldItem, newItem)

      override fun areContentsTheSame(oldItem: VHS, newItem: VHS) =
        diffCallback.areContentsTheSame(oldItem, newItem)
    })

/**
 * Convenience [injectDiffedAdapter] for when [mapper] implements both [IPropMapper] and
 * [DiffUtil.ItemCallback].
 */
fun <GlobalState, Mapper, VH, VHS, VHA> IPropInjector<GlobalState>.injectDiffedAdapter(
  lifecycleOwner: LifecycleOwner,
  adapter: RecyclerView.Adapter<VH>,
  mapper: Mapper
) where
  VH : RecyclerView.ViewHolder,
  VH : IVariablePropContainer<VHS, VHA?>,
  Mapper : IPropMapper<GlobalState, Unit, List<VHS>?, VHA>,
  Mapper : IDiffItemCallback<VHS> =
  this.injectDiffedAdapter(lifecycleOwner, adapter, mapper, mapper)

/**
 * Convenience [injectDiffedAdapter] for when [adapter] implements both [RecyclerView.Adapter],
 * [IPropMapper] and [DiffUtil.ItemCallback].
 */
fun <GlobalState, Adapter, VH, VHS, VHA> IPropInjector<GlobalState>.injectDiffedAdapter(
  lifecycleOwner: LifecycleOwner,
  adapter: Adapter
) where
  VH : RecyclerView.ViewHolder,
  VH : IVariablePropContainer<VHS, VHA?>,
  Adapter : RecyclerView.Adapter<VH>,
  Adapter : IPropMapper<GlobalState, Unit, List<VHS>?, VHA>,
  Adapter : IDiffItemCallback<VHS> =
  this.injectDiffedAdapter(lifecycleOwner, adapter, adapter)
