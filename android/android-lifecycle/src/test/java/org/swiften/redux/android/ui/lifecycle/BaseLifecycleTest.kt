/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.android.ui.lifecycle

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import java.lang.RuntimeException
import java.util.concurrent.atomic.AtomicInteger

/** Created by viethai.pham on 2019/01/28 */
open class BaseLifecycleTest {
  class TestLifecycleRegistry(provider: LifecycleOwner) : LifecycleRegistry(provider) {
    val registerCount = AtomicInteger()
    val unregisterCount = AtomicInteger()

    override fun addObserver(observer: androidx.lifecycle.LifecycleObserver) {
      super.addObserver(observer)
      this.registerCount.incrementAndGet()
    }

    override fun removeObserver(observer: androidx.lifecycle.LifecycleObserver) {
      super.removeObserver(observer)
      this.unregisterCount.incrementAndGet()
    }
  }

  class TestLifecycleOwner : LifecycleOwner {
    val registry = TestLifecycleRegistry(this)

    override fun getLifecycle(): Lifecycle = this.registry
  }

  protected lateinit var owner: TestLifecycleOwner

  @Before
  fun beforeMethod() {
    this.owner = TestLifecycleOwner()
  }

  @Test
  fun `Moving to new state should notify lifecycle observer`() {
    // Setup
    val onCreateCount = AtomicInteger()
    val onResumeCount = AtomicInteger()
    val onPauseCount = AtomicInteger()
    val onDestroyCount = AtomicInteger()
    val startCount = AtomicInteger()
    val endCount = AtomicInteger()

    object : LifecycleObserver(this.owner, object : ILifecycleCallback {
      override fun onSafeForStartingLifecycleAwareTasks() { startCount.incrementAndGet() }
      override fun onSafeForEndingLifecycleAwareTasks() { endCount.incrementAndGet() }
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
    this.owner.registry.markState(Lifecycle.State.CREATED)
    this.owner.registry.markState(Lifecycle.State.STARTED)
    this.owner.registry.markState(Lifecycle.State.RESUMED)
    this.owner.registry.markState(Lifecycle.State.DESTROYED)

    // Then
    Assert.assertEquals(onCreateCount.get(), 1)
    Assert.assertEquals(startCount.get(), 1)
    Assert.assertEquals(this.owner.registry.registerCount.get(), 2)
    Assert.assertEquals(onResumeCount.get(), 1)
    Assert.assertEquals(onPauseCount.get(), 1)
    Assert.assertEquals(endCount.get(), 1)
    Assert.assertEquals(this.owner.registry.unregisterCount.get(), 1)

    /** The lifecycle registry should have been removed by now */
    Assert.assertEquals(onDestroyCount.get(), 0)
  }
}

