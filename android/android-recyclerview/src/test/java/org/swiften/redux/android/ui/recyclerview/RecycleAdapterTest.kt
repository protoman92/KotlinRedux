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
import org.swiften.redux.ui.IPropMapper
import org.swiften.redux.ui.IStateMapper
import org.swiften.redux.ui.ObservableReduxProps
import java.util.concurrent.atomic.AtomicInteger

/** Created by haipham on 2019/02/02 */
@Config(manifest = Config.NONE)
@RunWith(RobolectricTestRunner::class)
class RecycleAdapterTest : BaseLifecycleTest() {
  class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
    IPropContainer<Int, Pair<Unit, Int>, Int, Unit> {
    companion object : IPropMapper<Int, Pair<Unit, Int>, Int, Unit> {
      override fun mapState(state: Int, outProps: Pair<Unit, Int>) = state
      override fun mapAction(dispatch: IActionDispatcher, outProps: Pair<Unit, Int>) = Unit
    }

    override var reduxProps by ObservableReduxProps<Int, Unit> { _, _ -> }
  }

  class Adapter : ReduxRecyclerViewAdapter<ViewHolder>(),
    IStateMapper<Int, Unit, Int> by Adapter {
    companion object : IStateMapper<Int, Unit, Int> {
      val mapCount = AtomicInteger()

      override fun mapState(state: Int, outProps: Unit): Int {
        this.mapCount.incrementAndGet()
        return state
      }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
      return ViewHolder(parent)
    }
  }

  @Test
  fun `Injecting props for recycle adapter view holder should work`() {
    // Setup
    val injector = BaseLifecycleTest.TestInjector()
    val lc = BaseLifecycleTest.TestLifecycleOwner()
    val adapter = Adapter()

    // When
    val wrappedAdapter = injector.injectRecyclerAdapter(lc, adapter, Unit, Adapter, ViewHolder)

    // When - adapter mapper
    /** Every time itemCount is accessed, the adapter's state mapper should be triggered */
    wrappedAdapter.itemCount
    wrappedAdapter.itemCount
    wrappedAdapter.itemCount

    // Then - adapter mapper
    assertEquals(Adapter.mapCount.get(), 3)

    // When - view holder injection
    val viewGroup = LinearLayout(InstrumentationRegistry.getInstrumentation().context)
    val viewHolder1 = wrappedAdapter.onCreateViewHolder(viewGroup, 0)
    val viewHolder2 = wrappedAdapter.onCreateViewHolder(viewGroup, 0)
    val viewHolder3 = wrappedAdapter.onCreateViewHolder(viewGroup, 0)

    /** On view holder binding, injections will happen */
    wrappedAdapter.onBindViewHolder(viewHolder1, 0)
    wrappedAdapter.onBindViewHolder(viewHolder2, 1)
    wrappedAdapter.onBindViewHolder(viewHolder3, 2)

    // Then - view holder injection
    assertEquals(injector.injectionCount, 3)
    injector.subscriptions.forEach { assertFalse(it.isUnsubscribed()) }

    // When - lifecycle ending
    /** When state is destroyed, all subscriptions should have been disposed of */
    lc.registry.markState(Lifecycle.State.CREATED)
    lc.registry.markState(Lifecycle.State.STARTED)
    lc.registry.markState(Lifecycle.State.RESUMED)
    lc.registry.markState(Lifecycle.State.DESTROYED)

    // Then - lifecycle ending
    injector.subscriptions.forEach { assertTrue(it.isUnsubscribed()) }
  }
}
