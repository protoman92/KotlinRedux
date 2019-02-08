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
import org.swiften.redux.android.ui.lifecycle.ReduxLifecycleObserver
import org.swiften.redux.ui.IPropContainer
import org.swiften.redux.ui.IPropInjector
import org.swiften.redux.ui.IPropMapper
import org.swiften.redux.ui.IStateMapper
import org.swiften.redux.ui.ReduxProps
import org.swiften.redux.ui.unsubscribeSafely

/** Created by haipham on 2019/01/24 */
/**
 * Perform [injectRecyclerAdapter], but also handle lifecycle with [lifecycleOwner].
 * @receiver An [IPropInjector] instance.
 * @param GState The global state type.
 * @param GExt See [IPropInjector.external].
 * @param VH The [RecyclerView.ViewHolder] instance.
 * @param VHState The [VH] state type. See [ReduxProps.state].
 * @param VHAction The [VH] action type. See [ReduxProps.action].
 * @param lifecycleOwner A [LifecycleOwner] instance.
 * @param adapter The base [RecyclerView.Adapter] instance.
 * @param adapterMapper An [IStateMapper] instance that calculates item count for
 * [RecyclerView.Adapter.getItemCount].
 * @param vhMapper An [IPropMapper] instance for [VH].
 * @return A [RecyclerView.Adapter] instance.
 */
fun <GState, GExt, VH, VHState, VHAction> IPropInjector<GState, GExt>.injectRecyclerAdapter(
  lifecycleOwner: LifecycleOwner,
  adapter: RecyclerView.Adapter<VH>,
  adapterMapper: IStateMapper<GState, Unit, Int>,
  vhMapper: IPropMapper<GState, GExt, Int, VHState, VHAction>
): RecyclerView.Adapter<VH> where
  GState : Any,
  GExt : Any,
  VH : RecyclerView.ViewHolder,
  VH : IPropContainer<GState, GExt, VHState, VHAction>,
  VHState : Any,
  VHAction : Any {
  val wrappedAdapter = this.injectRecyclerAdapter(adapter, adapterMapper, vhMapper)

  ReduxLifecycleObserver(lifecycleOwner, object : ILifecycleCallback {
    override fun onSafeForStartingLifecycleAwareTasks() {}
    override fun onSafeForEndingLifecycleAwareTasks() { wrappedAdapter.unsubscribeSafely() }
  })

  return wrappedAdapter
}

/**
 * Same as [IPropInjector.injectRecyclerAdapter] but [Adapter] also implements [IStateMapper].
 * @receiver An [IPropInjector] instance.
 * @param GState The global state type.
 * @param GExt See [IPropInjector.external].
 * @param Adapter The [RecyclerView.Adapter] type.
 * @param VH The [RecyclerView.ViewHolder] instance.
 * @param VHState The [VH] state type. See [ReduxProps.state].
 * @param VHAction The [VH] action type. See [ReduxProps.action].
 * @param lifecycleOwner A [LifecycleOwner] instance.
 * @param adapter The base [Adapter] instance.
 * @param vhMapper An [IPropMapper] instance for [VH].
 * @return A [RecyclerView.Adapter] instance.
 */
fun <GState, GExt, Adapter, VH, VHState, VHAction> IPropInjector<GState, GExt>.injectRecyclerAdapter(
  lifecycleOwner: LifecycleOwner,
  adapter: Adapter,
  vhMapper: IPropMapper<GState, GExt, Int, VHState, VHAction>
): RecyclerView.Adapter<VH> where
  GState : Any,
  GExt : Any,
  Adapter : RecyclerView.Adapter<VH>,
  Adapter : IStateMapper<GState, Unit, Int>,
  VH : RecyclerView.ViewHolder,
  VH : IPropContainer<GState, GExt, VHState, VHAction>,
  VHState : Any,
  VHAction : Any {
  return this.injectRecyclerAdapter(lifecycleOwner, adapter, adapter, vhMapper)
}

/**
 * Perform [injectDiffedAdapter], but also handle lifecycle with [lifecycleOwner].
 * @receiver An [IPropInjector] instance.
 * @param GState The global state type.
 * @param GExt See [IPropInjector.external].
 * @param VH The [RecyclerView.ViewHolder] instance.
 * @param VHState The [VH] state type. See [ReduxProps.state].
 * @param VHAction The [VH] action type. See [ReduxProps.action].
 * @param lifecycleOwner A [LifecycleOwner] instance.
 * @param adapter The base [RecyclerView.Adapter] instance.
 * @param adapterMapper An [IPropMapper] instance for [ReduxListAdapter].
 * @param diffCallback A [DiffUtil.ItemCallback] instance.
 * @return A [ListAdapter] instance.
 */
fun <GState, GExt, VH, VHState, VHAction> IPropInjector<GState, GExt>.injectDiffedAdapter(
  lifecycleOwner: LifecycleOwner,
  adapter: RecyclerView.Adapter<VH>,
  adapterMapper: IPropMapper<GState, GExt, Unit, List<VHState>, VHAction>,
  diffCallback: DiffUtil.ItemCallback<VHState>
): ListAdapter<VHState, VH> where
  GState : Any,
  GExt : Any,
  VH : RecyclerView.ViewHolder,
  VH : IPropContainer<GState, GExt, VHState, VHAction>,
  VHState : Any,
  VHAction : Any {
  val wrappedAdapter = this.injectDiffedAdapter(adapter, adapterMapper, diffCallback)

  ReduxLifecycleObserver(lifecycleOwner, object : ILifecycleCallback {
    override fun onSafeForStartingLifecycleAwareTasks() {}

    override fun onSafeForEndingLifecycleAwareTasks() {
      wrappedAdapter.unsubscribeSafely()
      wrappedAdapter.vhSubscription.unsubscribe()
    }
  })

  return wrappedAdapter
}

/**
 * Instead of [DiffUtil.ItemCallback], use [IDiffItemCallback] to avoid abstract class.
 * @receiver An [IPropInjector] instance.
 * @param GState The global state type.
 * @param GExt See [IPropInjector.external].
 * @param VH The [RecyclerView.ViewHolder] instance.
 * @param VHState The [VH] state type. See [ReduxProps.state].
 * @param VHAction The [VH] action type. See [ReduxProps.action].
 * @param lifecycleOwner A [LifecycleOwner] instance.
 * @param adapter The base [RecyclerView.Adapter] instance.
 * @param adapterMapper An [IPropMapper] instance for [ReduxListAdapter].
 * @param diffCallback A [IDiffItemCallback] instance.
 * @return A [ListAdapter] instance.
 */
fun <GState, GExt, VH, VHState, VHAction> IPropInjector<GState, GExt>.injectDiffedAdapter(
  lifecycleOwner: LifecycleOwner,
  adapter: RecyclerView.Adapter<VH>,
  adapterMapper: IPropMapper<GState, GExt, Unit, List<VHState>, VHAction>,
  diffCallback: IDiffItemCallback<VHState>
): ListAdapter<VHState, VH> where
  GState : Any,
  GExt : Any,
  VH : RecyclerView.ViewHolder,
  VH : IPropContainer<GState, GExt, VHState, VHAction>,
  VHState : Any,
  VHAction : Any {
  return this.injectDiffedAdapter(lifecycleOwner, adapter, adapterMapper,
    object : DiffUtil.ItemCallback<VHState>() {
      override fun areItemsTheSame(oldItem: VHState, newItem: VHState): Boolean {
        return diffCallback.areItemsTheSame(oldItem, newItem)
      }

      override fun areContentsTheSame(oldItem: VHState, newItem: VHState): Boolean {
        return diffCallback.areContentsTheSame(oldItem, newItem)
      }
    })
}

/**
 * Convenience [injectDiffedAdapter] for when [mapper] implements both [IPropMapper] and
 * [DiffUtil.ItemCallback].
 * @receiver An [IPropInjector] instance.
 * @param GState The global state type.
 * @param GExt See [IPropInjector.external].
 * @param VH The [RecyclerView.ViewHolder] instance.
 * @param VHState The [VH] state type. See [ReduxProps.state].
 * @param VHAction The [VH] action type. See [ReduxProps.action].
 * @param lifecycleOwner A [LifecycleOwner] instance.
 * @param adapter The base [RecyclerView.Adapter] instance.
 * @param mapper An [IPropMapper] instance for [ReduxListAdapter] that also implements
 * [IDiffItemCallback].
 * @return A [ListAdapter] instance.
 */
fun <GState, GExt, Mapper, VH, VHState, VHAction> IPropInjector<GState, GExt>.injectDiffedAdapter(
  lifecycleOwner: LifecycleOwner,
  adapter: RecyclerView.Adapter<VH>,
  mapper: Mapper
): ListAdapter<VHState, VH> where
  GState : Any,
  GExt : Any,
  VH : RecyclerView.ViewHolder,
  VH : IPropContainer<GState, GExt, VHState, VHAction>,
  VHState : Any,
  VHAction : Any,
  Mapper : IPropMapper<GState, GExt, Unit, List<VHState>, VHAction>,
  Mapper : IDiffItemCallback<VHState> {
  return this.injectDiffedAdapter(lifecycleOwner, adapter, mapper, mapper)
}

/**
 * Convenience [injectDiffedAdapter] for when [adapter] implements both [RecyclerView.Adapter],
 * [IPropMapper] and [DiffUtil.ItemCallback].
 * @receiver An [IPropInjector] instance.
 * @param GState The global state type.
 * @param GExt See [IPropInjector.external].
 * @param Adapter The base [RecyclerView.Adapter] type.
 * @param VH The [RecyclerView.ViewHolder] instance.
 * @param VHState The [VH] state type. See [ReduxProps.state].
 * @param VHAction The [VH] action type. See [ReduxProps.action].
 * @param lifecycleOwner A [LifecycleOwner] instance.
 * @param adapter The base [Adapter] instance that also implements [IPropMapper] and
 * [IDiffItemCallback].
 * @return A [ListAdapter] instance.
 */
fun <GState, GExt, Adapter, VH, VHState, VHAction> IPropInjector<GState, GExt>.injectDiffedAdapter(
  lifecycleOwner: LifecycleOwner,
  adapter: Adapter
): ListAdapter<VHState, VH> where
  GState : Any,
  GExt : Any,
  Adapter : RecyclerView.Adapter<VH>,
  Adapter : IPropMapper<GState, GExt, Unit, List<VHState>, VHAction>,
  Adapter : IDiffItemCallback<VHState>,
  VH : RecyclerView.ViewHolder,
  VH : IPropContainer<GState, GExt, VHState, VHAction>,
  VHState : Any,
  VHAction : Any {
  return this.injectDiffedAdapter(lifecycleOwner, adapter, adapter)
}
