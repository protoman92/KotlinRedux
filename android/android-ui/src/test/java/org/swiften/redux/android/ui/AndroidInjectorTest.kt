/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.android.ui

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Before
import org.junit.Test
import org.swiften.redux.android.util.AndroidUtil
import org.swiften.redux.core.IReduxStore
import org.swiften.redux.core.ISubscriberIDProvider
import org.swiften.redux.core.UUIDSubscriberIDProvider
import org.swiften.redux.ui.IFullPropInjector
import org.swiften.redux.ui.IPropContainer
import org.swiften.redux.ui.IPropLifecycleOwner
import org.swiften.redux.ui.LateinitObservableProp
import org.swiften.redux.ui.PropInjectorTest
import org.swiften.redux.ui.ReduxProp
import org.swiften.redux.ui.StaticProp
import java.util.concurrent.atomic.AtomicBoolean
import java.util.concurrent.atomic.AtomicInteger

/** Created by haipham on 2019/01/28 */
class AndroidInjectorTest : PropInjectorTest() {
  class Runner : AndroidUtil.IMainThreadRunner {
    private val _runCount = AtomicInteger()

    val runCount get() = this._runCount.get()

    override fun invoke(runnable: () -> Unit) {
      this._runCount.incrementAndGet()
      runnable()
    }
  }

  private lateinit var runner: Runner

  @Before
  override fun beforeMethod() {
    super.beforeMethod()
    this.runner = Runner()
  }

  internal class AndroidView :
    ISubscriberIDProvider by UUIDSubscriberIDProvider(),
    IPropContainer<S, A>,
    IPropLifecycleOwner<S, Unit> {
    private val propInitialized = AtomicBoolean(false)

    override var reduxProp by LateinitObservableProp<ReduxProp<S, A>> { _, _ ->
      this.propInjectionCount.incrementAndGet()
      this.propInitialized.set(true)
    }

    val propInjectionCount = AtomicInteger()
    val beforeInjectionCount = AtomicInteger()
    val afterInjectionCount = AtomicInteger()

    override fun beforePropInjectionStarts(sp: StaticProp<S, Unit>) {
      assertFalse(this.propInitialized.get())
      this.beforeInjectionCount.incrementAndGet()
    }

    override fun afterPropInjectionEnds() {
      this.afterInjectionCount.incrementAndGet()
      this.propInitialized.set(false)
    }
  }

  override fun createInjector(store: IReduxStore<S>): IFullPropInjector<S> {
    return AndroidPropInjector(store, this.runner)
  }

  @Test
  @Suppress("ForEachParameterNotUsed")
  fun `Injecting prop with Android injector should set prop on main thread`() {
    // Setup
    val injector = this.createInjector(this.store)
    val view = AndroidView()
    val mapper = this.mapper

    runBlocking {
      // When
      repeat(5) { GlobalScope.launch { injector.inject(Unit, view, mapper) } }
      delay(1000)

      // Then
      /** By now, [runner] should have been called upon as many times as specified. */
      assertEquals(this@AndroidInjectorTest.runner.runCount,
        view.propInjectionCount.get() +
          view.beforeInjectionCount.get() +
          view.afterInjectionCount.get()
      )
    }
  }
}
