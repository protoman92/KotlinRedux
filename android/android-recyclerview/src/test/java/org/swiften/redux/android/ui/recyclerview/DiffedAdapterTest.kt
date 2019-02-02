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
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.swiften.redux.android.ui.lifecycle.BaseLifecycleTest
import org.swiften.redux.core.IActionDispatcher
import org.swiften.redux.ui.IPropMapper
import org.swiften.redux.ui.IVariablePropContainer
import org.swiften.redux.ui.ObservableVariableProps

/** Created by haipham on 2019/02/03 */
@Config(manifest = Config.NONE)
@RunWith(RobolectricTestRunner::class)
class DiffedAdapterTest : BaseLifecycleTest() {
  class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
    IVariablePropContainer<Int, ViewHolder.A?> {
    class A

    override var reduxProps by ObservableVariableProps<Int, A?> { _, _ -> }
  }

  class RecyclerAdapter : ReduxRecyclerViewAdapter<ViewHolder>(),
    IPropMapper<Int, Unit, List<Int>?, ViewHolder.A> by RecyclerAdapter,
    IDiffItemCallback<Int> by RecyclerAdapter {
    companion object : IPropMapper<Int, Unit, List<Int>?, ViewHolder.A>, IDiffItemCallback<Int> {
      override fun mapState(state: Int, outProps: Unit): List<Int>? {
        return arrayListOf(state)
      }

      override fun mapAction(dispatch: IActionDispatcher, state: Int, outProps: Unit): ViewHolder.A {
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
  fun `Injecting props for diffed adapter should work`() {
    // Setup
    val injector = BaseLifecycleTest.TestInjector()
    val lifecycleOwner = BaseLifecycleTest.TestLifecycleOwner()
    val adapter = RecyclerAdapter()

    // When
    val wrappedAdapter = injector.injectDiffedAdapter(lifecycleOwner, adapter)

    // Then - adapter injection
    assertEquals(injector.injectionCount, 1)

    // When - view holder injection
    /** Submit new items to ensure view holders binding do not throw index exception */
    wrappedAdapter.submitList(arrayListOf(0, 1, 2))
    val viewGroup = LinearLayout(InstrumentationRegistry.getInstrumentation().context)
    val viewHolder1 = wrappedAdapter.onCreateViewHolder(viewGroup, 0)
    val viewHolder2 = wrappedAdapter.onCreateViewHolder(viewGroup, 0)
    val viewHolder3 = wrappedAdapter.onCreateViewHolder(viewGroup, 0)

    /** No injections here, just manually setting of props, so injection count remains the same */
    wrappedAdapter.onBindViewHolder(viewHolder1, 0)
    wrappedAdapter.onBindViewHolder(viewHolder2, 1)
    wrappedAdapter.onBindViewHolder(viewHolder3, 2)

    // Then - view holder injection
    assertEquals(injector.injectionCount, 1)
    injector.subscriptions.forEach { Assert.assertFalse(it.isUnsubscribed()) }

    // When - lifecycle ending
    /** When state is destroyed, all subscriptions should have been disposed of */
    lifecycleOwner.registry.markState(Lifecycle.State.CREATED)
    lifecycleOwner.registry.markState(Lifecycle.State.STARTED)
    lifecycleOwner.registry.markState(Lifecycle.State.RESUMED)
    lifecycleOwner.registry.markState(Lifecycle.State.DESTROYED)

    // Then - lifecycle ending
    injector.subscriptions.forEach { Assert.assertTrue(it.isUnsubscribed()) }
  }
}
