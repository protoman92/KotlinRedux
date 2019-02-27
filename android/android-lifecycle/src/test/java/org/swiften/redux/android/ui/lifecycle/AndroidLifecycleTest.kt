/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.android.ui.lifecycle

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test
import org.swiften.redux.ui.IPropInjector
import java.io.FileDescriptor
import java.io.PrintWriter
import java.util.concurrent.atomic.AtomicInteger
import java.util.concurrent.atomic.AtomicReference

/** Created by haipham on 2019/02/03 */
class AndroidLifecycleTest : BaseLifecycleTest() {
  @Suppress("ImplicitNullableNothingType")
  class TestFragmentManager : FragmentManager() {
    val callbacks = AtomicReference<FragmentLifecycleCallbacks>()

    override fun saveFragmentInstanceState(f: Fragment): Fragment.SavedState? = null
    override fun findFragmentById(id: Int) = null
    override fun getFragments() = mutableListOf<Fragment>()
    override fun beginTransaction() = throw RuntimeException()
    override fun putFragment(bundle: Bundle, key: String, fragment: Fragment) {}
    override fun removeOnBackStackChangedListener(listener: OnBackStackChangedListener) {}
    override fun getFragment(bundle: Bundle, key: String): Fragment? = null
    override fun getPrimaryNavigationFragment() = null
    override fun getBackStackEntryCount() = 0
    override fun isDestroyed() = false
    override fun getBackStackEntryAt(index: Int) = throw RuntimeException("")
    override fun executePendingTransactions() = false
    override fun popBackStackImmediate() = false
    override fun popBackStackImmediate(name: String?, flags: Int) = false
    override fun popBackStackImmediate(id: Int, flags: Int) = false
    override fun findFragmentByTag(tag: String?) = null
    override fun addOnBackStackChangedListener(listener: OnBackStackChangedListener) {}

    override fun dump(
      prefix: String,
      fd: FileDescriptor?,
      writer: PrintWriter,
      args: Array<out String>?
    ) {}

    override fun isStateSaved() = false
    override fun popBackStack() {}
    override fun popBackStack(name: String?, flags: Int) {}
    override fun popBackStack(id: Int, flags: Int) {}

    override fun registerFragmentLifecycleCallbacks(
      cb: FragmentLifecycleCallbacks,
      recursive: Boolean
    ) {
      this.callbacks.set(cb)
    }

    override fun unregisterFragmentLifecycleCallbacks(cb: FragmentLifecycleCallbacks) {
      this.callbacks.set(null)
    }
  }

  class TestActivity : IAppCompatActivity, LifecycleOwner {
    val registry = TestLifecycleRegistry(this)
    val fm = TestFragmentManager()
    override fun getLifecycle(): Lifecycle = this@TestActivity.registry
    override val supportFragmentManager get() = fm
  }

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

    object : ReduxLifecycleObserver(owner, object : ILifecycleCallback {
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
    injector.injectLifecycle(Unit, owner, TestLifecycleOwner)

    // When && Then
    owner.registry.markState(Lifecycle.State.STARTED)
    injector.subscriptions.forEach { _, v -> assertFalse(v.isUnsubscribed()) }
    owner.registry.markState(Lifecycle.State.DESTROYED)
    injector.subscriptions.forEach { _, v -> assertTrue(v.isUnsubscribed()) }
  }

  @Test
  fun `Injecting fragment prop should manage fragment callbacks correctly`() {
    // Setup
    val injector = TestInjector()
    val activity = TestActivity()
    val injectionCount = AtomicInteger()

    // When
    injector.injectFragment(activity, object : ILifecycleInjectionHelper<Int> {
      override fun inject(injector: IPropInjector<Int>, owner: LifecycleOwner) {
        injectionCount.incrementAndGet()
      }

      override fun deinitialize(owner: LifecycleOwner) {}
    })

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
