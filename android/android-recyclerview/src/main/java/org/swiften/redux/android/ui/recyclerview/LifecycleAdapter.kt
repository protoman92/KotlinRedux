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
import org.swiften.redux.ui.IPropLifecycleOwner
import org.swiften.redux.ui.IPropMapper
import org.swiften.redux.ui.IStateMapper
import org.swiften.redux.ui.unsubscribeSafely

/** Created by haipham on 2019/01/24 */
/** Perform [injectRecyclerAdapter], but also handle lifecycle with [lifecycleOwner] */
fun <GlobalState, VH, VHState, VHAction> IPropInjector<GlobalState>.injectRecyclerAdapter(
  lifecycleOwner: LifecycleOwner,
  adapter: RecyclerView.Adapter<VH>,
  adapterMapper: IStateMapper<GlobalState, Unit, Int>,
  vhMapper: IPropMapper<GlobalState, Int, VHState, VHAction>
): RecyclerView.Adapter<VH> where
  VH : RecyclerView.ViewHolder,
  VH : IPropContainer<GlobalState, VHState, VHAction>,
  VH : IPropLifecycleOwner<GlobalState> {
  val wrappedAdapter = this.injectRecyclerAdapter(adapter, adapterMapper, vhMapper)

  LifecycleObserver(lifecycleOwner, object : ILifecycleCallback {
    override fun onSafeForStartingLifecycleAwareTasks() {}
    override fun onSafeForEndingLifecycleAwareTasks() { wrappedAdapter.unsubscribeSafely() }
  })

  return wrappedAdapter
}

fun <GlobalState, Adapter, VH, VHState, VHAction> IPropInjector<GlobalState>.injectRecyclerAdapter(
  lifecycleOwner: LifecycleOwner,
  adapter: Adapter,
  vhMapper: IPropMapper<GlobalState, Int, VHState, VHAction>
): RecyclerView.Adapter<VH> where
  VH : RecyclerView.ViewHolder,
  VH : IPropContainer<GlobalState, VHState, VHAction>,
  VH : IPropLifecycleOwner<GlobalState>,
  Adapter : RecyclerView.Adapter<VH>,
  Adapter : IStateMapper<GlobalState, Unit, Int> {
  return this.injectRecyclerAdapter(lifecycleOwner, adapter, adapter, vhMapper)
}

/** Perform [injectDiffedAdapter], but also handle lifecycle with [lifecycleOwner] */
fun <GlobalState, VH, VHS, VHA> IPropInjector<GlobalState>.injectDiffedAdapter(
  lifecycleOwner: LifecycleOwner,
  adapter: RecyclerView.Adapter<VH>,
  adapterMapper: IPropMapper<GlobalState, Unit, List<VHS>, VHA>,
  diffCallback: DiffUtil.ItemCallback<VHS>
): ListAdapter<VHS, VH> where
  VH : RecyclerView.ViewHolder,
  VH : IPropContainer<GlobalState, VHS, VHA> {
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
  adapterMapper: IPropMapper<GlobalState, Unit, List<VHS>, VHA>,
  diffCallback: IDiffItemCallback<VHS>
): ListAdapter<VHS, VH> where
  VH : RecyclerView.ViewHolder,
  VH : IPropContainer<GlobalState, VHS, VHA> {
  return this.injectDiffedAdapter(lifecycleOwner, adapter, adapterMapper,
    object : DiffUtil.ItemCallback<VHS>() {
      override fun areItemsTheSame(oldItem: VHS, newItem: VHS): Boolean {
        return diffCallback.areItemsTheSame(oldItem, newItem)
      }

      override fun areContentsTheSame(oldItem: VHS, newItem: VHS): Boolean {
        return diffCallback.areContentsTheSame(oldItem, newItem)
      }
    })
}

/**
 * Convenience [injectDiffedAdapter] for when [mapper] implements both [IPropMapper] and
 * [DiffUtil.ItemCallback].
 */
fun <GlobalState, Mapper, VH, VHS, VHA> IPropInjector<GlobalState>.injectDiffedAdapter(
  lifecycleOwner: LifecycleOwner,
  adapter: RecyclerView.Adapter<VH>,
  mapper: Mapper
): ListAdapter<VHS, VH> where
  VH : RecyclerView.ViewHolder,
  VH : IPropContainer<GlobalState, VHS, VHA>,
  Mapper : IPropMapper<GlobalState, Unit, List<VHS>, VHA>,
  Mapper : IDiffItemCallback<VHS> {
  return this.injectDiffedAdapter(lifecycleOwner, adapter, mapper, mapper)
}

/**
 * Convenience [injectDiffedAdapter] for when [adapter] implements both [RecyclerView.Adapter],
 * [IPropMapper] and [DiffUtil.ItemCallback].
 */
fun <GlobalState, Adapter, VH, VHS, VHA> IPropInjector<GlobalState>.injectDiffedAdapter(
  lifecycleOwner: LifecycleOwner,
  adapter: Adapter
): ListAdapter<VHS, VH> where
  VH : RecyclerView.ViewHolder,
  VH : IPropContainer<GlobalState, VHS, VHA>,
  Adapter : RecyclerView.Adapter<VH>,
  Adapter : IPropMapper<GlobalState, Unit, List<VHS>, VHA>,
  Adapter : IDiffItemCallback<VHS> {
  return this.injectDiffedAdapter(lifecycleOwner, adapter, adapter)
}
