/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.android.ui.recyclerview

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
import org.swiften.redux.android.ui.lifecycle.BaseLifecycleTest
import org.swiften.redux.core.IActionDispatcher
import org.swiften.redux.ui.IPropContainer
import org.swiften.redux.ui.IPropLifecycleOwner
import org.swiften.redux.ui.IPropMapper
import org.swiften.redux.ui.ObservableReduxProp
import org.swiften.redux.ui.StaticProp
import java.util.concurrent.atomic.AtomicInteger

/** Created by haipham on 2019/02/03 */
@Config(manifest = Config.NONE)
@RunWith(RobolectricTestRunner::class)
class DiffedAdapterTest : BaseLifecycleTest() {
  class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
    IPropContainer<Int, ViewHolder.A>,
    IPropLifecycleOwner<Int, Unit> {
    class A

    val afterInjection = AtomicInteger()

    override var reduxProp by ObservableReduxProp<Int, A> { _, _ -> }
    override fun beforePropInjectionStarts(sp: StaticProp<Int, Unit>) {}

    override fun afterPropInjectionEnds() {
      this.afterInjection.incrementAndGet()
    }
  }

  class Adapter : ReduxRecyclerViewAdapter<ViewHolder>(),
    IPropMapper<Int, Unit, List<Int>, ViewHolder.A> by Adapter,
    IDiffItemCallback<Int> by Adapter {
    companion object : IPropMapper<Int, Unit, List<Int>, ViewHolder.A>, IDiffItemCallback<Int> {
      override fun mapState(state: Int, outProp: Unit): List<Int> {
        return (0 until state).map { it }
      }

      override fun mapAction(dispatch: IActionDispatcher, outProp: Unit): ViewHolder.A {
        return ViewHolder.A()
      }

      override fun areItemsTheSame(oldItem: Int, newItem: Int) = oldItem == newItem
      override fun areContentsTheSame(oldItem: Int, newItem: Int) = oldItem == newItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
      return ViewHolder(parent)
    }
  }

  @Test
  fun `Injecting prop for diffed adapter should work`() {
    // Setup
    val totalItemCount = 3

    /**
     * Override [BaseLifecycleTest.TestInjector.lastState] to avoid calling
     * [ReduxListAdapter.submitList]. This allows us to specify how many [ViewHolder] to create.
     */
    val injector = BaseLifecycleTest.TestInjector { totalItemCount }
    val lc = BaseLifecycleTest.TestLifecycleOwner()
    val adapter = Adapter()

    // When
    val wrappedAdapter = injector.injectDiffedAdapter(Unit, lc, adapter, Adapter, Adapter)

    // Then - adapter injection
    assertEquals(injector.injectionCount, 1)

    // When - view holder injection
    /** Submit new items to ensure view holders binding do not throw index exception */
    val viewGroup = LinearLayout(InstrumentationRegistry.getInstrumentation().context)
    val viewHolders = mutableListOf<ViewHolder>()

    (0 until totalItemCount).forEachIndexed { i, _ ->
      val viewHolder = wrappedAdapter.onCreateViewHolder(viewGroup, 0)
      viewHolders.add(viewHolder)

      /** No injections here, just manually setting of prop, so injection count remains the same */
      wrappedAdapter.onBindViewHolder(viewHolder, i)
    }

    // Then - view holder injection
    assertEquals(injector.injectionCount, 1)
    injector.subscriptions.forEach { _, v -> assertFalse(v.isUnsubscribed()) }

    // When - lifecycle ending
    /** When state is destroyed, all subscriptions should have been disposed of */
    lc.registry.markState(Lifecycle.State.CREATED)
    lc.registry.markState(Lifecycle.State.STARTED)
    lc.registry.markState(Lifecycle.State.RESUMED)
    lc.registry.markState(Lifecycle.State.DESTROYED)

    // Then - lifecycle ending
    injector.subscriptions.forEach { _, v -> assertTrue(v.isUnsubscribed()) }
    viewHolders.forEach { assertEquals(it.afterInjection.get(), 1) }
  }
}
