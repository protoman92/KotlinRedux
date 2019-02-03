/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.android.ui.recyclerview

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.swiften.redux.core.ReduxSubscription
import org.swiften.redux.ui.EmptyPropLifecycleOwner
import org.swiften.redux.ui.IPropContainer
import org.swiften.redux.ui.IPropInjector
import org.swiften.redux.ui.IPropLifecycleOwner
import org.swiften.redux.ui.IPropMapper
import org.swiften.redux.ui.ObservableReduxProps
import org.swiften.redux.ui.ReduxProps
import org.swiften.redux.ui.VariableProps
import org.swiften.redux.ui.unsubscribeSafely

/** Created by haipham on 2019/01/24 */
/** Callback for [DiffUtil.ItemCallback] since [DiffUtil.ItemCallback] is an abstract class */
interface IDiffItemCallback<T> {
  /** @see [DiffUtil.ItemCallback.areItemsTheSame] */
  fun areItemsTheSame(oldItem: T, newItem: T): Boolean

  /** @see [DiffUtil.ItemCallback.areContentsTheSame] */
  fun areContentsTheSame(oldItem: T, newItem: T): Boolean
}

/**
 * Custom Redux-compatible [ListAdapter] implementation. This [ListAdapter] can receive [ReduxProps]
 * in order to call [ListAdapter.submitList].
 */
abstract class ReduxListAdapter<GState, GExt, VH, S, A>(
  private val adapter: RecyclerView.Adapter<VH>,
  diffCallback: DiffUtil.ItemCallback<S>
) : ListAdapter<S, VH>(diffCallback),
  IPropLifecycleOwner<GState, GExt> by EmptyPropLifecycleOwner(),
  IPropContainer<List<S>, A> where VH : RecyclerView.ViewHolder {
  /**
   * Since we are only calling [ListAdapter.submitList] when [reduxProps] arrives, the
   * [VariableProps.action] instance must be non-null upon [onBindViewHolder]. As a result, we can
   * safely access [VariableProps.action] in [onBindViewHolder].
   */
  override var reduxProps by ObservableReduxProps<List<S>, A> { _, next ->
    this.submitList(next?.state ?: arrayListOf())
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
    return this.adapter.onCreateViewHolder(parent, viewType)
  }

  override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
    super.onDetachedFromRecyclerView(recyclerView)
    this.adapter.onDetachedFromRecyclerView(recyclerView)
    this.unsubscribeSafely()
  }

  override fun getItemViewType(position: Int) = this.adapter.getItemViewType(position)
  override fun getItemId(position: Int) = this.adapter.getItemId(position)
  override fun onFailedToRecycleView(holder: VH) = this.adapter.onFailedToRecycleView(holder)

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

  override fun toString() = this.adapter.toString()
}

/**
 * Inject props for [adapter] with a compatible [VH] by wrapping it in a [ListAdapter]. Note that
 * [adapter] does not have to be a [ListAdapter] - it can be any [RecyclerView.Adapter] as long as
 * it implements [RecyclerView.Adapter.onCreateViewHolder].
 *
 * Since we do not call [IPropInjector.inject] directly into [VH], we cannot use
 * [IPropMapper.mapAction] on [VH] itself. As a result, we must pass down
 * [VariableProps.action] from [ReduxListAdapter.reduxProps] into each [VH] instance. The
 * [VHA] should contain actions that take at least one [Int] parameter, (e.g. (Int) -> Unit),
 * so that we can use [RecyclerView.ViewHolder.getLayoutPosition] to call them.
 *
 * Note that this does not support lifecycle handling, so we will need to manually set null via
 * [RecyclerView.setAdapter] to invoke [RecyclerView.Adapter.onDetachedFromRecyclerView].
 */
@Suppress("UNCHECKED_CAST")
fun <GState, GExt, VH, VHS, VHA> IPropInjector<GState, GExt>.injectDiffedAdapter(
  adapter: RecyclerView.Adapter<VH>,
  adapterMapper: IPropMapper<GState, GExt, Unit, List<VHS>, VHA>,
  diffCallback: DiffUtil.ItemCallback<VHS>
): ReduxListAdapter<GState, GExt, VH, VHS, VHA> where
  VH : RecyclerView.ViewHolder,
  VH : IPropContainer<VHS, VHA> {
  val listAdapter = object : ReduxListAdapter<GState, GExt, VH, VHS, VHA>(adapter, diffCallback) {
    override fun onBindViewHolder(holder: VH, position: Int) {
      adapter.onBindViewHolder(holder, position)
      require(this.reduxProps.v?.action is Any, { "Use Unit instead of null for prop mapping" })

      val action = requireNotNull(this.reduxProps.v?.action as Any) {
        "By the time this method is called, injection must have already happened at the adapter" +
          "level and there is no way for action props to be null. Please contact the library" +
          "maintainer if you are encountering this behavior."
      } as VHA

      val variable = VariableProps(this.getItem(position), action)
      holder.reduxProps = ReduxProps(ReduxSubscription.EMPTY, variable)
    }
  }

  this.inject(listAdapter, Unit, adapterMapper)
  return listAdapter
}
