/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.android.ui.recyclerview

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.swiften.redux.core.CompositeReduxSubscription
import org.swiften.redux.ui.IPropContainer
import org.swiften.redux.ui.IPropInjector
import org.swiften.redux.ui.IPropMapper
import org.swiften.redux.ui.IStateMapper
import org.swiften.redux.ui.unsubscribeSafely
import java.util.Date

/** Created by haipham on 2019/01/08 */
/**
 * Convenience [RecyclerView.Adapter] that implements some default methods to make working with
 * [IPropInjector] easier. Basically, [RecyclerView.Adapter.getItemCount] always returns 0
 * (because it will be delegated to a different calculation.
 *
 * This class is not required because custom [RecyclerView.Adapter] only needs to do the same as
 * which has been done here to ensure proper integration.
 */
abstract class ReduxRecyclerViewAdapter<VH : RecyclerView.ViewHolder> : RecyclerView.Adapter<VH>() {
  final override fun getItemCount() = 0
  override fun onBindViewHolder(holder: VH, position: Int) {}
}

/** [RecyclerView.Adapter] that delegates method calls to another [RecyclerView.Adapter] */
abstract class DelegateRecyclerAdapter<VH>(
  private val adapter: RecyclerView.Adapter<VH>
) : RecyclerView.Adapter<VH>() where VH : RecyclerView.ViewHolder {
  protected val composite = CompositeReduxSubscription("${this.javaClass}${Date().time}")

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
    adapter.onCreateViewHolder(parent, viewType)

  override fun getItemViewType(position: Int) = adapter.getItemViewType(position)
  override fun getItemId(position: Int) = adapter.getItemId(position)
  override fun onFailedToRecycleView(holder: VH) = adapter.onFailedToRecycleView(holder)

  override fun onBindViewHolder(holder: VH, position: Int) {
    adapter.onBindViewHolder(holder, position)
  }

  override fun onViewRecycled(holder: VH) {
    super.onViewRecycled(holder)
    adapter.onViewRecycled(holder)
  }

  override fun onViewAttachedToWindow(holder: VH) {
    super.onViewAttachedToWindow(holder)
    adapter.onViewAttachedToWindow(holder)
  }

  override fun onViewDetachedFromWindow(holder: VH) {
    super.onViewDetachedFromWindow(holder)
    adapter.onViewDetachedFromWindow(holder)
  }

  override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
    super.onAttachedToRecyclerView(recyclerView)
    adapter.onAttachedToRecyclerView(recyclerView)
  }

  override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
    super.onDetachedFromRecyclerView(recyclerView)
    adapter.onDetachedFromRecyclerView(recyclerView)
  }

  override fun setHasStableIds(hasStableIds: Boolean) {
    super.setHasStableIds(hasStableIds)
    adapter.setHasStableIds(hasStableIds)
  }

  override fun registerAdapterDataObserver(observer: RecyclerView.AdapterDataObserver) {
    super.registerAdapterDataObserver(observer)
    adapter.registerAdapterDataObserver(observer)
  }

  override fun unregisterAdapterDataObserver(observer: RecyclerView.AdapterDataObserver) {
    super.unregisterAdapterDataObserver(observer)
    adapter.unregisterAdapterDataObserver(observer)
  }

  /**
   * Since we will be performing [IPropInjector.injectProps] for [VH] instances, we will be
   * be using [CompositeReduxSubscription.add] a lot every time
   * [RecyclerView.Adapter.onBindViewHolder] is called. As a result, calling this method will
   * ensure proper deinitialization.
   */
  internal fun unsubscribeSafely() = this.composite.unsubscribe()
}

/**
 * Inject props for a [RecyclerView.Adapter] with a compatible [VH]. Note that this does not
 * support lifecycle handling, so we will need to manually set null via [RecyclerView.setAdapter]
 * in order to invoke [RecyclerView.Adapter.onViewRecycled], e.g. on orientation change.
 */
fun <GlobalState, Adapter, VH, VHState, VHAction> IPropInjector<GlobalState>.injectRecyclerAdapterProps(
  adapter: Adapter,
  adapterMapper: IStateMapper<GlobalState, Unit, Int>,
  vhMapper: IPropMapper<GlobalState, Int, VHState, VHAction>
): DelegateRecyclerAdapter<VH> where
  VH : RecyclerView.ViewHolder,
  VH : IPropContainer<GlobalState, VHState, VHAction>,
  Adapter : RecyclerView.Adapter<VH> {
  return object : DelegateRecyclerAdapter<VH>(adapter) {
    override fun getItemCount() =
      adapterMapper.mapState(this@injectRecyclerAdapterProps.lastState(), Unit)

    override fun onBindViewHolder(holder: VH, position: Int) {
      super.onBindViewHolder(holder, position)
      val subscription = this@injectRecyclerAdapterProps.injectProps(holder, position, vhMapper)
      this.composite.add(subscription)
    }

    /** Unsubscribe from [holder]'s subscription on recycling */
    override fun onViewRecycled(holder: VH) {
      super.onViewRecycled(holder)
      holder.unsubscribeSafely()?.also { this.composite.remove(it) }
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
      super.onDetachedFromRecyclerView(recyclerView)
      this.unsubscribeSafely()
    }
  }
}
