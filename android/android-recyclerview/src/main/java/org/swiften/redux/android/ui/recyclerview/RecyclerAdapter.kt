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
import org.swiften.redux.ui.ReduxProp
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

/**
 * Use this as container for outer prop for [DelegateRecyclerAdapter] view holder.
 * @param OutProp The main external prop.
 * @param external An [OutProp] instance.
 * @param position The view holder's position.
 */
class PositionProp<OutProp>(val external: OutProp, val position: Int)

/**
 * [RecyclerView.Adapter] that delegates method calls to another [RecyclerView.Adapter].
 * @param GState The global state type.
 * @param LState The local state type that [GState] must extend from.
 * @param OutProp Property as defined by [lifecycleOwner]'s parent.
 * @param VH The [RecyclerView.ViewHolder] instance.
 * @param VHState The [VH] state type. See [ReduxProp.state].
 * @param VHAction The [VH] action type. See [ReduxProp.action].
 * @param adapter The base [RecyclerView.Adapter] instance.
 */
abstract class DelegateRecyclerAdapter<GState, LState, OutProp, VH, VHState, VHAction>(
  private val adapter: RecyclerView.Adapter<VH>
) : RecyclerView.Adapter<VH>() where
  GState : LState,
  LState : Any,
  VH : RecyclerView.ViewHolder,
  VH : IPropContainer<LState, PositionProp<OutProp>, VHState, VHAction>,
  VHState : Any,
  VHAction : Any {
  protected val composite = CompositeReduxSubscription("${this.javaClass}${Date().time}")

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
    return this.adapter.onCreateViewHolder(parent, viewType)
  }

  override fun getItemViewType(position: Int) = this.adapter.getItemViewType(position)
  override fun getItemId(position: Int) = this.adapter.getItemId(position)
  override fun onFailedToRecycleView(holder: VH) = this.adapter.onFailedToRecycleView(holder)

  override fun onViewRecycled(holder: VH) {
    super.onViewRecycled(holder)
    this.adapter.onViewRecycled(holder)
    holder.unsubscribeSafely()?.also { this.composite.remove(it) }
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

  override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
    super.onDetachedFromRecyclerView(recyclerView)
    this.adapter.onDetachedFromRecyclerView(recyclerView)
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

  /**
   * Since we will be performing [IPropInjector.inject] for [VH] instances, we will be using
   * [CompositeReduxSubscription.add] a lot every time [RecyclerView.Adapter.onBindViewHolder] is
   * called. As a result, calling this method will ensure proper deinitialization.
   */
  internal fun unsubscribeSafely() = this.composite.unsubscribe()
}

/**
 * Inject prop for a [RecyclerView.Adapter] with a compatible [VH]. Note that this does not support
 * lifecycle handling, so we will need to manually set null via [RecyclerView.setAdapter] in order
 * to invoke [RecyclerView.Adapter.onViewRecycled], e.g. on orientation change.
 *
 * Note that [VH]'s outer prop store both [OutProp] and [Int] corresponding to its layout position.
 * @return An [IPropInjector] instance.
 * @param GState The global state type.
 * @param LState The local state type that [GState] must extend from.
 * @param OutProp Property as defined by [adapter]'s parent.
 * @param VH The [RecyclerView.ViewHolder] instance.
 * @param VHState The [VH] state type. See [ReduxProp.state].
 * @param VHAction The [VH] action type. See [ReduxProp.action].
 * @param adapter The base [RecyclerView.Adapter] instance.
 * @param outProp An [OutProp] instance.
 * @param adapterMapper An [IStateMapper] instance that calculates item count for
 * [RecyclerView.Adapter.getItemCount].
 * @param vhMapper An [IPropMapper] instance for [VH].
 * @return A [DelegateRecyclerAdapter] instance.
 */
fun <GState, LState, OutProp, VH, VHState, VHAction> IPropInjector<GState>.injectRecyclerAdapter(
  adapter: RecyclerView.Adapter<VH>,
  outProp: OutProp,
  adapterMapper: IStateMapper<LState, Unit, Int>,
  vhMapper: IPropMapper<LState, PositionProp<OutProp>, VHState, VHAction>
): DelegateRecyclerAdapter<GState, LState, OutProp, VH, VHState, VHAction> where
  GState : LState,
  LState : Any,
  VH : RecyclerView.ViewHolder,
  VH : IPropContainer<LState, PositionProp<OutProp>, VHState, VHAction>,
  VHState : Any,
  VHAction : Any {
  return object : DelegateRecyclerAdapter<GState, LState, OutProp, VH, VHState, VHAction>(adapter) {
    override fun getItemCount(): Int {
      return adapterMapper.mapState(this@injectRecyclerAdapter.lastState(), Unit)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
      val vhOutProp = PositionProp(outProp, position)
      val subscription = this@injectRecyclerAdapter.inject(holder, vhOutProp, vhMapper)
      this.composite.add(subscription)
    }

    override fun onViewRecycled(holder: VH) {
      super.onViewRecycled(holder)
      holder.unsubscribeSafely()?.also { this.composite.remove(it) }
    }
  }
}
