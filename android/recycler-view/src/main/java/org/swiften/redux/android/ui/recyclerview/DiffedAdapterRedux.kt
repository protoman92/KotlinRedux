/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.android.ui.recyclerview

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.swiften.redux.core.IReduxDispatcher
import org.swiften.redux.ui.EmptyReduxPropLifecycleOwner
import org.swiften.redux.ui.IReduxPropContainer
import org.swiften.redux.ui.IReduxPropInjector
import org.swiften.redux.ui.IReduxPropLifecycleOwner
import org.swiften.redux.ui.IReduxPropMapper
import org.swiften.redux.ui.IReduxStatePropMapper
import org.swiften.redux.ui.ObservableReduxProps
import org.swiften.redux.ui.ReduxProps
import org.swiften.redux.ui.unsubscribeSafely

/** Created by haipham on 2019/01/24 */
/**
 * Custom Redux-compatible [ListAdapter] implementation. This [ListAdapter] can receive [ReduxProps]
 * in order to call [ListAdapter.submitList].
 */
internal abstract class ReduxListAdapter<State, T, VH>(
  diffCallback: DiffUtil.ItemCallback<T>
) : ListAdapter<T, VH>(diffCallback),
  IReduxPropLifecycleOwner<State> by EmptyReduxPropLifecycleOwner(),
  IReduxPropContainer<State, List<T>?, Unit> where VH : RecyclerView.ViewHolder {
  override var reduxProps by ObservableReduxProps<State, List<T>?, Unit> { _, next ->
    next?.state?.also { this.submitList(it) }
  }
}

/**
 * Inject props for a [Adapter] with a compatible [VH] by wrapping it in a [ListAdapter]. Note
 * that [adapter] does not have to be a [ListAdapter] - it can be any [RecyclerView.Adapter] as
 * long as it implements [RecyclerView.Adapter.onCreateViewHolder].
 */
fun <State, Adapter, T, VH> IReduxPropInjector<State>.injectDiffedAdapterProps(
  adapter: Adapter,
  adapterMapper: IReduxStatePropMapper<State, Unit, List<T>?>,
  diffCallback: DiffUtil.ItemCallback<T>
): RecyclerView.Adapter<VH> where
  VH : RecyclerView.ViewHolder,
  Adapter : RecyclerView.Adapter<VH>
{
  val listAdapter = object : ReduxListAdapter<State, T, VH>(diffCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
      adapter.onCreateViewHolder(parent, viewType)

    override fun onBindViewHolder(holder: VH, position: Int) {}

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
      super.onDetachedFromRecyclerView(recyclerView)
      this.unsubscribeSafely()
    }
  }

  this.injectProps(listAdapter, Unit, object : IReduxPropMapper<State, Unit, List<T>?, Unit> {
    override fun mapAction(dispatch: IReduxDispatcher, state: State, outProps: Unit) = Unit

    override fun mapState(state: State, outProps: Unit): List<T>? =
      adapterMapper.mapState(state, Unit)
  })

  return object : DelegateRecyclerAdapter<VH>(adapter) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
      listAdapter.onCreateViewHolder(parent, viewType)

    override fun getItemCount() = listAdapter.itemCount

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
      super.onDetachedFromRecyclerView(recyclerView)
      listAdapter.onDetachedFromRecyclerView(recyclerView)
    }
  }
}
