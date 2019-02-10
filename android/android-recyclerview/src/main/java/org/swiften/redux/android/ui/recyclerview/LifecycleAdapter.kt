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
 * @param LState The local state type that [GState] must extend from.
 * @param OutProps Property as defined by [lifecycleOwner]'s parent.
 * @param VH The [RecyclerView.ViewHolder] instance.
 * @param VHState The [VH] state type. See [ReduxProps.state].
 * @param VHAction The [VH] action type. See [ReduxProps.action].
 * @param lifecycleOwner A [LifecycleOwner] instance.
 * @param adapter The base [RecyclerView.Adapter] instance.
 * @param outProps An [OutProps] instance.
 * @param adapterMapper An [IStateMapper] instance that calculates item count for
 * [RecyclerView.Adapter.getItemCount].
 * @param vhMapper An [IPropMapper] instance for [VH].
 * @return A [RecyclerView.Adapter] instance.
 */
fun <GState, LState, OutProps, VH, VHState, VHAction> IPropInjector<GState>.injectRecyclerAdapter(
  lifecycleOwner: LifecycleOwner,
  adapter: RecyclerView.Adapter<VH>,
  outProps: OutProps,
  adapterMapper: IStateMapper<LState, Unit, Int>,
  vhMapper: IPropMapper<LState, PositionProps<OutProps>, VHState, VHAction>
): RecyclerView.Adapter<VH> where
  GState : LState,
  LState : Any,
  VH : RecyclerView.ViewHolder,
  VH : IPropContainer<LState, PositionProps<OutProps>, VHState, VHAction>,
  VHState : Any,
  VHAction : Any {
  val wrappedAdapter = this.injectRecyclerAdapter(adapter, outProps, adapterMapper, vhMapper)

  ReduxLifecycleObserver(lifecycleOwner, object : ILifecycleCallback {
    override fun onSafeForStartingLifecycleAwareTasks() {}
    override fun onSafeForEndingLifecycleAwareTasks() { wrappedAdapter.unsubscribeSafely() }
  })

  return wrappedAdapter
}

/**
 * Perform [injectDiffedAdapter], but also handle lifecycle with [lifecycleOwner].
 * @receiver An [IPropInjector] instance.
 * @param GState The global state type.
 * @param GExt See [IPropInjector.external].
 * @param LState The local state type that [GState] must extend from.
 * @param OutProps Property as defined by [lifecycleOwner]'s parent.
 * @param VH The [RecyclerView.ViewHolder] instance.
 * @param VHState The [VH] state type. See [ReduxProps.state].
 * @param VHAction The [VH] action type. See [ReduxProps.action].
 * @param lifecycleOwner A [LifecycleOwner] instance.
 * @param adapter The base [RecyclerView.Adapter] instance.
 * @param outProps An [OutProps] instance.
 * @param adapterMapper An [IPropMapper] instance for [ReduxListAdapter].
 * @param diffCallback A [DiffUtil.ItemCallback] instance.
 * @return A [ListAdapter] instance.
 */
fun <GState, LState, OutProps, VH, VHState, VHAction> IPropInjector<GState>.injectDiffedAdapter(
  lifecycleOwner: LifecycleOwner,
  adapter: RecyclerView.Adapter<VH>,
  outProps: OutProps,
  adapterMapper: IPropMapper<LState, OutProps, List<VHState>, VHAction>,
  diffCallback: DiffUtil.ItemCallback<VHState>
): ListAdapter<VHState, VH> where
  GState : LState,
  LState : Any,
  VH : RecyclerView.ViewHolder,
  VH : IPropContainer<LState, OutProps, VHState, VHAction>,
  VHState : Any,
  VHAction : Any {
  val wrappedAdapter = this.injectDiffedAdapter(adapter, outProps, adapterMapper, diffCallback)

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
 * @param LState The local state type that [GState] must extend from.
 * @param OutProps Property as defined by [lifecycleOwner]'s parent.
 * @param VH The [RecyclerView.ViewHolder] instance.
 * @param VHState The [VH] state type. See [ReduxProps.state].
 * @param VHAction The [VH] action type. See [ReduxProps.action].
 * @param lifecycleOwner A [LifecycleOwner] instance.
 * @param adapter The base [RecyclerView.Adapter] instance.
 * @param outProps An [OutProps] instance.
 * @param adapterMapper An [IPropMapper] instance for [ReduxListAdapter].
 * @param diffCallback A [IDiffItemCallback] instance.
 * @return A [ListAdapter] instance.
 */
fun <GState, LState, OutProps, VH, VHState, VHAction> IPropInjector<GState>.injectDiffedAdapter(
  lifecycleOwner: LifecycleOwner,
  adapter: RecyclerView.Adapter<VH>,
  outProps: OutProps,
  adapterMapper: IPropMapper<LState, OutProps, List<VHState>, VHAction>,
  diffCallback: IDiffItemCallback<VHState>
): ListAdapter<VHState, VH> where
  GState : LState,
  LState : Any,
  VH : RecyclerView.ViewHolder,
  VH : IPropContainer<LState, OutProps, VHState, VHAction>,
  VHState : Any,
  VHAction : Any {
  return this.injectDiffedAdapter(lifecycleOwner, adapter, outProps, adapterMapper,
    object : DiffUtil.ItemCallback<VHState>() {
      override fun areItemsTheSame(oldItem: VHState, newItem: VHState): Boolean {
        return diffCallback.areItemsTheSame(oldItem, newItem)
      }

      override fun areContentsTheSame(oldItem: VHState, newItem: VHState): Boolean {
        return diffCallback.areContentsTheSame(oldItem, newItem)
      }
    })
}
