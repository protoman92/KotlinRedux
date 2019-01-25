/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.android.ui.recyclerview

import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.swiften.redux.android.ui.lifecycle.LifecycleCallback
import org.swiften.redux.android.ui.lifecycle.LifecycleObserver
import org.swiften.redux.ui.IPropContainer
import org.swiften.redux.ui.IPropInjector
import org.swiften.redux.ui.IPropMapper
import org.swiften.redux.ui.IStateMapper
import org.swiften.redux.ui.IVariablePropContainer
import org.swiften.redux.ui.unsubscribeSafely

/** Created by haipham on 2019/01/24/1 */
/** Perform [injectRecyclerAdapterProps], but also handle lifecycle with [lifecycleOwner] */
fun <GlobalState, Adapter, VH, VHState, VHAction> IPropInjector<GlobalState>.injectRecyclerAdapterProps(
  lifecycleOwner: LifecycleOwner,
  adapter: Adapter,
  adapterMapper: IStateMapper<GlobalState, Unit, Int>,
  vhMapper: IPropMapper<GlobalState, Int, VHState, VHAction>
): RecyclerView.Adapter<VH> where
  VH : RecyclerView.ViewHolder,
  VH : IPropContainer<GlobalState, VHState, VHAction>,
  Adapter : RecyclerView.Adapter<VH> {
  val wrappedAdapter = this.injectRecyclerAdapterProps(adapter, adapterMapper, vhMapper)

  LifecycleObserver(lifecycleOwner, object : LifecycleCallback() {
    override fun onSafeForStartingLifecycleAwareTasks() {}
    override fun onSafeForEndingLifecycleAwareTasks() { wrappedAdapter.unsubscribeSafely() }
  })

  return wrappedAdapter
}

/** Perform [injectDiffedAdapterProps], but also handle lifecycle with [lifecycleOwner] */
fun <GlobalState, Adapter, VH, VHState, VHAction> IPropInjector<GlobalState>.injectDiffedAdapterProps(
  lifecycleOwner: LifecycleOwner,
  adapter: Adapter,
  adapterMapper: IPropMapper<GlobalState, Unit, List<VHState>?, VHAction>,
  diffCallback: DiffUtil.ItemCallback<VHState>
): ListAdapter<VHState, VH> where
  VH : RecyclerView.ViewHolder,
  VH : IVariablePropContainer<VHState, VHAction>,
  Adapter : RecyclerView.Adapter<VH> {
  val wrappedAdapter = this.injectDiffedAdapterProps(adapter, adapterMapper, diffCallback)

  LifecycleObserver(lifecycleOwner, object : LifecycleCallback() {
    override fun onSafeForStartingLifecycleAwareTasks() {}
    override fun onSafeForEndingLifecycleAwareTasks() { wrappedAdapter.unsubscribeSafely() }
  })

  return wrappedAdapter
}
