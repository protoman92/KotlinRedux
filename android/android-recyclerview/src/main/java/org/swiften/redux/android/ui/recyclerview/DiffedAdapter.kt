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
import org.swiften.redux.ui.IPropInjector
import org.swiften.redux.ui.IPropContainer
import org.swiften.redux.ui.IFullPropInjector
import org.swiften.redux.ui.IPropLifecycleOwner
import org.swiften.redux.ui.IPropMapper
import org.swiften.redux.ui.NoopPropLifecycleOwner
import org.swiften.redux.ui.ObservableReduxProp
import org.swiften.redux.ui.ReduxProp
import org.swiften.redux.ui.StaticProp
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
 * Custom Redux-compatible [ListAdapter] implementation. This [ListAdapter] can receive [ReduxProp]
 * in order to call [ListAdapter.submitList].
 * @param GState The global state type.
 * @param LState The local state type that [GState] must extend from.
 * @param OutProp Property as defined by [adapter]'s parent.
 * @param VH The [RecyclerView.ViewHolder] instance.
 * @param VHState The [VH] state type. See [ReduxProp.state].
 * @param VHAction The [VH] action type. See [ReduxProp.action].
 * @param adapter The base [RecyclerView.Adapter] instance.
 * @param diffCallback A [DiffUtil.ItemCallback] instance.
 */
abstract class ReduxListAdapter<GState, LState, OutProp, VH, VHState, VHAction>(
  private val adapter: RecyclerView.Adapter<VH>,
  diffCallback: DiffUtil.ItemCallback<VHState>
) : ListAdapter<VHState, VH>(diffCallback),
  IPropLifecycleOwner<LState, OutProp> by NoopPropLifecycleOwner(),
  IPropContainer<List<VHState>, VHAction> where
  GState : LState,
  LState : Any,
  VH : RecyclerView.ViewHolder,
  VHState : Any,
  VHAction : Any {
  internal lateinit var staticProp: StaticProp<LState, OutProp>

  /**
   * Since we will be manually injecting prop into [VH] instances, we will need to collect their
   * [ReduxSubscription] here.
   */
  val vhSubscription = CompositeReduxSubscription("${this.adapter}${Date().time}")

  /**
   * Since we are only calling [ListAdapter.submitList] when [reduxProp] arrives, the
   * [ReduxProp.action] instance must be non-null upon [onBindViewHolder]. As a result, we can
   * safely access [ReduxProp.action] in [onBindViewHolder].
   */
  override var reduxProp by ObservableReduxProp<List<VHState>, VHAction> { _, next ->
    this.submitList(next.state ?: arrayListOf())
  }

  override fun beforePropInjectionStarts(sp: StaticProp<LState, OutProp>) {
    this.staticProp = sp
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
 * Inject prop for [adapter] with a compatible [VH] by wrapping it in a [ListAdapter]. Note that
 * [adapter] does not have to be a [ListAdapter] - it can be any [RecyclerView.Adapter] as long as
 * it implements [RecyclerView.Adapter.onCreateViewHolder].
 *
 * Since we do not call [IFullPropInjector.inject] directly into [VH], we cannot use
 * [IPropMapper.mapAction] on [VH] itself. As a result, we must pass down
 * [ReduxProp.action] from [ReduxListAdapter.reduxProp] into each [VH] instance. The [VHAction]
 * should contain actions that take at least one [Int] parameter, (e.g. (Int) -> Unit), so that we
 * can use [RecyclerView.ViewHolder.getLayoutPosition] to call them.
 *
 * Note that this does not support lifecycle handling, so we will need to manually set null via
 * [RecyclerView.setAdapter] to invoke [RecyclerView.Adapter.onDetachedFromRecyclerView].
 * @receiver An [IPropInjector] instance.
 * @param GState The global state type.
 * @param OutProp Property as defined by [adapter]'s parent.
 * @param VH The [RecyclerView.ViewHolder] instance.
 * @param VHState The [VH] state type. See [ReduxProp.state].
 * @param VHAction The [VH] action type. See [ReduxProp.action].
 * @param outProp An [OutProp] instance.
 * @param adapter The base [RecyclerView.Adapter] instance.
 * @param adapterMapper An [IPropMapper] instance for [ReduxListAdapter].
 * @param diffCallback A [DiffUtil.ItemCallback] instance.
 */
@Suppress("UNCHECKED_CAST")
fun <GState, LState, OutProp, VH, VHState, VHAction> IPropInjector<GState>.injectDiffedAdapter(
  outProp: OutProp,
  adapter: RecyclerView.Adapter<VH>,
  adapterMapper: IPropMapper<LState, OutProp, List<VHState>, VHAction>,
  diffCallback: DiffUtil.ItemCallback<VHState>
): ReduxListAdapter<GState, LState, OutProp, VH, VHState, VHAction> where
  GState : LState,
  LState : Any,
  VH : RecyclerView.ViewHolder,
  VH : IPropContainer<VHState, VHAction>,
  VH : IPropLifecycleOwner<LState, OutProp>,
  VHState : Any,
  VHAction : Any {
  val listAdapter = object : ReduxListAdapter<GState, LState, OutProp, VH, VHState, VHAction>(adapter, diffCallback) {
    override fun onBindViewHolder(holder: VH, position: Int) {
      adapter.onBindViewHolder(holder, position)
      val subscribeId = "$holder${Date().time}"
      val subscription = ReduxSubscription(subscribeId) { holder.afterPropInjectionEnds() }
      this.vhSubscription.add(subscription)
      holder.beforePropInjectionStarts(this.staticProp)
      holder.reduxProp = ReduxProp(subscription, this.getItem(position), this.reduxProp.action)
    }

    override fun onViewRecycled(holder: VH) {
      super.onViewRecycled(holder)
      holder.unsubscribeSafely()?.also { this.vhSubscription.remove(it) }
    }
  }

  this.inject(outProp, listAdapter, adapterMapper)
  return listAdapter
}
