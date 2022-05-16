/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.android.recyclerview

import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.RecyclerView
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.swiften.redux.android.lifecycle.BaseLifecycleTest
import org.swiften.redux.core.DefaultUniqueIDProvider
import org.swiften.redux.core.IActionDispatcher
import org.swiften.redux.core.IUniqueIDProvider
import org.swiften.redux.ui.IPropContainer
import org.swiften.redux.ui.IPropLifecycleOwner
import org.swiften.redux.ui.IPropMapper
import org.swiften.redux.ui.IStateMapper
import org.swiften.redux.ui.NoopPropLifecycleOwner
import org.swiften.redux.ui.ObservableReduxProp
import org.swiften.redux.ui.StaticProp
import java.util.concurrent.atomic.AtomicInteger

/** Created by haipham on 2019/02/02 */
@Config(manifest = Config.NONE)
@RunWith(RobolectricTestRunner::class)
class RecycleAdapterTest : BaseLifecycleTest() {
  class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
    IUniqueIDProvider by DefaultUniqueIDProvider(),
    IPropContainer<Int, Unit>,
    IPropLifecycleOwner<Int, PositionProp<Unit>> by NoopPropLifecycleOwner() {
    companion object : IPropMapper<Int, PositionProp<Unit>, Int, Unit> {
      override fun mapState(state: Int, outProp: PositionProp<Unit>) = state
      override fun mapAction(dispatch: IActionDispatcher, outProp: PositionProp<Unit>) = Unit
    }

    override var reduxProp by ObservableReduxProp<Int, Unit> { _, _ -> }
    val beforeInjection = AtomicInteger()
    val afterInjection = AtomicInteger()

    override fun beforePropInjectionStarts(sp: StaticProp<Int, PositionProp<Unit>>) {
      this.beforeInjection.incrementAndGet()
    }

    override fun afterPropInjectionEnds(sp: StaticProp<Int, PositionProp<Unit>>) {
      this.afterInjection.incrementAndGet()
    }
  }

  class Adapter : ReduxRecyclerViewAdapter<ViewHolder>(),
    IStateMapper<Int, Unit, Int> by Adapter {
    companion object : IStateMapper<Int, Unit, Int> {
      val mapCount = AtomicInteger()

      override fun mapState(state: Int, outProp: Unit): Int {
        this.mapCount.incrementAndGet()
        return state
      }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
      return ViewHolder(parent)
    }
  }

  @Test
  fun `Injecting prop for recycle adapter view holder should work`() {
    // Setup
    val injector = BaseLifecycleTest.TestInjector()
    val lc = BaseLifecycleTest.TestLifecycleOwner()
    val adapter = Adapter()
    val viewHolderCount = 100

    // When
    val wrappedAdapter = injector.injectRecyclerAdapter(Unit, lc, adapter, Adapter, ViewHolder)

    // When - adapter mapper
    /** Every time itemCount is accessed, the adapter's state mapper should be triggered */
    wrappedAdapter.itemCount
    wrappedAdapter.itemCount
    wrappedAdapter.itemCount

    // Then - adapter mapper
    assertEquals(Adapter.mapCount.get(), 3)

    // When - view holder injection
    val viewGroup = LinearLayout(InstrumentationRegistry.getInstrumentation().context)
    val viewHolders = (0 until viewHolderCount).map { wrappedAdapter.onCreateViewHolder(viewGroup, 0) }

    /** On view holder binding, injections will happen. */
    viewHolders.forEachIndexed { i, vh -> wrappedAdapter.bindViewHolder(vh, i) }

    // Then - view holder injection
    assertEquals(injector.injectionCount, viewHolderCount)
    injector.subscriptions.forEach { _, v -> assertFalse(v.isUnsubscribed()) }

    // When - lifecycle ending
    /** When state is destroyed, all subscriptions should have been disposed of. */
    lc.registry.markState(Lifecycle.State.CREATED)
    lc.registry.markState(Lifecycle.State.STARTED)
    lc.registry.markState(Lifecycle.State.RESUMED)
    lc.registry.markState(Lifecycle.State.DESTROYED)

    // Then - lifecycle ending
    injector.subscriptions.forEach { _, v -> assertTrue(v.isUnsubscribed()) }
  }

  @Test
  fun `View holder subscription should be disposed of in onViewRecycled`() {
    // Setup
    val totalItemCount = 3
    val injector = BaseLifecycleTest.TestInjector { totalItemCount }
    val adapter = Adapter()
    val wrappedAdapter = injector.injectRecyclerAdapter(Unit, adapter, Adapter, ViewHolder)
    val viewGroup = LinearLayout(InstrumentationRegistry.getInstrumentation().context)
    val viewHolders = mutableListOf<ViewHolder>()

    // When
    (0 until totalItemCount).forEachIndexed { i, _ ->
      val viewHolder = wrappedAdapter.onCreateViewHolder(viewGroup, 0)
      viewHolders.add(viewHolder)
      wrappedAdapter.bindViewHolder(viewHolder, i)
    }

    viewHolders.forEach { assertEquals(it.beforeInjection.get(), 1) }
    viewHolders.forEach { wrappedAdapter.onViewRecycled(it) }

    // Then
    viewHolders.forEach { assertEquals(it.afterInjection.get(), 1) }
  }
}
