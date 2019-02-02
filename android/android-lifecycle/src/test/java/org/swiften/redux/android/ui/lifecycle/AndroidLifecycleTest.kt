/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.android.ui.lifecycle

import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test
import java.util.concurrent.atomic.AtomicInteger

/** Created by haipham on 2019/02/03 */
class AndroidLifecycleTest : BaseLifecycleTest() {
  @Test
  fun `Moving to new state should notify lifecycle observer`() {
    // Setup
    val owner = TestLifecycleOwner()
    val onCreateCount = AtomicInteger()
    val onResumeCount = AtomicInteger()
    val onPauseCount = AtomicInteger()
    val onDestroyCount = AtomicInteger()
    val lifecycleStartCount = AtomicInteger()
    val lifecycleEndCount = AtomicInteger()

    object : LifecycleObserver(owner, object : ILifecycleCallback {
      override fun onSafeForStartingLifecycleAwareTasks() { lifecycleStartCount.incrementAndGet() }
      override fun onSafeForEndingLifecycleAwareTasks() { lifecycleEndCount.incrementAndGet() }
    }) {
      override fun onCreate() {
        super.onCreate()
        onCreateCount.incrementAndGet()
      }

      override fun onResume() {
        super.onResume()
        onResumeCount.incrementAndGet()
      }

      override fun onPause() {
        super.onPause()
        onPauseCount.incrementAndGet()
      }

      override fun onDestroy() {
        super.onDestroy()
        onDestroyCount.incrementAndGet()
      }
    }

    // When
    owner.registry.markState(Lifecycle.State.CREATED)
    owner.registry.markState(Lifecycle.State.STARTED)
    owner.registry.markState(Lifecycle.State.RESUMED)
    owner.registry.markState(Lifecycle.State.DESTROYED)

    // Then
    assertEquals(onCreateCount.get(), 1)
    assertEquals(lifecycleStartCount.get(), 1)
    assertEquals(owner.registry.registerCount.get(), 2)
    assertEquals(onResumeCount.get(), 1)
    assertEquals(onPauseCount.get(), 1)
    assertEquals(lifecycleEndCount.get(), 1)
    assertEquals(owner.registry.unregisterCount.get(), 1)

    /** The lifecycle registry should have been removed by now */
    assertEquals(onDestroyCount.get(), 0)
  }

  @Test
  fun `Injecting lifecycle should manage subscription correctly`() {
    // Setup
    val injector = TestInjector()
    val owner = TestLifecycleOwner()

    // When
    injector.injectLifecycle(owner)

    // When && Then
    owner.registry.markState(Lifecycle.State.STARTED)
    injector.subscriptions.forEach { assertFalse(it.isUnsubscribed()) }
    owner.registry.markState(Lifecycle.State.DESTROYED)
    injector.subscriptions.forEach { assertTrue(it.isUnsubscribed()) }
  }

  @Test
  fun `Injecting fragment props should manage fragment callbacks correctly`() {
    // Setup
    val injector = TestInjector()
    val activity = TestActivity()
    val injectionCount = AtomicInteger()

    // When
    injector.injectFragment(activity) { injectionCount.incrementAndGet() }

    // When && Then
    activity.registry.markState(Lifecycle.State.CREATED)
    assertNotNull(activity.fm.callbacks.get())
    activity.fm.callbacks.get().onFragmentStarted(activity.fm, Fragment())
    activity.fm.callbacks.get().onFragmentStarted(activity.fm, Fragment())
    activity.fm.callbacks.get().onFragmentStarted(activity.fm, Fragment())
    assertEquals(injectionCount.get(), 3)
    activity.registry.markState(Lifecycle.State.DESTROYED)
    assertNull(activity.fm.callbacks.get())
  }
}
