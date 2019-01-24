/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.android.ui.recyclerview

import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.swiften.redux.android.ui.lifecycle.EmptyLifecycleCallback
import org.swiften.redux.android.ui.lifecycle.ILifecycleCallback
import org.swiften.redux.android.ui.lifecycle.ReduxLifecycleObserver
import org.swiften.redux.ui.IReduxPropContainer
import org.swiften.redux.ui.IReduxPropInjector
import org.swiften.redux.ui.IReduxPropMapper
import org.swiften.redux.ui.IReduxStatePropMapper
import org.swiften.redux.ui.IVariableReduxPropContainer
import org.swiften.redux.ui.unsubscribeSafely

/** Created by haipham on 2019/01/24/1 */
/** Perform [injectRecyclerAdapterProps], but also handle lifecycle with [lifecycleOwner] */
fun <State, Adapter, VH, VHState, VHAction> IReduxPropInjector<State>.injectRecyclerAdapterProps(
  lifecycleOwner: LifecycleOwner,
  adapter: Adapter,
  adapterMapper: IReduxStatePropMapper<State, Unit, Int>,
  vhMapper: IReduxPropMapper<State, Int, VHState, VHAction>
): RecyclerView.Adapter<VH> where
  VH : RecyclerView.ViewHolder,
  VH : IReduxPropContainer<State, VHState, VHAction>,
  Adapter : RecyclerView.Adapter<VH> {
  val wrappedAdapter = this.injectRecyclerAdapterProps(adapter, adapterMapper, vhMapper)

  ReduxLifecycleObserver(lifecycleOwner, object : ILifecycleCallback by EmptyLifecycleCallback {
    override fun onStop() { wrappedAdapter.unsubscribeSafely() }
  })

  return wrappedAdapter
}

/** Perform [injectDiffedAdapterProps], but also handle lifecycle with [lifecycleOwner] */
fun <State, Adapter, VH, VHState, VHAction> IReduxPropInjector<State>.injectDiffedAdapterProps(
  lifecycleOwner: LifecycleOwner,
  adapter: Adapter,
  adapterMapper: IReduxPropMapper<State, Unit, List<VHState>?, VHAction>,
  diffCallback: DiffUtil.ItemCallback<VHState>
): ListAdapter<VHState, VH> where
  VH : RecyclerView.ViewHolder,
  VH : IVariableReduxPropContainer<VHState, VHAction>,
  Adapter : RecyclerView.Adapter<VH>
{
  val wrappedAdapter = this.injectDiffedAdapterProps(adapter, adapterMapper, diffCallback)

  ReduxLifecycleObserver(lifecycleOwner, object : ILifecycleCallback by EmptyLifecycleCallback {
    override fun onStop() { wrappedAdapter.unsubscribeSafely() }
  })

  return wrappedAdapter
}
