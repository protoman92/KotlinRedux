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
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.swiften.redux.android.util.AndroidUtil
import org.swiften.redux.core.IReduxStore
import org.swiften.redux.ui.IPropContainer
import org.swiften.redux.ui.IPropInjector
import org.swiften.redux.ui.IPropLifecycleOwner
import org.swiften.redux.ui.PropInjectorTest
import org.swiften.redux.ui.ReduxProp
import org.swiften.redux.ui.StaticProp
import org.swiften.redux.ui.VetoableObservableProp
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

  internal class AndroidView : IPropContainer<S, A>, IPropLifecycleOwner<S, Unit> {
    /**
     * Make sue [VetoableObservableProp.equalChecker] always returns false to avoid comparison
     * because [AndroidUtil.IMainThreadRunner.invoke] is performed before the prop comparison
     * happens. By returning false we can compare the actual [Runner.runCount] with the number
     * of times prop injection happens.
     */
    override var reduxProp by VetoableObservableProp<ReduxProp<S, A>>(
      { _, _ -> false }
    ) { _, _ -> this.propInjectionCount.incrementAndGet() }

    val propInjectionCount = AtomicInteger()
    val beforeInjectionCount = AtomicInteger()
    val afterInjectionCount = AtomicInteger()

    override fun beforePropInjectionStarts(sp: StaticProp<S, Unit>) {
      assertNotNull(this.reduxProp)
      this.beforeInjectionCount.incrementAndGet()
    }

    override fun afterPropInjectionEnds() { this.afterInjectionCount.incrementAndGet() }
  }

  override fun createInjector(store: IReduxStore<S>): IPropInjector<S> {
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
      (0 until 5).forEach { GlobalScope.launch { injector.inject(Unit, view, mapper) } }

      delay(500)

      // Then
      /** By now, [runner] should have been called upon as many times as specified */
      assertEquals(this@AndroidInjectorTest.runner.runCount,
        view.propInjectionCount.get() +
          view.beforeInjectionCount.get() +
          view.afterInjectionCount.get()
      )
    }
  }
}
