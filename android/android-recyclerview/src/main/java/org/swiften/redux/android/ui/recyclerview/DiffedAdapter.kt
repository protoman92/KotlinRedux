/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.android.ui.recyclerview

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.swiften.redux.core.CompositeReduxSubscription
import org.swiften.redux.core.ReduxSubscription
import org.swiften.redux.ui.EmptyPropLifecycleOwner
import org.swiften.redux.ui.IActionDependency
import org.swiften.redux.ui.IPropContainer
import org.swiften.redux.ui.IPropInjector
import org.swiften.redux.ui.IPropLifecycleOwner
import org.swiften.redux.ui.IPropMapper
import org.swiften.redux.ui.ObservableReduxProps
import org.swiften.redux.ui.ReduxProps
import org.swiften.redux.ui.StaticProps
import org.swiften.redux.ui.unsubscribeSafely
import java.util.Date

/** Created by haipham on 2019/01/24 */
/** Callback for [DiffUtil.ItemCallback] since [DiffUtil.ItemCallback] is an abstract class. */
interface IDiffItemCallback<T> {
  /** @see [DiffUtil.ItemCallback.areItemsTheSame]. */
  fun areItemsTheSame(oldItem: T, newItem: T): Boolean

  /** @see [DiffUtil.ItemCallback.areContentsTheSame]. */
  fun areContentsTheSame(oldItem: T, newItem: T): Boolean
}

/**
 * Custom Redux-compatible [ListAdapter] implementation. This [ListAdapter] can receive [ReduxProps]
 * in order to call [ListAdapter.submitList].
 * @param GState The global state type.
 * @param GExt See [IPropInjector.external].
 * @param LState The local state type that [GState] must extend from.
 * @param LExt See [IActionDependency.external]. [GExt] must extend from this parameter.
 * @param VH The [RecyclerView.ViewHolder] instance.
 * @param VHState The [VH] state type. See [ReduxProps.state].
 * @param VHAction The [VH] action type. See [ReduxProps.action].
 * @param adapter The base [RecyclerView.Adapter] instance.
 * @param diffCallback A [DiffUtil.ItemCallback] instance.
 */
abstract class ReduxListAdapter<GState, GExt, LState, LExt, VH, VHState, VHAction>(
  private val adapter: RecyclerView.Adapter<VH>,
  diffCallback: DiffUtil.ItemCallback<VHState>
) : ListAdapter<VHState, VH>(diffCallback),
  IPropLifecycleOwner<LState, LExt> by EmptyPropLifecycleOwner(),
  IPropContainer<LState, LExt, List<VHState>, VHAction> where
  GState : LState,
  GExt : LExt,
  LState : Any,
  LExt : Any,
  VH : RecyclerView.ViewHolder,
  VHState : Any,
  VHAction : Any {
  internal lateinit var staticProps: StaticProps<LState, LExt>

  /**
   * Since we will be manually injecting props into [VH] instances, we will need to collect their
   * [ReduxSubscription] here.
   */
  val vhSubscription = CompositeReduxSubscription("$this${Date().time}")

  /**
   * Since we are only calling [ListAdapter.submitList] when [reduxProps] arrives, the
   * [ReduxProps.action] instance must be non-null upon [onBindViewHolder]. As a result, we can
   * safely access [ReduxProps.action] in [onBindViewHolder].
   */
  override var reduxProps by ObservableReduxProps<List<VHState>, VHAction> { _, next ->
    this.submitList(next.state ?: arrayListOf())
  }

  override fun beforePropInjectionStarts(sp: StaticProps<LState, LExt>) {
    this.staticProps = sp
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
 * [ReduxProps.action] from [ReduxListAdapter.reduxProps] into each [VH] instance. The [VHAction] should
 * contain actions that take at least one [Int] parameter, (e.g. (Int) -> Unit), so that we can use
 * [RecyclerView.ViewHolder.getLayoutPosition] to call them.
 *
 * Note that this does not support lifecycle handling, so we will need to manually set null via
 * [RecyclerView.setAdapter] to invoke [RecyclerView.Adapter.onDetachedFromRecyclerView].
 * @param GState The global state type.
 * @param GExt See [IPropInjector.external].
 * @param LState The local state type that [GState] must extend from.
 * @param LExt See [IActionDependency.external]. [GExt] must extend from this parameter.
 * @param VH The [RecyclerView.ViewHolder] instance.
 * @param VHState The [VH] state type. See [ReduxProps.state].
 * @param VHAction The [VH] action type. See [ReduxProps.action].
 * @param adapter The base [RecyclerView.Adapter] instance.
 * @param adapterMapper An [IPropMapper] instance for [ReduxListAdapter].
 * @param diffCallback A [DiffUtil.ItemCallback] instance.
 */
@Suppress("UNCHECKED_CAST")
fun <GState, GExt, LState, LExt, VH, VHState, VHAction> IPropInjector<GState, GExt>.injectDiffedAdapter(
  adapter: RecyclerView.Adapter<VH>,
  adapterMapper: IPropMapper<LState, LExt, Unit, List<VHState>, VHAction>,
  diffCallback: DiffUtil.ItemCallback<VHState>
): ReduxListAdapter<GState, GExt, LState, LExt, VH, VHState, VHAction> where
  GState : LState,
  GExt : LExt,
  LState : Any,
  LExt : Any,
  VH : RecyclerView.ViewHolder,
  VH : IPropContainer<LState, LExt, VHState, VHAction>,
  VHState : Any,
  VHAction : Any {
  val listAdapter = object : ReduxListAdapter<GState, GExt, LState, LExt, VH, VHState, VHAction>(adapter, diffCallback) {
    override fun onBindViewHolder(holder: VH, position: Int) {
      adapter.onBindViewHolder(holder, position)
      val subscribeId = "$holder${Date().time}"
      val subscription = ReduxSubscription(subscribeId) { holder.afterPropInjectionEnds() }
      this.vhSubscription.add(subscription)
      holder.beforePropInjectionStarts(this.staticProps)
      holder.reduxProps = ReduxProps(subscription, this.getItem(position), this.reduxProps.action)
    }

    override fun onViewRecycled(holder: VH) {
      super.onViewRecycled(holder)
      holder.unsubscribeSafely()?.also { this.vhSubscription.remove(it) }
    }
  }

  this.inject(listAdapter, Unit, adapterMapper)
  return listAdapter
}
