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
import org.swiften.redux.ui.EmptyPropLifecycleOwner
import org.swiften.redux.ui.IActionDependency
import org.swiften.redux.ui.IPropContainer
import org.swiften.redux.ui.IPropLifecycleOwner
import org.swiften.redux.ui.IPropMapper
import org.swiften.redux.ui.IStateMapper
import org.swiften.redux.ui.ObservableReduxProps
import java.util.concurrent.atomic.AtomicInteger

/** Created by haipham on 2019/02/02 */
@Config(manifest = Config.NONE)
@RunWith(RobolectricTestRunner::class)
class RecycleAdapterTest : BaseLifecycleTest() {
  class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
    IPropContainer<Int, Unit>,
    IPropLifecycleOwner<Int, Unit> by EmptyPropLifecycleOwner() {
    companion object : IPropMapper<Int, Unit, Int, Int, Unit> {
      override fun mapState(state: Int, outProps: Int) = state
      override fun mapAction(static: IActionDependency<Unit>, state: Int, outProps: Int) = Unit
    }

    override var reduxProps by ObservableReduxProps<Int, Unit> { _, _ -> }
  }

  class RecyclerAdapter : ReduxRecyclerViewAdapter<ViewHolder>(),
    IStateMapper<Int, Unit, Int> by RecyclerAdapter {
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
    val lifecycleOwner = BaseLifecycleTest.TestLifecycleOwner()
    val adapter = RecyclerAdapter()

    // When
    val wrappedAdapter = injector.injectRecyclerAdapter(lifecycleOwner, adapter, ViewHolder)

    // When - adapter mapper
    /** Every time itemCount is accessed, the adapter's state mapper should be triggered */
    wrappedAdapter.itemCount
    wrappedAdapter.itemCount
    wrappedAdapter.itemCount

    // Then - adapter mapper
    assertEquals(RecyclerAdapter.mapCount.get(), 3)

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
    lifecycleOwner.registry.markState(Lifecycle.State.CREATED)
    lifecycleOwner.registry.markState(Lifecycle.State.STARTED)
    lifecycleOwner.registry.markState(Lifecycle.State.RESUMED)
    lifecycleOwner.registry.markState(Lifecycle.State.DESTROYED)

    // Then - lifecycle ending
    injector.subscriptions.forEach { assertTrue(it.isUnsubscribed()) }
  }
}
