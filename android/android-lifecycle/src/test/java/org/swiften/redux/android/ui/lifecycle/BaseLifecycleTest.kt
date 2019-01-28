/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.android.ui.lifecycle

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import org.junit.Assert
import org.junit.Test
import org.swiften.redux.core.IActionDispatcher
import org.swiften.redux.core.IDeinitializer
import org.swiften.redux.core.IReduxAction
import org.swiften.redux.core.IReduxSubscription
import org.swiften.redux.core.IStateGetter
import org.swiften.redux.core.ReduxSubscription
import org.swiften.redux.ui.EmptyPropLifecycleOwner
import org.swiften.redux.ui.IPropContainer
import org.swiften.redux.ui.IPropInjector
import org.swiften.redux.ui.IPropLifecycleOwner
import org.swiften.redux.ui.IPropMapper
import org.swiften.redux.ui.ObservableReduxProps
import java.io.FileDescriptor
import java.io.PrintWriter
import java.util.Collections
import java.util.concurrent.atomic.AtomicInteger
import java.util.concurrent.atomic.AtomicReference

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

  class TestLifecycleOwner : LifecycleOwner,
    IPropContainer<Int, Int, Unit>,
    IPropLifecycleOwner<Int> by EmptyPropLifecycleOwner(),
    IPropMapper<Int, Unit, Int, Unit> by TestLifecycleOwner {
    companion object : IPropMapper<Int, Unit, Int, Unit> {
      override fun mapState(state: Int, outProps: Unit) = state
      override fun mapAction(dispatch: IActionDispatcher, state: Int, outProps: Unit) = Unit
    }

    val registry = TestLifecycleRegistry(this)
    override var reduxProps by ObservableReduxProps<Int, Int, Unit> { _, _ -> }
    override fun getLifecycle(): Lifecycle = this.registry
  }

  @Suppress("ImplicitNullableNothingType")
  class TestFragmentManager : FragmentManager() {
    val callbacks = AtomicReference<FragmentLifecycleCallbacks>()

    override fun saveFragmentInstanceState(f: Fragment?) = null
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
      prefix: String?,
      fd: FileDescriptor?,
      writer: PrintWriter?,
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

  class TestActivity : AppCompatActivity(),
    IPropContainer<Int, Int, Unit>,
    IPropLifecycleOwner<Int> by EmptyPropLifecycleOwner(),
    IPropMapper<Int, Unit, Int, Unit> by TestActivity {
    companion object : IPropMapper<Int, Unit, Int, Unit> {
      override fun mapState(state: Int, outProps: Unit) = state
      override fun mapAction(dispatch: IActionDispatcher, state: Int, outProps: Unit) = Unit
    }

    override var reduxProps by ObservableReduxProps<Int, Int, Unit> { _, _ -> }

    val registry = TestLifecycleRegistry(this)
    val fm = TestFragmentManager()

    override fun getLifecycle(): Lifecycle = this@TestActivity.registry
    override fun getSupportFragmentManager() = fm
  }

  class TestInjector : IPropInjector<Int> {
    val injectionCount = AtomicInteger()
    val dispatched = Collections.synchronizedList(mutableListOf<IReduxAction>())
    val subscription = AtomicReference<IReduxSubscription>()

    override fun <OutProps, State, Action> inject(
      view: IPropContainer<Int, State, Action>,
      outProps: OutProps,
      mapper: IPropMapper<Int, OutProps, State, Action>
    ): IReduxSubscription {
      this.injectionCount.incrementAndGet()
      val subscription = ReduxSubscription("$view") {}
      this.subscription.set(subscription)
      return subscription
    }

    override val dispatch: IActionDispatcher = { this.dispatched.add(it) }
    override val lastState: IStateGetter<Int> = { 0 }
    override val deinitialize: IDeinitializer = {}
  }

  @Test
  fun `Moving to new state should notify lifecycle observer`() {
    // Setup
    val owner = TestLifecycleOwner()
    val onCreateCount = AtomicInteger()
    val onResumeCount = AtomicInteger()
    val onPauseCount = AtomicInteger()
    val onDestroyCount = AtomicInteger()
    val startCount = AtomicInteger()
    val endCount = AtomicInteger()

    object : LifecycleObserver(owner, object : ILifecycleCallback {
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
    owner.registry.markState(Lifecycle.State.CREATED)
    owner.registry.markState(Lifecycle.State.STARTED)
    owner.registry.markState(Lifecycle.State.RESUMED)
    owner.registry.markState(Lifecycle.State.DESTROYED)

    // Then
    Assert.assertEquals(onCreateCount.get(), 1)
    Assert.assertEquals(startCount.get(), 1)
    Assert.assertEquals(owner.registry.registerCount.get(), 2)
    Assert.assertEquals(onResumeCount.get(), 1)
    Assert.assertEquals(onPauseCount.get(), 1)
    Assert.assertEquals(endCount.get(), 1)
    Assert.assertEquals(owner.registry.unregisterCount.get(), 1)

    /** The lifecycle registry should have been removed by now */
    Assert.assertEquals(onDestroyCount.get(), 0)
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
    Assert.assertNotNull(injector.subscription.get())
    Assert.assertFalse(injector.subscription.get().isUnsubscribed())
    owner.registry.markState(Lifecycle.State.DESTROYED)
    Assert.assertTrue(injector.subscription.get().isUnsubscribed())
  }
}

